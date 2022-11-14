package pl.panszelescik.basicmachines.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;
import pl.panszelescik.basicmachines.integration.jei.category.CrusherRecipeCategory;
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;

@JeiPlugin
public class BasicMachinesJEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = BasicMachinesMod.id("jei");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(BasicMachinesTypes.ELECTRIC_FURNACE.getItemStack(), RecipeTypes.SMELTING);
        registration.addRecipeCatalyst(BasicMachinesTypes.ELECTRIC_BLAST_FURNACE.getItemStack(), RecipeTypes.BLASTING);
        registration.addRecipeCatalyst(BasicMachinesTypes.ELECTRIC_SMOKER.getItemStack(), RecipeTypes.SMOKING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new CrusherRecipeCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        var recipeManager = getRecipeManager();
        if (recipeManager == null) {
            return;
        }

        registration.addRecipes(CrusherRecipeCategory.TYPE, recipeManager.getAllRecipesFor(CrusherRecipe.TYPE));
    }

    private RecipeManager getRecipeManager() {
        var world = Minecraft.getInstance().level;
        return world != null ? world.getRecipeManager() : null;
    }
}
