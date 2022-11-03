package pl.panszelescik.basicmachines.init;

import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import pl.panszelescik.basicmachines.BasicMachinesMod;

public class BasicMachinesBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_REGISTRY);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(BasicMachinesMod.MOD_ID, Registry.MENU_REGISTRY);

    private BasicMachinesBlocks() {
    }

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITY_TYPES.register();
        MENU_TYPES.register();
    }
}
