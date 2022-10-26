package pl.panszelescik.basicmachines.forge;

import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.fml.loading.FMLPaths;
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
}
