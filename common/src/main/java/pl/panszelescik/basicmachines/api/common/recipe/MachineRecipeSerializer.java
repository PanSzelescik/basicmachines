package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public interface MachineRecipeSerializer<R extends Recipe<?>> extends RecipeSerializer<R> {

    ResourceLocation getId();
}
