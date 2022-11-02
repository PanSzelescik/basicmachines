package pl.panszelescik.basicmachines.forge;

import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.loading.FMLPaths;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.util.MachineBlockEntityCreator;
import pl.panszelescik.basicmachines.forge.api.block.entity.MachineBlockEntityForge;

import java.nio.file.Path;

public class BasicMachinesPlatformImpl {

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
        var optional = getEnergyStorage(stack);
        optional.ifPresent(energyItem -> {
            // TODO
        });
    }

    private static LazyOptional<IEnergyStorage> getEnergyStorage(ItemStack itemStack) {
        return itemStack.getCapability(ForgeCapabilities.ENERGY);
    }
}
