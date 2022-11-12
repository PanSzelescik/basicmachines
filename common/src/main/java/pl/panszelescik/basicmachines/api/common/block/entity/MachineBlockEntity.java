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
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.api.client.sound.MachineSoundInstance;
import pl.panszelescik.basicmachines.api.common.block.MachineBlock;
import pl.panszelescik.basicmachines.api.common.block.energy.IMachineEnergyStorage;
import pl.panszelescik.basicmachines.api.common.block.inventory.IMachineContainer;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.api.common.type.SlotHolder;
import pl.panszelescik.basicmachines.api.common.type.SlotType;

public class MachineBlockEntity<R extends Recipe<Container>> extends BlockEntity implements IMachineContainer, MenuProvider, IMachineEnergyStorage {

    public static final int DATA_COUNT = 6;
    public static final String ITEMS = "Items";
    public static final String PROGRESS = "Progress";
    public static final String PROCESSING_TIME = "Total";
    public static final String ENERGY = "Energy";
    public static final String MAX_ENERGY = "MaxEnergy";
    public static final String IS_PROCESSING = "IsProcessing";

    public final MachineType<R> machineType;
    private final NonNullList<ItemStack> items;
    private final Component component;
    private final ContainerData dataAccess;
    private R lastRecipe;
    private int progressTime;
    private int currentEnergy;
    private boolean slotChanged = true;
    private boolean changedInTick = false;
    public boolean isProcessing = false;

    public MachineBlockEntity(MachineType<R> machineType, BlockPos blockPos, BlockState blockState) {
        super(machineType.getBlockEntityType(), blockPos, blockState);
        this.machineType = machineType;
        this.items = NonNullList.withSize(machineType.getSlotHolder().getCount(), ItemStack.EMPTY);
        this.component = Component.translatable("block." + BasicMachinesMod.MOD_ID + "." + this.machineType.getName());
        this.dataAccess = new ContainerData() {
            public int get(int i) {
                return switch (i) {
                    case 0 -> MachineBlockEntity.this.getProgress();
                    case 1 -> MachineBlockEntity.this.getProcessingTime();
                    case 2 -> MachineBlockEntity.this.getCurrentEnergy();
                    case 3 -> MachineBlockEntity.this.getMaxEnergy();
                    case 4 -> MachineBlockEntity.this.getEnergyUsagePerTick();
                    case 5 -> MachineBlockEntity.this.isProcessing ? 1 : 0;
                    default -> 0;
                };
            }

            public void set(int i, int j) {
                switch (i) {
                    case 0 -> MachineBlockEntity.this.progressTime = j;
                    case 2 -> MachineBlockEntity.this.currentEnergy = j;
                    case 5 -> MachineBlockEntity.this.isProcessing = j == 1;
                }
            }

            public int getCount() {
                return DATA_COUNT;
            }
        };
    }

    public static <R extends Recipe<Container>> void serverTick(Level level, BlockPos blockPos, BlockState blockState, MachineBlockEntity<R> machineBlockEntity) {
        machineBlockEntity.takeEnergyFromItem();

        if (machineBlockEntity.canProcess()) {
            machineBlockEntity.isProcessing = true;
            machineBlockEntity.checkProgress();
        } else {
            machineBlockEntity.isProcessing = false;
            machineBlockEntity.softReset();
        }

        machineBlockEntity.slotChanged = false;

        if (blockState.getValue(MachineBlock.LIT) != machineBlockEntity.isProcessing) {
            level.setBlock(blockPos, blockState.setValue(MachineBlock.LIT, machineBlockEntity.isProcessing), 3);
            machineBlockEntity.changedInTick = true;
        }

        if (machineBlockEntity.changedInTick) {
            machineBlockEntity.changedInTick = false;
            machineBlockEntity.setChanged();
        }
    }

