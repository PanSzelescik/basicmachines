package pl.panszelescik.basicmachines.api.common.block.inventory.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.type.MachineSlot;
import pl.panszelescik.basicmachines.api.common.type.SlotType;

public class MachineMenuSlot extends Slot {

    private final MachineSlot slot;

    public MachineMenuSlot(Container container, MachineSlot slot) {
        super(container, slot.id(), slot.x(), slot.y());
        this.slot = slot;
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return this.slot.canInsert(itemStack);
    }

    @Override
    public boolean mayPickup(Player player) {
        return this.slot.canExtract();
    }

    @Override
    public int getMaxStackSize() {
        var slotType = this.slot.slotType();
        return slotType == SlotType.UPGRADE || slotType == SlotType.ENERGY ? 1 : super.getMaxStackSize();
    }
}
