package pl.panszelescik.basicmachines.api.common.recipe;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;

public abstract class OneInputOneOutputRecipe extends MachineRecipe {

    protected final Ingredient input;

    public OneInputOneOutputRecipe(Ingredient input, ItemStack output, ResourceLocation id) {
        super(output, id);
        this.input = input;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return this.input.test(container.getItem(0));
    }

    public Ingredient getInput() {
        return this.input;
    }
}
