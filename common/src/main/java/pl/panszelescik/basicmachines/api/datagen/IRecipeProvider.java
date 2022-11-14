package pl.panszelescik.basicmachines.api.datagen;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import pl.panszelescik.basicmachines.api.datagen.builder.IRecipeBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public interface IRecipeProvider {

    default void modsLoaded(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, String... modids) {
        this.modsLoaded(consumer, builder, Arrays.asList(modids));
    }

    void modsLoaded(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<String> modids);

    default void tagLocationsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, ResourceLocation... tags) {
        this.tagLocationsExists(consumer, builder, Arrays.asList(tags));
    }

    void tagLocationsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<ResourceLocation> tags);

    default void tagsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, TagKey<Item>... tags) {
        this.tagsExists(consumer, builder, Arrays.asList(tags));
    }

    void tagsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<TagKey<Item>> tags);
}
