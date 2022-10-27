package pl.panszelescik.basicmachines.api.common.type;

import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.item.UpgradeItem;

import java.util.function.Predicate;

public enum SlotType {

    INPUT(true, true, false),
    OUTPUT(false, false, true),
    UPGRADE(UpgradeItem::isUpgrade, false, false),
    ;

    public final Predicate<ItemStack> canInsert; // Can Player insert items in GUI
    public final boolean canExtract = true; // Can Player extract items in GUI
    public final boolean canAutoInsert; // Can Pipes insert items
    public final boolean canAutoExtract; // Can Pipes extract items

    SlotType(boolean canInsert,boolean canAutoInsert, boolean canAutoExtract) {
        this(s -> canInsert, canAutoInsert, canAutoExtract);
    }

    SlotType(Predicate<ItemStack> canInsert, boolean canAutoInsert, boolean canAutoExtract) {
        this.canInsert = canInsert;
        this.canAutoInsert = canAutoInsert;
        this.canAutoExtract = canAutoExtract;
    }

    public boolean canInsert(ItemStack itemStack) {
        return this.canInsert.test(itemStack);
    }

    public boolean canAutoInsert(ItemStack itemStack) {
        return this.canAutoInsert && this.canInsert(itemStack);
    }
}
