package pl.panszelescik.basicmachines.init;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;

public class BasicMachinesItems {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.ITEM_REGISTRY);
    public static final CreativeModeTab CREATIVE_TAB = CreativeTabRegistry.create(BasicMachinesMod.id("items"), () -> new ItemStack(BasicMachinesTypes.ELECTRIC_FURNACE.getBlockItem())).setRecipeFolderName("");

    public static final RegistrySupplier<Item> BLANK_UPGRADE = registerItem("blank_upgrade");

    private BasicMachinesItems() {
    }

    private static RegistrySupplier<Item> registerItem(String name) {
        var location = BasicMachinesMod.id(name);
        return ITEMS.register(location, () -> new Item(new Item.Properties().tab(CREATIVE_TAB)));
    }

    public static RegistrySupplier<BlockItem> registerBlockItem(RegistrySupplier<? extends Block> block) {
        return registerBlockItem(block, new Item.Properties());
    }

    public static RegistrySupplier<BlockItem> registerBlockItem(RegistrySupplier<? extends Block> block, Item.Properties properties) {
        return ITEMS.register(block.getId(), () -> new BlockItem(block.get(), properties.tab(CREATIVE_TAB)));
    }

    public static void register() {
        UpgradeType.values(); // Force load Enum

        ITEMS.register();
    }
}
