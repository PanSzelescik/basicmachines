package pl.panszelescik.basicmachines.forge.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.forge.api.util.LazyOptionalUtils;

public class MachineBlockEntityForge<R extends Recipe<Container>> extends MachineBlockEntity<R> implements ICapabilityProvider, IEnergyStorage, IItemHandlerModifiable {

    private final LazyOptional<MachineBlockEntityForge<R>> holder;

    public MachineBlockEntityForge(MachineType<R> machineType, BlockPos blockPos, BlockState blockState) {
        super(machineType, blockPos, blockState);
        this.holder = LazyOptional.of(() -> this);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction direction) {
        return LazyOptionalUtils.or(
                ForgeCapabilities.ENERGY.orEmpty(cap, this.holder.cast()),
                () -> ForgeCapabilities.ITEM_HANDLER.orEmpty(cap, this.holder.cast())
        );
    }

    // Energy
    @Override
    public int receiveEnergy(int i, boolean bl) {
        return this.inputEnergy(i, bl);
    }

    @Override
    public int extractEnergy(int i, boolean bl) {
        return this.outputEnergy(i, bl);
    }

    @Override
    public int getEnergyStored() {
        return this.getCurrentEnergy();
    }

    @Override
    public int getMaxEnergyStored() {
        return this.getMaxEnergy();
    }

    @Override
    public boolean canExtract() {
        return this.canOutputEnergy();
    }

    @Override
    public boolean canReceive() {
        return this.canInputEnergy();
    }

    @Override
    public int getSlots() {
        return this.getContainerSize();
    }

    // Item
    @Override
    public @NotNull ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate) {
        if (stack.isEmpty()) {
            return ItemStack.EMPTY;
        }

        if (!this.isItemValid(slot, stack)) {
            return stack;
        }

        this.validateSlotIndex(slot);
        var existing = this.getStackInSlot(slot);
        var limit = this.getStackLimit(slot, stack);
        if (!existing.isEmpty()) {
            if (!ItemHandlerHelper.canItemStacksStack(stack, existing)) {
                return stack;
            }

            limit -= existing.getCount();
        }

        if (limit <= 0) {
            return stack;
        }

        var reachedLimit = stack.getCount() > limit;
        if (!simulate) {
            if (existing.isEmpty()) {
                this.setStackInSlot(slot, reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, limit) : stack);
            } else {
                existing.grow(reachedLimit ? limit : stack.getCount());
            }

            this.setChanged();
        }

        return reachedLimit ? ItemHandlerHelper.copyStackWithSize(stack, stack.getCount() - limit) : ItemStack.EMPTY;
    }

    @Override
    public @NotNull ItemStack extractItem(int slot, int amount, boolean simulate) {
        if (amount <= 0) {
            return ItemStack.EMPTY;
        }

        if (!this.canTakeItem(slot)) {
            return ItemStack.EMPTY;
        }

        this.validateSlotIndex(slot);
        var existing = this.getStackInSlot(slot);
        if (existing.isEmpty()) {
            return ItemStack.EMPTY;
        }

        var toExtract = Math.min(amount, existing.getMaxStackSize());
        if (existing.getCount() <= toExtract) {
            if (!simulate) {
                this.setStackInSlot(slot, ItemStack.EMPTY);
                this.setChanged();
                return existing;
            }

            return existing.copy();
        }

        if (!simulate) {
            this.setStackInSlot(slot, ItemHandlerHelper.copyStackWithSize(existing, existing.getCount() - toExtract));
            this.setChanged();
        }

        return ItemHandlerHelper.copyStackWithSize(existing, toExtract);
    }

    @Override
    public @NotNull ItemStack getStackInSlot(int i) {
        return this.getItem(i);
    }

    @Override
    public int getSlotLimit(int i) {
        return this.getMaxStackSize();
    }

    @Override
    public boolean isItemValid(int i, @NotNull ItemStack arg) {
        return this.canPlaceItem(i, arg);
    }

    @Override
    public void setStackInSlot(int i, @NotNull ItemStack arg) {
        this.setItem(i, arg);
    }

    private int getStackLimit(int slot, @NotNull ItemStack stack) {
        return Math.min(this.getSlotLimit(slot), stack.getMaxStackSize());
    }

    private void validateSlotIndex(int slot) {
        if (slot < 0 || slot >= this.getContainerSize()) {
            throw new RuntimeException("Slot " + slot + " not in valid range - [0," + this.getContainerSize() + ")");
        }
    }
}
