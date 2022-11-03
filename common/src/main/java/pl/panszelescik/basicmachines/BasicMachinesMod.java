package pl.panszelescik.basicmachines;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import pl.panszelescik.basicmachines.api.common.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.api.common.recipe.serializer.CrusherRecipeSerializer;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.api.common.type.SlotHolder;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;

public class BasicMachinesMod {

    public static final String MOD_ID = "basicmachines";

    public static final CreativeModeTab CREATIVE_TAB;

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.ITEM_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.MENU_REGISTRY);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.RECIPE_SERIALIZER_REGISTRY);
    public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);

    public static final MachineType<SmeltingRecipe> ELECTRIC_FURNACE = new MachineType<>("electric_furnace", RecipeType.SMELTING, SlotHolder.ONE_INPUT_ONE_OUTPUT, AbstractCookingRecipe::getCookingTime);
    public static final MachineType<BlastingRecipe> ELECTRIC_BLAST_FURNACE = new MachineType<>("electric_blast_furnace", RecipeType.BLASTING, SlotHolder.ONE_INPUT_ONE_OUTPUT, AbstractCookingRecipe::getCookingTime);
    public static final MachineType<SmokingRecipe> ELECTRIC_SMOKER = new MachineType<>("electric_smoker", RecipeType.SMOKING, SlotHolder.ONE_INPUT_ONE_OUTPUT, AbstractCookingRecipe::getCookingTime);
    public static final MachineType<CrusherRecipe> ELECTRIC_CRUSHER = new MachineType<>("electric_crusher", CrusherRecipe.Type.INSTANCE, SlotHolder.ONE_INPUT_ONE_OUTPUT);

    static {
        CrusherRecipe.Type.register();
        CrusherRecipeSerializer.register();

        MachineType.MACHINE_TYPES.add(ELECTRIC_FURNACE);
        MachineType.MACHINE_TYPES.add(ELECTRIC_BLAST_FURNACE);
        MachineType.MACHINE_TYPES.add(ELECTRIC_SMOKER);
        MachineType.MACHINE_TYPES.add(ELECTRIC_CRUSHER);

        UpgradeType.values();

        CREATIVE_TAB = CreativeTabRegistry.create(id("creative_tab"), () ->
                new ItemStack(ELECTRIC_FURNACE.getBlockItem()));
    }

    public static void init() {
        BLOCKS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();
        MENU_TYPES.register();
        RECIPE_SERIALIZERS.register();
        RECIPE_TYPES.register();
    }

    public static RegistrySupplier<BlockItem> registerBlockItem(RegistrySupplier<? extends Block> block) {
        return registerBlockItem(block, new Item.Properties());
    }

    public static RegistrySupplier<BlockItem> registerBlockItem(RegistrySupplier<? extends Block> block, Item.Properties properties) {
        return ITEMS.register(block.getId(), () -> new BlockItem(block.get(), properties.tab(BasicMachinesMod.CREATIVE_TAB)));
    }

    public static ResourceLocation id(String path) {
        return new ResourceLocation(BasicMachinesMod.MOD_ID, path);
    }
}
