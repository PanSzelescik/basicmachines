package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.recipe.base.OneInputOneOutputRecipe;
import pl.panszelescik.basicmachines.api.common.recipe.serializer.CrusherRecipeSerializer;

public class CrusherRecipe extends OneInputOneOutputRecipe {

    public CrusherRecipe(Ingredient input, ItemStack output, ResourceLocation id) {
        super(input, output, id);
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return CrusherRecipeSerializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return CrusherRecipe.Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrusherRecipe> {

        public static final RecipeType<CrusherRecipe> INSTANCE = new Type();
        public static final ResourceLocation ID = BasicMachinesMod.id("crusher");

        private Type() {
        }

        public static void register() {
            BasicMachinesMod.RECIPE_TYPES.register(ID, () -> INSTANCE);
        }
    }
}
