package pl.panszelescik.basicmachines.api.common.recipe.serializer;

import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.api.common.recipe.base.serializer.OneInputOneOutputSerializer;

public class CrusherRecipeSerializer extends OneInputOneOutputSerializer<CrusherRecipe> {

    public static final CrusherRecipeSerializer INSTANCE = new CrusherRecipeSerializer();

    private CrusherRecipeSerializer() {
        super(CrusherRecipe::new);
    }

    public static void register() {
        BasicMachinesMod.RECIPE_SERIALIZERS.register(CrusherRecipe.Type.ID, () -> INSTANCE);
    }
}
