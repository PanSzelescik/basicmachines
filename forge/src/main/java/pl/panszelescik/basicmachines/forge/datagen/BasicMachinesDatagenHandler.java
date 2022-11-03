package pl.panszelescik.basicmachines.forge.datagen;

import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.forge.datagen.provider.client.BasicMachinesBlockModelsProvider;
import pl.panszelescik.basicmachines.forge.datagen.provider.client.BasicMachinesBlockstateProvider;
import pl.panszelescik.basicmachines.forge.datagen.provider.client.BasicMachinesLangProvider;

@Mod.EventBusSubscriber(modid = BasicMachinesMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BasicMachinesDatagenHandler {

    @SubscribeEvent
    public static void dataGenEvent(GatherDataEvent event) {
        var gen = event.getGenerator();
        var efh = event.getExistingFileHelper();

        if (event.includeClient()) {
            gen.addProvider(true, new BasicMachinesBlockModelsProvider(gen, efh));
            gen.addProvider(true, new BasicMachinesBlockstateProvider(gen, efh));
            gen.addProvider(true, new BasicMachinesLangProvider(gen));
        }
    }
}
