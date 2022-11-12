package pl.panszelescik.basicmachines.init;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import pl.panszelescik.basicmachines.BasicMachinesMod;

public class BasicMachinesBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<Block> IRON_CASING = registerBlock("iron_casing");

    private BasicMachinesBlocks() {
    }

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITY_TYPES.register();
        MENU_TYPES.register();
    }

    public static RegistrySupplier<Block> registerBlock(String name) {
        return registerBlock(new ResourceLocation(BasicMachinesMod.MOD_ID, name));
    }

    public static RegistrySupplier<Block> registerBlock(ResourceLocation location) {
        var block = BLOCKS.register(location, () -> new Block(BlockBehaviour.Properties.of(Material.METAL)));
        BasicMachinesItems.registerBlockItem(block);
        return block;
    }
}
