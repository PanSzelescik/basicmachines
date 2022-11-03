package pl.panszelescik.basicmachines;

import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.init.*;

public class BasicMachinesMod {

    public static final String MOD_ID = "basicmachines";

    public static void init() {
        BasicMachinesBlocks.register();
        BasicMachinesItems.register();
        BasicMachinesRecipes.register();
        BasicMachinesSounds.register();
        BasicMachinesTypes.register();
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(BasicMachinesMod.MOD_ID, path);
    }
}