    public static <R extends Recipe<Container>> void clientTick(Level level, BlockPos blockPos, BlockState blockState, MachineBlockEntity<R> machineBlockEntity) {
        if (machineBlockEntity.machineType.getSoundEvent() != null && machineBlockEntity.isProcessing) {
            MachineSoundInstance.playSound(machineBlockEntity);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    @Override
    public SlotHolder getSlotHolder() {
        return this.machineType.getSlotHolder();
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        ContainerHelper.loadAllItems(compoundTag, this.items);
        this.progressTime = compoundTag.getShort(PROGRESS);
        this.currentEnergy = compoundTag.getInt(ENERGY);
        this.isProcessing = compoundTag.getBoolean(IS_PROCESSING);
    }

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        ContainerHelper.saveAllItems(compoundTag, this.items);
        compoundTag.putShort(PROGRESS, (short) this.progressTime);
        compoundTag.putInt(ENERGY, this.currentEnergy);
        compoundTag.putBoolean(IS_PROCESSING, this.isProcessing);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }

    public int getProgress() {
        return this.progressTime;
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
        return this.component;
    }

    @Override
    public AbstractContainerMenu createMenu(int syncId, Inventory inventory, Player player) {
        return new MachineContainerMenu<>(syncId, inventory, this.machineType, this, this.dataAccess);
    }

    private int getEnergyUsagePerTick() {
        return this.machineType.getEnergyUsagePerTick();
    }

    @Override
    public int getCurrentEnergy() {
        return this.currentEnergy;
    }

    @Override
    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
        this.setChanged();
    }

    @Override
    public int getMaxEnergy() {
        return 10000;
    }

    private void takeEnergyFromItem() {
        var items = this.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (this.getSlotHolder().getSlot(i).slotType() == SlotType.ENERGY) {
                var stack = items.get(i);
                if (stack.isEmpty()) {
                    return;
                }

                BasicMachinesPlatform.takeEnergyFromItem(this, stack);
            }
        }
    }

    private boolean findRecipe() {
        if (this.lastRecipe != null && this.lastRecipe.matches(this, this.level)) {
            return true;
        }

        if (!this.slotChanged) {
            return false;
        }

        var recipe = this.machineType.getRecipeManager().getRecipeFor(this, this.level);
        recipe.ifPresent(r -> {
            this.progressTime = 0;
            this.lastRecipe = r;
        });
        return recipe.isPresent();
    }

    private boolean canProcess() {
        if (this.items.get(0).isEmpty()) {
            return false;
        }

        var foundRecipe = this.findRecipe();
        if (!foundRecipe) {
            return false;
        }

        if (this.getCurrentEnergy() < this.getEnergyUsagePerTick()) {
            return false;
        }

        var result = this.lastRecipe.assemble(this);
        if (result.isEmpty()) {
            return false;
        }

        var output = this.items.get(1);
        if (output.isEmpty()) {
            return true;
        }

        if (!output.sameItem(result)) {
            return false;
        }

        return output.getCount() + result.getCount() <= output.getMaxStackSize();
    }

    private void checkProgress() {
        if (this.progressTime >= this.getProcessingTime()) {
            this.process();
            this.hardReset();
        }

        this.progressTime++;
        this.decrementEnergy(this.getEnergyUsagePerTick());
        this.setChangedInTick();
    }

    private void process() {
        var result = this.lastRecipe.assemble(this);
        var outputSlot = this.getItem(1);
        if (outputSlot.isEmpty()) {
            this.setItem(1, result);
            this.getItem(0).shrink(1);
        } else if (outputSlot.sameItem(result) && outputSlot.getCount() + result.getCount() <= outputSlot.getMaxStackSize()) {
            this.getItem(0).shrink(1);
            outputSlot.grow(result.getCount());
        }
    }

    public int getProcessingTime() {
        return !this.isProcessing || this.lastRecipe == null ? -1 : this.machineType.getProcessingTime(this.lastRecipe);
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
}
