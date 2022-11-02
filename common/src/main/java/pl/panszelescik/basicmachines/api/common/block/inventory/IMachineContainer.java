package pl.panszelescik.basicmachines.api.common.block.inventory;

import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.api.common.type.SlotHolder;

public interface IMachineContainer extends WorldlyContainer {

    static IMachineContainer of(NonNullList<ItemStack> items, SlotHolder slotHolder) {
        return new IMachineContainer() {
            @Override
            public NonNullList<ItemStack> getItems() {
                return items;
            }

            @Override
            public SlotHolder getSlotHolder() {
                return slotHolder;
            }
        };
    }

    static IMachineContainer fromSlotHolder(SlotHolder slotHolder) {
        return of(NonNullList.withSize(slotHolder.getCount(), ItemStack.EMPTY), slotHolder);
    }

    static IMachineContainer fromMachineType(MachineType<?> machineType) {
        return fromSlotHolder(machineType.getSlotHolder());
    }

    NonNullList<ItemStack> getItems();

    SlotHolder getSlotHolder();

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
        return getSlotHolder().getSlots();
    }

    @Override
    default boolean canPlaceItem(int i, ItemStack itemStack) {
        return getSlotHolder().getSlot(i).canAutoInsert(itemStack);
    }

    @Override
    default boolean canPlaceItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return canPlaceItem(i, itemStack);
    }

    default boolean canTakeItem(int i) {
        return getSlotHolder().getSlot(i).canAutoExtract();
    }

    @Override
    default boolean canTakeItemThroughFace(int i, ItemStack itemStack, Direction direction) {
        return canTakeItem(i);
    }
}
