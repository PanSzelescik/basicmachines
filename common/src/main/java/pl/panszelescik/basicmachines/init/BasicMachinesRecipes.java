package pl.panszelescik.basicmachines.init;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.recipe.serializer.CrusherRecipeSerializer;

public class BasicMachinesRecipes {

    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);

    private BasicMachinesRecipes() {
    }

    private static <R extends Recipe<?>> void registerRecipe(String name, RecipeSerializer<R> serializer, RecipeType<R> recipeType) {
        var location = BasicMachinesMod.id(name);
        RECIPE_SERIALIZERS.register(location, () -> serializer);
        RECIPE_TYPES.register(location, () -> recipeType);
    }

    public static void register() {
        registerRecipe("crusher", CrusherRecipeSerializer.INSTANCE, CrusherRecipe.TYPE);

        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();
    }
}
