package pl.panszelescik.basicmachines.api.common.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;

public class UpgradeItem extends Item {

    private final UpgradeType upgradeType;

    public UpgradeItem(UpgradeType upgradeType) {
        this(upgradeType, new Item.Properties());
    }

    public UpgradeItem(UpgradeType upgradeType, Properties properties) {
        super(properties.tab(BasicMachinesItems.CREATIVE_TAB));
        this.upgradeType = upgradeType;
    }

    public static boolean isUpgrade(ItemStack itemStack) {
        return itemStack.getItem() instanceof UpgradeItem;
    }
}
