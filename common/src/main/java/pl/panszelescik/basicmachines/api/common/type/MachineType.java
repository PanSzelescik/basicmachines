package pl.panszelescik.basicmachines.api.common.type;

import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import pl.panszelescik.basicmachines.api.client.screen.MachineScreen;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.block.MachineBlock;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

import java.util.Objects;
import java.util.function.ToIntFunction;

public class MachineType<R extends Recipe<Container>> {

    public static final int[] INPUT_SLOTS = new int[]{0};
    public static final int[] OUTPUT_SLOTS = new int[]{1};

    public static final ObjectArrayList<MachineType<?>> MACHINE_TYPES = new ObjectArrayList<>();;

    private final String name;
    private final RecipeManager.CachedCheck<Container, R> recipeManager;
    private final int[] inputSlots;
    private final int[] outputSlots;
    private final ToIntFunction<R> processingTimeFunction;
    private RegistrySupplier<MachineBlock<R>> block;
    private RegistrySupplier<BlockItem> blockItem;
    private RegistrySupplier<BlockEntityType<MachineBlockEntity<R>>> blockEntityType;
    private RegistrySupplier<MenuType<MachineContainerMenu<R>>> menuType;

    public MachineType(String name, RecipeType<R> recipeType, int[] inputSlots, int[] outputSlots, ToIntFunction<R> processingTimeFunction) {
        this.name = name;
        this.recipeManager = RecipeManager.createCheck(recipeType);
        this.inputSlots = inputSlots;
        this.outputSlots = outputSlots;
        this.processingTimeFunction = Objects.requireNonNullElseGet(processingTimeFunction, () -> r -> 200);
    }

    public String getMachineName() {
        return this.name;
    }

    public RecipeManager.CachedCheck<Container, R> getRecipeManager() {
        return this.recipeManager;
    }

    public int[] getInputSlots() {
        return this.inputSlots;
    }

    public int[] getOutputSlots() {
        return this.outputSlots;
    }

    public int slotsAmount() {
        return this.getInputSlots().length + this.getOutputSlots().length;
    }

    public MachineBlock<R> getBlock() {
        return this.block.get();
    }

    public BlockItem getBlockItem() {
        return this.blockItem.get();
    }

    @SuppressWarnings("unchecked")
    public MachineBlockEntity<R> getBlockEntity(BlockPos blockPos, BlockState blockState) {
        return (MachineBlockEntity<R>) BasicMachinesPlatform.getMachineBlockEntityCreator().create((MachineType<Recipe<Container>>) this, blockPos, blockState);
    }

    public BlockEntityType<MachineBlockEntity<R>> getBlockEntityType() {
        return this.blockEntityType.get();
    }

    public MenuType<MachineContainerMenu<R>> getMenuType() {
        return this.menuType.get();
    }

    public int getProcessingTime(R recipe) {
        var time = this.processingTimeFunction.applyAsInt(recipe);
        return time > 0 ? time : 200;
    }

    public int getEnergyUsagePerTick() {
        return 20;
    }

    // Registering
    private void registerBlock() {
        this.block = BasicMachinesMod.BLOCKS.register(this.getMachineName(), () -> new MachineBlock<>(BlockBehaviour.Properties.of(Material.METAL), this));
    }

    private void registerBlockItem() {
        this.blockItem = BasicMachinesMod.registerBlockItem(this.block);
    }

    private void registerBlockEntityType() {
        this.blockEntityType = BasicMachinesMod.BLOCK_ENTITY_TYPES.register(this.getMachineName(), () -> BlockEntityType.Builder.of(this::getBlockEntity, this.getBlock()).build(null));
    }

    private void registerMenuType() {
        this.menuType = BasicMachinesMod.MENU_TYPES.register(this.getMachineName(), () -> new MenuType<>((syncId, inventory) -> new MachineContainerMenu<>(syncId, inventory, this)));
    }

    private void register() {
        this.registerBlock();
        this.registerBlockItem();
        this.registerBlockEntityType();
        this.registerMenuType();
    }

    private void registerClient() {
        MenuScreens.register(this.getMenuType(), MachineScreen::new);
    }

    public static void registerAll() {
        MACHINE_TYPES.forEach(MachineType::register);
    }

    public static void registerAllClient() {
        MACHINE_TYPES.forEach(MachineType::registerClient);
    }
}
