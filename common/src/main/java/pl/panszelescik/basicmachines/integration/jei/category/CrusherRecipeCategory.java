package pl.panszelescik.basicmachines.integration.jei.category;

import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;
import pl.panszelescik.basicmachines.integration.jei.category.base.OneInputOneOutputRecipeCategory;
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.recipe.serializer.CrusherRecipeSerializer;

public class CrusherRecipeCategory extends OneInputOneOutputRecipeCategory<CrusherRecipe> {

    public static RecipeType<CrusherRecipe> TYPE = new RecipeType<>(CrusherRecipeSerializer.INSTANCE.getId(), CrusherRecipe.class);

    public CrusherRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, BasicMachinesTypes.ELECTRIC_CRUSHER);
    }

    @Override
    public RecipeType<CrusherRecipe> getRecipeType() {
        return TYPE;
    }
}
