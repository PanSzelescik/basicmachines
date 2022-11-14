package pl.panszelescik.basicmachines.forge.datagen.provider.server;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.datagen.IRecipeProvider;
import pl.panszelescik.basicmachines.api.datagen.builder.IRecipeBuilder;

import java.util.List;
import java.util.function.Consumer;

public class BasicMachinesRecipeProvider implements IRecipeProvider {

    @Override
    public void modsLoaded(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<String> modids) {
        this.withCondition(consumer, builder, modids.stream()
                .map(ModLoadedCondition::new)
                .toList());
    }

    @Override
    public void tagLocationsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<ResourceLocation> tags) {
        this.withCondition(consumer, builder, tags.stream()
                .map(TagEmptyCondition::new)
                .map(NotCondition::new)
                .toList());
    }

    @Override
    public final void tagsExists(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<TagKey<Item>> tags) {
        this.tagLocationsExists(consumer, builder, tags.stream()
                .map(TagKey::location)
                .toList());
    }

    private void withCondition(Consumer<FinishedRecipe> consumer, IRecipeBuilder builder, List<? extends ICondition> conditions) {
        var recipeBuilder = ConditionalRecipe.builder();

        for (var condition : conditions) {
            recipeBuilder.addCondition(condition);
        }

        recipeBuilder.addRecipe(builder::save)
                .generateAdvancement(BasicMachinesMod.id("recipes/" + builder.getId().getPath())) // TODO?
                .build(consumer, builder.getId());
    }
}
