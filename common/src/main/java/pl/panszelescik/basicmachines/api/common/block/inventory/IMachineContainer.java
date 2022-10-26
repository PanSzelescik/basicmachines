package pl.panszelescik.basicmachines.api.common.block.inventory;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

import java.util.Arrays;
import java.util.stream.IntStream;

public interface IMachineContainer extends WorldlyContainer {

    NonNullList<ItemStack> getItems();

    static IMachineContainer of(NonNullList<ItemStack> items, int[] inputSlots, int[] outputSlots) {
        return new IMachineContainer() {
            @Override
            public NonNullList<ItemStack> getItems() {
                return items;
            }

            @Override
            public int[] getInputSlots() {
                return inputSlots;
            }

            @Override
            public int[] getOutputSlots() {
                return outputSlots;
            }
        };
    }

    static IMachineContainer ofSize(int size, int[] inputSlots, int[] outputSlots) {
        return of(NonNullList.withSize(size, ItemStack.EMPTY), inputSlots, outputSlots);
    }

    static IMachineContainer fromMachineType(MachineType<?> machineType) {
        return ofSize(machineType.slotsAmount(), machineType.getInputSlots(), machineType.getOutputSlots());
    }

    @Override
    default int getContainerSize() {
        return getItems().size();
    }

    @Override
    default boolean isEmpty() {
        for (var i = 0; i < getContainerSize(); i++) {
            var stack = getItem(i);
            if (!stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    default ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    default ItemStack removeItem(int slot, int count) {
        var result = ContainerHelper.removeItem(getItems(), slot, count);
        this.slotChanged();
        return result;
    }

    @Override
    default ItemStack removeItemNoUpdate(int slot) {
        var result = ContainerHelper.takeItem(getItems(), slot);
        this.slotChanged();
        return result;
    }

    @Override
    default void setItem(int slot, ItemStack stack) {
        getItems().set(slot, stack);
        if (stack.getCount() > stack.getMaxStackSize()) {
            stack.setCount(stack.getMaxStackSize());
        }
        this.slotChanged();
    }

    @Override
    default void setChanged() {
    }

    default void slotChanged() {
        this.setChanged();
    }

    @Override
    default boolean stillValid(Player player) {
        return true;
    }

    @Override
    default void clearContent() {
        getItems().clear();
        this.slotChanged();
    }

    @Override
    default int[] getSlotsForFace(Direction direction) {
        return getAllSlots().toArray();
    }

    default boolean canPlaceItem(int i) {
        return Arrays.stream(getInputSlots()).anyMatch(inputSlot -> inputSlot == i);
    }

    @Override
    default boolean canPlaceItem(int i, ItemStack itemStack) {
        return canPlaceItem(i);
    }

    @Override
    default boolean canPlaceItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return canPlaceItem(i);
    }

    default boolean canTakeItem(int i) {
        return Arrays.stream(getOutputSlots()).anyMatch(outputSlot -> outputSlot == i);
    }

    @Override
    default boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return canTakeItem(i);
    }

    default IntStream getAllSlots() {
        return IntStream.concat(
                Arrays.stream(getInputSlots()),
                Arrays.stream(getOutputSlots())
        );
    }

    int[] getInputSlots();

    int[] getOutputSlots();
}
