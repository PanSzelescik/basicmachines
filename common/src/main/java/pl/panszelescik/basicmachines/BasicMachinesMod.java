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
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public class BasicMachinesMod {

    public static final String MOD_ID = "basicmachines";

    public static final CreativeModeTab CREATIVE_TAB;

    public static final DeferredRegister<Block> BLOCKS;
    public static final DeferredRegister<Item> ITEMS;
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES;
    public static final DeferredRegister<MenuType<?>> MENU_TYPES;

    public static final MachineType<SmeltingRecipe> ELECTRIC_FURNACE = new MachineType<>("electric_furnace", RecipeType.SMELTING, MachineType.INPUT_SLOTS, MachineType.OUTPUT_SLOTS, AbstractCookingRecipe::getCookingTime);
    
    public static void init() {
        BLOCKS.register();
        ITEMS.register();
        BLOCK_ENTITY_TYPES.register();
        MENU_TYPES.register();
    }

    public static RegistrySupplier<BlockItem> registerBlockItem(RegistrySupplier<? extends Block> block) {
        return registerBlockItem(block, new Item.Properties());
    }

    public static RegistrySupplier<BlockItem> registerBlockItem(RegistrySupplier<? extends Block> block, Item.Properties properties) {
        return ITEMS.register(block.getId(), () -> new BlockItem(block.get(), properties.tab(BasicMachinesMod.CREATIVE_TAB)));
    }

    static {
        BLOCKS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_REGISTRY);
        ITEMS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.ITEM_REGISTRY);
        BLOCK_ENTITY_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
        MENU_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.MENU_REGISTRY);

        MachineType.MACHINE_TYPES.add(ELECTRIC_FURNACE);

        MachineType.registerAll();

        CREATIVE_TAB = CreativeTabRegistry.create(new ResourceLocation(MOD_ID, "creative_tab"), () ->
                new ItemStack(ELECTRIC_FURNACE.getBlockItem()));
    }
}
