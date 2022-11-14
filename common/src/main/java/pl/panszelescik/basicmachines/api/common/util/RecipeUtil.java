package pl.panszelescik.basicmachines.api.common.util;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.crafting.Ingredient;
import pl.panszelescik.basicmachines.api.common.recipe.IngredientWithAmount;

public class RecipeUtil {

    private static final String INGREDIENT = "ingredient";
    private static final String AMOUNT = "amount";

    public static Ingredient readIngredient(JsonObject jsonObject) {
        return Ingredient.fromJson(jsonObject);
    }

    public static Ingredient readIngredient(FriendlyByteBuf friendlyByteBuf) {
        return Ingredient.fromNetwork(friendlyByteBuf);
    }

    public static IngredientWithAmount readIngredientWithAmount(JsonObject jsonObject) {
        var ingredient = readIngredient(jsonObject.get(INGREDIENT).getAsJsonObject());
        if (jsonObject.has(AMOUNT)) {
            var amount = jsonObject.get(AMOUNT).getAsInt();
            return new IngredientWithAmount(ingredient, amount);
        }
        return new IngredientWithAmount(ingredient);
    }

    public static IngredientWithAmount readIngredientWithAmount(FriendlyByteBuf friendlyByteBuf) {
        var ingredient = readIngredient(friendlyByteBuf);
        var amount = friendlyByteBuf.readVarInt();
        return new IngredientWithAmount(ingredient, amount);
    }

    public static void writeIngredient(JsonObject jsonObject, String name, Ingredient ingredient) {
        jsonObject.add(name, ingredient.toJson());
    }

    public static void writeIngredient(FriendlyByteBuf friendlyByteBuf, Ingredient ingredient) {
        ingredient.toNetwork(friendlyByteBuf);
    }

    public static void writeIngredientWithAmount(JsonObject jsonObject, String name, Ingredient ingredient, int amount) {
        writeIngredientWithAmount(jsonObject, name, new IngredientWithAmount(ingredient, amount));
    }

    public static void writeIngredientWithAmount(JsonObject jsonObject, String name, IngredientWithAmount ingredientWithAmount) {
        var ingredient = ingredientWithAmount.ingredient().toJson().getAsJsonObject();
        var amount = ingredientWithAmount.amount();

        var json = new JsonObject();
        json.add(INGREDIENT, ingredient);
        if (amount > 1) {
            json.addProperty(AMOUNT, amount);
        }

        jsonObject.add(name, json);
    }

    public static void writeIngredientWithAmount(FriendlyByteBuf friendlyByteBuf, IngredientWithAmount ingredient) {
        ingredient.ingredient().toNetwork(friendlyByteBuf);
        friendlyByteBuf.writeInt(ingredient.amount());
    }
}
