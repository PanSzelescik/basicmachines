package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class MachineRecipe implements Recipe<Container> {

    protected final IngredientWithAmount output;
    protected final ResourceLocation id;

    public MachineRecipe(IngredientWithAmount output, ResourceLocation id) {
        this.output = output;
        this.id = id;
    }

    public abstract int getInputAmount();

    public IngredientWithAmount getOutput() {
        return this.output;
    }

    @Override
    public ItemStack assemble(Container container) {
        return this.getResultItem().copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output.toItemStack();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
