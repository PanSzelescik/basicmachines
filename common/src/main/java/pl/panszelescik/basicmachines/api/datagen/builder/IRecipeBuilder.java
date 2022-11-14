package pl.panszelescik.basicmachines.api.datagen.builder;

import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Consumer;

public interface IRecipeBuilder {

    void save(Consumer<FinishedRecipe> consumer);

    ResourceLocation getId();
}
