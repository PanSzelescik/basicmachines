package pl.panszelescik.basicmachines.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import pl.panszelescik.basicmachines.api.common.recipe.OneInputOneOutputRecipe;
import pl.panszelescik.basicmachines.recipe.serializer.CrusherRecipeSerializer;

public class CrusherRecipe extends OneInputOneOutputRecipe {

    public static final RecipeType<CrusherRecipe> TYPE = new RecipeType<>() {};

    public CrusherRecipe(Ingredient input, ItemStack output, ResourceLocation id) {
        super(input, output, id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrusherRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return TYPE;
    }
}
