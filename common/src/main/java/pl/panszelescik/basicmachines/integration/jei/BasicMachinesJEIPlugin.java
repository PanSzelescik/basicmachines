package pl.panszelescik.basicmachines.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.RecipeTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;

@JeiPlugin
public class BasicMachinesJEIPlugin implements IModPlugin {

    private static final ResourceLocation ID = new ResourceLocation(BasicMachinesMod.MOD_ID, "jei");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(BasicMachinesMod.ELECTRIC_FURNACE.getItemStack(), RecipeTypes.SMELTING);
    }
}
