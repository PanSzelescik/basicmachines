package pl.panszelescik.basicmachines.api.common.recipe.serializer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import pl.panszelescik.basicmachines.api.common.recipe.OneInputOneOutputRecipe;
import pl.panszelescik.basicmachines.api.common.util.RecipeUtil;

public abstract class OneInputOneOutputSerializer<R extends OneInputOneOutputRecipe> implements RecipeSerializer<R> {

    private static final Gson GSON = new GsonBuilder().create();
    private final OneInputOneOutputRecipeCreator<R> creator;

    protected OneInputOneOutputSerializer(OneInputOneOutputRecipeCreator<R> creator) {
        this.creator = creator;
    }

    @Override
    public R fromJson(ResourceLocation resourceLocation, JsonObject jsonObject) {
        var recipeJson = GSON.fromJson(jsonObject, OneInputOneOutputJson.class);

        var input = RecipeUtil.readIngredient(recipeJson.input);
        var output = RecipeUtil.readItemStack(recipeJson.output, recipeJson.amount);

        return this.creator.create(input, output, resourceLocation);
    }

    @Override
    public R fromNetwork(ResourceLocation resourceLocation, FriendlyByteBuf friendlyByteBuf) {
        var input = RecipeUtil.readIngredient(friendlyByteBuf);
        var output = RecipeUtil.readItemStack(friendlyByteBuf);

        return this.creator.create(input, output, resourceLocation);
    }

    @Override
    public void toNetwork(FriendlyByteBuf friendlyByteBuf, R recipe) {
        RecipeUtil.writeIngredient(friendlyByteBuf, recipe.getInput());
        RecipeUtil.writeItemStack(friendlyByteBuf, recipe.getResultItem());
    }

    @FunctionalInterface
    public interface OneInputOneOutputRecipeCreator<R extends OneInputOneOutputRecipe> {

        R create(Ingredient input, ItemStack output, ResourceLocation id);
    }

    public static class OneInputOneOutputJson {
        private JsonObject input;
        private String output;
        private int amount;
    }
}
