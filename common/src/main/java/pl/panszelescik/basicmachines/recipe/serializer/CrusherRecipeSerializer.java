package pl.panszelescik.basicmachines.recipe.serializer;

import pl.panszelescik.basicmachines.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.api.common.recipe.serializer.OneInputOneOutputSerializer;

public class CrusherRecipeSerializer extends OneInputOneOutputSerializer<CrusherRecipe> {

    public static final CrusherRecipeSerializer INSTANCE = new CrusherRecipeSerializer();

    private CrusherRecipeSerializer() {
        super(CrusherRecipe::new);
    }
}
