package pl.panszelescik.basicmachines.fabric;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.fabricmc.loader.api.FabricLoader;
import pl.panszelescik.basicmachines.api.common.util.MachineBlockEntityCreator;
import pl.panszelescik.basicmachines.fabric.api.block.entity.MachineBlockEntityFabric;

import java.nio.file.Path;

public class BasicMachinesPlatformImpl {

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static <R extends Recipe<Container>> MachineBlockEntityCreator<R> getMachineBlockEntityCreator() {
        return MachineBlockEntityFabric::new;
    }

    public static String getEnergyType() {
        return "E";
    }
}
