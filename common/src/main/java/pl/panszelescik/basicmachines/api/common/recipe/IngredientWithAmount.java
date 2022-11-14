package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;
import java.util.List;

public record IngredientWithAmount(Ingredient ingredient, int amount) {

    public IngredientWithAmount(Ingredient ingredient) {
        this(ingredient, 1);
    }

    public boolean isValid() {
        return this.ingredient.getItems().length > 0;
    }

    public List<ItemStack> toItemStackList() {
        return Arrays.stream(this.ingredient.getItems())
                .map(this::copyItemStack)
                .toList();
    }

    public ItemStack toItemStack() {
        if (!this.isValid()) {
            return ItemStack.EMPTY;
        }

        return this.copyItemStack(this.ingredient.getItems()[0]);
    }

    private ItemStack copyItemStack(ItemStack oldStack) {
        var newStack = oldStack.copy();
        newStack.setCount(this.amount);
        return newStack;
    }
}
