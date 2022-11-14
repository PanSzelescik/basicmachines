package pl.panszelescik.basicmachines;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.util.MachineBlockEntityCreator;
import pl.panszelescik.basicmachines.datagen.server.builder.RecipeTags;

import java.nio.file.Path;

public class BasicMachinesPlatform {

    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <R extends Recipe<Container>> MachineBlockEntityCreator<R> getMachineBlockEntityCreator() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static String getEnergyType() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isBatteryItem(ItemStack itemStack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends MachineBlockEntity<?>> void takeEnergyFromItem(T machineBlockEntity, ItemStack stack) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static TagKey<Item> getTagFromEnum(RecipeTags tag) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getOreTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getRawOreTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getDustTag(String name) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getIngotTag(String name) {
        throw new AssertionError();
    }
}
