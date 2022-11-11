package pl.panszelescik.basicmachines.datagen.server.builder;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;

public enum RecipeTags {

    DUSTS_REDSTONE,
    ENDER_PEARLS,
    GEMS_LAPIS,
    INGOTS_COPPER,
    INGOTS_GOLD,
    INGOTS_IRON,
    NUGGETS_GOLD,
    STORAGE_BLOCKS_IRON,
    ;

    public TagKey<Item> getTag() {
        return BasicMachinesPlatform.getTagFromEnum(this);
    }
}
