package pl.panszelescik.basicmachines.api.common.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.api.common.recipe.IngredientWithAmount;
import pl.panszelescik.basicmachines.api.common.recipe.MachineRecipeSerializer;
import pl.panszelescik.basicmachines.api.common.recipe.OneInputOneOutputRecipe;
import pl.panszelescik.basicmachines.api.common.util.RecipeUtil;

public abstract class OneInputOneOutputSerializer<R extends OneInputOneOutputRecipe> implements MachineRecipeSerializer<R> {

    private static final Gson GSON = new GsonBuilder().create();
    private final OneInputOneOutputRecipeCreator<R> creator;

    protected OneInputOneOutputSerializer(OneInputOneOutputRecipeCreator<R> creator) {
        this.creator = creator;
    }

    @Override
    public R fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        var recipeJson = GSON.fromJson(jsonObject, OneInputOneOutputJson.class);

        var input = RecipeUtil.readIngredientWithAmount(recipeJson.input);
        var output = RecipeUtil.readIngredientWithAmount(recipeJson.output);

        return this.creator.create(input, output, resourceLocation);
    }

    @Override
    public R fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        var input = RecipeUtil.readIngredientWithAmount(friendlyByteBuf);
        var output = RecipeUtil.readIngredientWithAmount(friendlyByteBuf);

        return this.creator.create(input, output, resourceLocation);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, R recipe) {
        RecipeUtil.writeIngredientWithAmount(friendlyByteBuf, recipe.getInput());
        RecipeUtil.writeIngredientWithAmount(friendlyByteBuf, recipe.getOutput());
    }

    @FunctionalInterface
    public interface OneInputOneOutputRecipeCreator<R extends OneInputOneOutputRecipe> {

        R create(IngredientWithAmount input, IngredientWithAmount output, ResourceLocation id);
    }

    public static class OneInputOneOutputJson {
        private JsonObject input;
        private JsonObject output;
        private int amount;
    }
}
