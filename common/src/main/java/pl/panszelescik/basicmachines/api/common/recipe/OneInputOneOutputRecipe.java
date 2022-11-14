package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public abstract class OneInputOneOutputRecipe extends MachineRecipe {

    protected final IngredientWithAmount input;

    public OneInputOneOutputRecipe(IngredientWithAmount input, IngredientWithAmount output, ResourceLocation id) {
        super(output, id);
        this.input = input;
    }

    @Override
    public boolean matches(Container container, Level level) {
        if (!this.input.isValid() || !this.output.isValid()) {
            return false;
        }

        var inputStack = container.getItem(0);
        return this.input.ingredient().test(inputStack) && inputStack.getCount() >= this.input.amount();
    }

    public IngredientWithAmount getInput() {
        return this.input;
    }

    @Override
    public int getInputAmount() {
        return this.input.amount();
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return NonNullList.of(Ingredient.EMPTY, this.input.ingredient());
    }
}
