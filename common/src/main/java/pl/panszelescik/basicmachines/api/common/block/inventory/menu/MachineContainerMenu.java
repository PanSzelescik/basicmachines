package pl.panszelescik.basicmachines.api.common.block.inventory.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.api.common.block.inventory.IMachineContainer;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.slot.MachineOutputSlot;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public class MachineContainerMenu<R extends Recipe<Container>> extends AbstractContainerMenu {

    private final IMachineContainer container;

    public MachineContainerMenu(int syncId, Inventory inventory, MachineType<R> machineType) {
        this(syncId, inventory, machineType, IMachineContainer.fromMachineType(machineType));
    }

    public MachineContainerMenu(int syncId, Inventory inventory, MachineType<R> machineType, IMachineContainer container) {
        super(machineType.getMenuType(), syncId);
        checkContainerSize(container, machineType.slotsAmount());
        this.container = container;
        inventory.startOpen(inventory.player);

        this.addMachineSlots();
        this.addPlayerSlots(inventory);
    }

    private void addMachineSlots() {
        this.addSlot(new Slot(this.container, 0, 62, 17));
        this.addSlot(new MachineOutputSlot(this.container, 1, 62, 35));
    }

    private void addPlayerSlots(Inventory inventory) {
        int m;
        int l;

        // Player Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }

        // Player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int invSlot) {
        var newStack = ItemStack.EMPTY;
        var slot = this.slots.get(invSlot);
        if (slot != null && slot.hasItem()) {
            var originalStack = slot.getItem();
            newStack = originalStack.copy();
            if (invSlot < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(originalStack, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(originalStack, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            slot.onTake(player, originalStack);
        }

        return newStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }
}
