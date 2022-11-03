package pl.panszelescik.basicmachines.api.common.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public class RecipeUtil {

    public static Ingredient readIngredient(JsonObject jsonObject) {
        return Ingredient.fromJson(jsonObject);
    }

    public static Ingredient readIngredient(FriendlyByteBuf friendlyByteBuf) {
        return Ingredient.fromNetwork(friendlyByteBuf);
    }

    public static void writeIngredient(FriendlyByteBuf friendlyByteBuf, Ingredient ingredient) {
        ingredient.toNetwork(friendlyByteBuf);
    }

    public static ItemStack readItemStack(String name, int amount) {
        return new ItemStack(Registry.ITEM
                .getOptional(new ResourceLocation(name))
                .orElseThrow(() -> new JsonSyntaxException("No such item " + name)), amount <= 0 ? 1 : amount);
    }

    public static ItemStack readItemStack(FriendlyByteBuf friendlyByteBuf) {
        return friendlyByteBuf.readItem();
    }

    public static void writeItemStack(FriendlyByteBuf friendlyByteBuf, ItemStack itemStack) {
        friendlyByteBuf.writeItem(itemStack);
    }
}
