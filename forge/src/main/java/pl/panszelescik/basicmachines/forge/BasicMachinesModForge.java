package pl.panszelescik.basicmachines.forge;

import dev.architectury.platform.forge.EventBuses;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BasicMachinesMod.MOD_ID)
public class BasicMachinesModForge {

    public BasicMachinesModForge() {
        EventBuses.registerModEventBus(BasicMachinesMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BasicMachinesMod.init();
    }
}
