package pl.panszelescik.basicmachines.api.common.block.inventory.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.type.MachineSlot;

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
}
