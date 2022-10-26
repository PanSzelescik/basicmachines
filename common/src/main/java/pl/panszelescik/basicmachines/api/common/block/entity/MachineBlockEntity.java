package pl.panszelescik.basicmachines.api.common.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pl.panszelescik.basicmachines.api.common.block.inventory.IMachineContainer;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.BasicMachinesMod;

import java.util.Optional;

public class MachineBlockEntity<R extends Recipe<Container>> extends BlockEntity implements IMachineContainer, MenuProvider, IMachineEnergyStorage {

    private static final String PROGRESS_TIME = "ProgressTime";
    private static final String CURRENT_ENERGY = "CurrentEnergy";

    private final NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private final MachineType<R> machineType;
    private final ContainerData dataAccess;
    private R lastRecipe;
    private int progressTime;
    private int currentEnergy;
    private boolean slotChanged = true;
    private boolean changedInTick = false;

    public MachineBlockEntity(MachineType<R> machineType, BlockPos blockPos, BlockState blockState) {
        super(machineType.getBlockEntityType(), blockPos, blockState);
        this.machineType = machineType;
        this.dataAccess = new ContainerData() {
            public int get(int i) {
                switch (i) {
                    case 0:
                        return MachineBlockEntity.this.progressTime;
                    case 1:
                        return MachineBlockEntity.this.currentEnergy;
                    default:
                        return 0;
                }
            }

            public void set(int i, int j) {
                switch (i) {
                    case 0:
                        MachineBlockEntity.this.progressTime = j;
                        break;
                    case 1:
                        MachineBlockEntity.this.currentEnergy = j;
                        break;
                }
            }

            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public int[] getInputSlots() {
        return this.machineType.getInputSlots();
    }

    @Override
    public int[] getOutputSlots() {
        return this.machineType.getOutputSlots();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        this.progressTime = compoundTag.getShort(PROGRESS_TIME);
        this.currentEnergy = compoundTag.getInt(CURRENT_ENERGY);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.items);
        compoundTag.putShort(PROGRESS_TIME, (short) this.progressTime);
        compoundTag.putInt(CURRENT_ENERGY, this.currentEnergy);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    private void setChangedInTick() {
        this.changedInTick = true;
    }

    @Override
    public void setChanged() {
        super.setChanged();
    }

    @Override
    public void slotChanged() {
        this.slotChanged = true;
        IMachineContainer.super.slotChanged();
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block." + BasicMachinesMod.MOD_ID + "." + this.machineType.getMachineName());
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
        return new MachineContainerMenu<>(syncId, inventory, this.machineType, this);
    }

    private Optional<R> findRecipe() {
        if (this.lastRecipe != null && this.lastRecipe.matches(this, this.level)) {
            return Optional.of(this.lastRecipe);
        }

        if (!this.slotChanged) {
            return Optional.empty();
        }

        var recipe = this.machineType.getRecipeManager().getRecipeFor(this, this.level);
        recipe.ifPresent(r -> {
            this.progressTime = 0;
            this.lastRecipe = r;
        });
        return recipe;
    }

    private Optional<R> canProcess() {
        if (this.items.get(0).isEmpty()) {
            return Optional.empty();
        }

        var recipe = this.findRecipe();
        if (recipe.isEmpty()) {
            return Optional.empty();
        }

        if (this.getCurrentEnergy() < this.getEnergyUsagePerTick()) {
            return Optional.empty();
        }

        var result = recipe.get().assemble(this);
        if (result.isEmpty()) {
            return Optional.empty();
        }

        var output = this.items.get(1);
        if (output.isEmpty()) {
            return recipe;
        }

        if (!output.sameItem(result)) {
            return Optional.empty();
        }

        if (output.getCount() + result.getCount() <= output.getMaxStackSize()) {
            return recipe;
        }

        return Optional.empty();
    }

    private void checkProgress(R recipe) {
        if (this.progressTime >= this.machineType.getProcessingTime(recipe)) {
            this.process(recipe);
            this.hardReset();
        }

        this.progressTime++;
        this.decrementEnergy(this.getEnergyUsagePerTick());
        this.setChangedInTick();
    }

    private void process(R recipe) {
        var result = recipe.assemble(this);
        var outputSlot = this.getItem(1);
        if (outputSlot.isEmpty()) {
            this.setItem(1, result);
            this.getItem(0).shrink(1);
        } else if (outputSlot.sameItem(result) && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize()) {
            this.getItem(0).shrink(1);
            outputSlot.grow(result.getCount());
        }
    }

    private void hardReset() {
        if (this.progressTime != 0) {
            this.progressTime = 0;
            this.setChangedInTick();
        }
    }

    private void softReset() {
        if (this.progressTime < 0) {
            this.hardReset();
            return;
        }

        if (this.progressTime > 0) {
            this.progressTime--;
            this.setChangedInTick();
        }
    }

    public static <R extends Recipe<Container>> void serverTick(Level level, BlockPos blockPos, BlockState blockState, MachineBlockEntity<R> machineBlockEntity) {
        machineBlockEntity.canProcess()
                .ifPresentOrElse(machineBlockEntity::checkProgress, machineBlockEntity::softReset);

        machineBlockEntity.slotChanged = false;

        if (machineBlockEntity.changedInTick) {
            machineBlockEntity.changedInTick = false;
            machineBlockEntity.setChanged();
        }
    }

    private int getEnergyUsagePerTick() {
        return this.machineType.getEnergyUsagePerTick();
    }

    @Override
    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
        this.setChanged();
    }

    @Override
    public int getCurrentEnergy() {
        return this.currentEnergy;
    }

    @Override
    public int getMaxEnergy() {
        return 10000;
    }
}
