package pl.panszelescik.basicmachines.fabric.datagen.provider.server;

import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ConditionJsonProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.DefaultResourceConditions;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import pl.panszelescik.basicmachines.datagen.server.BasicMachinesCommonRecipeProvider;
import pl.panszelescik.basicmachines.api.datagen.IRecipeProvider;
import pl.panszelescik.basicmachines.api.datagen.builder.IRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;

public class BasicMachinesRecipeProvider extends FabricRecipeProvider implements IRecipeProvider {

    private final BasicMachinesCommonRecipeProvider provider;

    public BasicMachinesRecipeProvider(FabricDataGenerator dataGenerator) {
        super(dataGenerator);
        this.provider = new BasicMachinesCommonRecipeProvider(dataGenerator, this);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> exporter) {
        this.provider.buildCraftingRecipes(exporter);
    }

    @Override
    public void modsLoaded(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<String> modids) {
        this.modsLoaded(consumer, builder, modids.toArray(new String[0]));
    }

    @Override
    public void modsLoaded(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, String... modids) {
        this.withConditions(consumer, builder, DefaultResourceConditions.allModsLoaded(modids));
    }

    @Override
    public void tagLocationsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<ResourceLocation> tags) {
        this.tagsExists(consumer, builder, tags.stream()
                .map(t -> TagKey.create(Registry.ITEM_REGISTRY, t))
                .toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public void tagsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<TagKey<Item>> tags) {
        this.tagsExists(consumer, builder, tags.toArray(new TagKey[0]));
    }

    @SafeVarargs
    @Override
    public final void tagsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, TagKey<Item>... tags) {
        this.withConditions(consumer, builder, DefaultResourceConditions.tagsPopulated(tags));
    }

    private void withConditions(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, ConditionJsonProvider conditions) {
        builder.save(this.withConditions(consumer, conditions));
    }
}
