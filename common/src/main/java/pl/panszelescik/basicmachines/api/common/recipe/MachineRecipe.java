package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;

public abstract class MachineRecipe implements Recipe<Container> {

    protected final ItemStack output;
    protected final ResourceLocation id;

    public MachineRecipe(ItemStack output, ResourceLocation id) {
        this.output = output;
        this.id = id;
    }

    @Override
    public ItemStack assemble(Container container) {
        return this.output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int i, int j) {
        return true;
    }

    @Override
    public ItemStack getResultItem() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }
}
