package pl.panszelescik.basicmachines.integration.jei.category.base;

import com.mojang.blaze3d.vertex.PoseStack;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableStatic;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import pl.panszelescik.basicmachines.api.common.recipe.OneInputOneOutputRecipe;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public abstract class OneInputOneOutputRecipeCategory<R extends OneInputOneOutputRecipe> implements IRecipeCategory<R> {

    private static final int MINUS_X = 43;
    private static final int MINUS_Y = 24;

    private final MachineType<R> machineType;
    private final IDrawableStatic background;
    private final IDrawable icon;

    public OneInputOneOutputRecipeCategory(IGuiHelper guiHelper, MachineType<R> machineType) {
        this.machineType = machineType;
        this.background = guiHelper.createDrawable(machineType.getSlotHolder().getTexture(), MINUS_X, MINUS_Y, 107, 38);
        this.icon = guiHelper.createDrawableItemStack(machineType.getItemStack());
    }

    @Override
    public Component getTitle() {
        return this.machineType.getComponent();
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, R recipe, IFocusGroup focuses) {
        var inputSlot = this.machineType.getSlotHolder().getSlot(0);
        builder.addSlot(RecipeIngredientRole.INPUT, inputSlot.x() - MINUS_X, inputSlot.y() - MINUS_Y)
                .setSlotName("input")
                .addItemStacks(recipe.getInput().toItemStackList());

        var outputSlot = this.machineType.getSlotHolder().getSlot(1);
        builder.addSlot(RecipeIngredientRole.OUTPUT, outputSlot.x() - MINUS_X, outputSlot.y() - MINUS_Y)
                .setSlotName("output")
                .addItemStack(recipe.getOutput().toItemStack());
    }

    @Override
    public void draw(R recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        // TODO render ticks and energy
    }
}
