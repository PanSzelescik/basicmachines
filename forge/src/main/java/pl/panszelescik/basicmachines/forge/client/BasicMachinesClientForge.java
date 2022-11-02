package pl.panszelescik.basicmachines.forge.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

@Mod.EventBusSubscriber(modid = BasicMachinesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BasicMachinesClientForge {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        MachineType.registerAllClient();
    }
}
