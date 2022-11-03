package pl.panszelescik.basicmachines.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

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
}
