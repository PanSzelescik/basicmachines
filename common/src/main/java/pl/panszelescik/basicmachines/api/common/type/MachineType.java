package pl.panszelescik.basicmachines.api.common.type;

import dev.architectury.registry.registries.RegistrySupplier;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.api.client.screen.MachineScreen;
import pl.panszelescik.basicmachines.api.common.block.MachineBlock;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

import java.util.Objects;
import java.util.function.ToIntFunction;

public class MachineType<R extends Recipe<Container>> {

    public static final ObjectArrayList<MachineType<?>> MACHINE_TYPES = new ObjectArrayList<>();

    private final ResourceLocation resourceLocation;
    private final RecipeManager.CachedCheck<Container, R> recipeManager;
    private final SlotHolder slotHolder;
    private final ToIntFunction<R> processingTimeFunction;
    private RegistrySupplier<MachineBlock<R>> block;
    private RegistrySupplier<BlockItem> blockItem;
    private RegistrySupplier<BlockEntityType<MachineBlockEntity<R>>> blockEntityType;
    private RegistrySupplier<MenuType<MachineContainerMenu<R>>> menuType;

    public MachineType(String name, RecipeType<R> recipeType, SlotHolder slotHolder) {
        this(name, recipeType, slotHolder, null);
    }

    public MachineType(String name, RecipeType<R> recipeType, SlotHolder slotHolder, ToIntFunction<R> processingTimeFunction) {
        this.resourceLocation = new ResourceLocation(BasicMachinesMod.MOD_ID, name);
        this.recipeManager = RecipeManager.createCheck(recipeType);
        this.slotHolder = slotHolder;
        this.processingTimeFunction = Objects.requireNonNullElseGet(processingTimeFunction, () -> r -> 200);

        this.registerBlock();
        this.registerBlockItem();
        this.registerBlockEntityType();
        this.registerMenuType();
    }

    public static void registerAllClient() {
        MACHINE_TYPES.forEach(MachineType::registerClient);
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public String getName() {
        return this.resourceLocation.getPath();
    }

    public RecipeManager.CachedCheck<Container, R> getRecipeManager() {
        return this.recipeManager;
    }

    public SlotHolder getSlotHolder() {
        return this.slotHolder;
    }

    public MachineBlock<R> getBlock() {
        return this.block.get();
    }

    public BlockItem getBlockItem() {
        return this.blockItem.get();
    }

    public ItemStack getItemStack() {
        return this.getBlockItem().getDefaultInstance();
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
        this.block = BasicMachinesMod.BLOCKS.register(this.getResourceLocation(), () -> new MachineBlock<>(BlockBehaviour.Properties.of(Material.METAL), this));
    }

    private void registerBlockItem() {
        this.blockItem = BasicMachinesMod.registerBlockItem(this.block);
    }

    private void registerBlockEntityType() {
        this.blockEntityType = BasicMachinesMod.BLOCK_ENTITY_TYPES.register(this.getResourceLocation(), () -> BlockEntityType.Builder.of(this::getBlockEntity, this.getBlock()).build(null));
    }

    private void registerMenuType() {
        this.menuType = BasicMachinesMod.MENU_TYPES.register(this.getResourceLocation(), () -> new MenuType<>((syncId, inventory) -> new MachineContainerMenu<>(syncId, inventory, this)));
    }

    private void registerClient() {
        MenuScreens.register(this.getMenuType(), MachineScreen::new);
    }
}
