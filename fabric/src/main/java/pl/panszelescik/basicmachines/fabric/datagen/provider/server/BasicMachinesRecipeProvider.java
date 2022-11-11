package pl.panszelescik.basicmachines.fabric.datagen.provider.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import pl.panszelescik.basicmachines.datagen.server.BasicMachinesCommonRecipeProvider;

import java.util.function.Consumer;

public class BasicMachinesRecipeProvider extends FabricRecipeProvider {

    private final BasicMachinesCommonRecipeProvider provider;

    public BasicMachinesRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
        this.provider = new BasicMachinesCommonRecipeProvider(dataGenerator);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
        this.provider.buildCraftingRecipes(exporter);
    }
}
