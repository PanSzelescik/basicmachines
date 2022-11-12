package pl.panszelescik.basicmachines.recipe.serializer;

import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.api.common.recipe.serializer.OneInputOneOutputSerializer;

public class CrusherRecipeSerializer extends OneInputOneOutputSerializer<CrusherRecipe> {

    public static final CrusherRecipeSerializer INSTANCE = new CrusherRecipeSerializer();
    private static final ResourceLocation ID = BasicMachinesMod.id("crusher");

    private CrusherRecipeSerializer() {
        super(CrusherRecipe::new);
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }
}
