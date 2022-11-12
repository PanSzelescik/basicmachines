package pl.panszelescik.basicmachines.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import pl.panszelescik.basicmachines.fabric.datagen.provider.client.BasicMachinesLangProvider;
import pl.panszelescik.basicmachines.fabric.datagen.provider.client.BasicMachinesModelsProvider;
import pl.panszelescik.basicmachines.fabric.datagen.provider.server.BasicMachinesRecipeProvider;

public class BasicMachinesFabricDatagen implements DataGeneratorEntrypoint {

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        fabricDataGenerator.addProvider(BasicMachinesLangProvider::new);
        fabricDataGenerator.addProvider(BasicMachinesModelsProvider::new);
        fabricDataGenerator.addProvider(BasicMachinesRecipeProvider::new);
    }
}
