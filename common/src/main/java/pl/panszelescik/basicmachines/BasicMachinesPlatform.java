package pl.panszelescik.basicmachines;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.api.common.util.MachineBlockEntityCreator;

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
}
