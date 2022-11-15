package pl.panszelescik.basicmachines.forge;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.loading.FMLPaths;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.util.MachineBlockEntityCreator;
import pl.panszelescik.basicmachines.datagen.server.builder.RecipeTags;
import pl.panszelescik.basicmachines.forge.api.block.entity.MachineBlockEntityForge;

import java.nio.file.Path;

public class BasicMachinesPlatformImpl {

    private static final String FORGE = "forge";

    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }

    public static <R extends Recipe<Container>> MachineBlockEntityCreator<R> getMachineBlockEntityCreator() {
        return MachineBlockEntityForge::new;
    }

    public static String getEnergyType() {
        return "FE";
    }

    public static boolean isBatteryItem(ItemStack itemStack) {
        return getEnergyStorage(itemStack).isPresent();
    }

    public static <T extends MachineBlockEntity<?>> void takeEnergyFromItem(T machineBlockEntity, ItemStack stack) {
        getEnergyStorage(stack)
                .ifPresent(energyItem -> machineBlockEntity.inputEnergy(energyItem.extractEnergy(machineBlockEntity.getInputMaxEnergy(), false), false));
    }

    public static TagKey<Item> getTagFromEnum(RecipeTags tag) {
        return switch (tag) {
            case DUSTS_REDSTONE -> Tags.Items.DUSTS_REDSTONE;
            case ENDER_PEARLS -> Tags.Items.ENDER_PEARLS;
            case GEMS_LAPIS -> Tags.Items.GEMS_LAPIS;
            case INGOTS_COPPER -> Tags.Items.INGOTS_COPPER;
            case INGOTS_GOLD -> Tags.Items.INGOTS_GOLD;
            case INGOTS_IRON -> Tags.Items.INGOTS_IRON;
            case NUGGETS_GOLD -> Tags.Items.NUGGETS_GOLD;
            case STORAGE_BLOCKS_IRON -> Tags.Items.STORAGE_BLOCKS_IRON;
        };
    }

    public static ResourceLocation getOreTag(String name) {
        return new ResourceLocation(FORGE, "ores/" + name);
    }

    public static ResourceLocation getRawOreTag(String name) {
        return new ResourceLocation(FORGE, "raw_materials/" + name);
    }

    public static ResourceLocation getDustTag(String name) {
        return new ResourceLocation(FORGE, "dusts/" + name);
    }

    public static ResourceLocation getIngotTag(String name) {
        return new ResourceLocation(FORGE, "ingots/" + name);
    }

    private static LazyOptional<IEnergyStorage> getEnergyStorage(ItemStack itemStack) {
        return itemStack.getCapability(ForgeCapabilities.ENERGY);
    }
}
