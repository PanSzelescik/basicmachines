package pl.panszelescik.basicmachines.api.common.block.inventory.menu;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.api.common.block.inventory.IMachineContainer;
import pl.panszelescik.basicmachines.api.common.type.MachineSlot;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public class MachineContainerMenu<R extends Recipe<Container>> extends AbstractContainerMenu {

    public final MachineType<R> machineType;
    private final IMachineContainer container;
    private final ContainerData data;

    public MachineContainerMenu(int syncId, Inventory inventory, MachineType<R> machineType) {
        this(syncId, inventory, machineType, IMachineContainer.fromMachineType(machineType), new SimpleContainerData(4));
    }

    public MachineContainerMenu(int syncId, Inventory inventory, MachineType<R> machineType, IMachineContainer container, ContainerData containerData) {
        super(machineType.getMenuType(), syncId);

        this.machineType = machineType;

        checkContainerSize(container, machineType.getSlotHolder().getCount());
        this.container = container;
        this.addMachineSlots(machineType);
        this.addPlayerSlots(inventory);

        checkContainerDataCount(containerData, 4);
        this.data = containerData;
        this.addDataSlots(containerData);

        inventory.startOpen(inventory.player);
    }

    private void addMachineSlots(MachineType<R> machineType) {
        machineType.getSlotHolder()
                .getSlotsStream()
                .map(slot -> slot.toMenuSlot(this.container))
                .forEach(this::addSlot);
    }

    private void addPlayerSlots(Inventory inventory) {
        int m;
        int l;

        // Player Inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(inventory, l + m * 9 + 9, 8 + l * MachineSlot.SIZE, 84 + m * MachineSlot.SIZE));
            }
        }

        // Player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(inventory, m, 8 + m * MachineSlot.SIZE, 142));
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

    public int getProgress() {
        return this.data.get(0);
    }

    public int getProcessingTime() {
        return this.data.get(1);
    }

    public int getCurrentEnergy() {
        return this.data.get(2);
    }

    public int getMaxEnergy() {
        return this.data.get(3);
    }

    public int getProcessingProgress() {
        float progress = this.getProgress();
        float total = this.getProcessingTime();
        if (total <= 0 || progress <= 0) {
            return 0;
        }
        return (int) (progress * (float) 24 / total);
    }

    public int getEnergyBar() {
        float current = this.getCurrentEnergy();
        float max = this.getMaxEnergy();
        if (max <= 0 || current <= 0) {
            return 0;
        }
        return (int) (current * (float) 42 / max);
    }
}
