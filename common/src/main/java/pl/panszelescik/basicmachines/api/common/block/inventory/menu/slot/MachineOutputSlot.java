package pl.panszelescik.basicmachines.api.common.block.inventory.menu.slot;

import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class MachineOutputSlot extends Slot {

    public MachineOutputSlot(Container container, int i, int j, int k) {
        super(container, i, j, k);
    }

    @Override
    public boolean mayPlace(ItemStack itemStack) {
        return false;
    }
}
