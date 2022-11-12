package pl.panszelescik.basicmachines.api.common.type;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
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
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;

import java.util.Objects;
import java.util.function.ToIntFunction;

public class MachineType<R extends Recipe<Container>> {

    private final ResourceLocation resourceLocation;
    private final RecipeManager.CachedCheck<Container, R> recipeManager;
    private final SlotHolder slotHolder;
    private final ToIntFunction<R> processingTimeFunction;
    private final RegistrySupplier<MachineBlock<R>> block;
    private final RegistrySupplier<BlockItem> blockItem;
    private final RegistrySupplier<BlockEntityType<MachineBlockEntity<R>>> blockEntityType;
    private final RegistrySupplier<MenuType<MachineContainerMenu<R>>> menuType;
    private final SoundEvent soundEvent;

    public MachineType(String name, RecipeType<R> recipeType, SlotHolder slotHolder, SoundEvent soundEvent) {
        this(name, recipeType, slotHolder, soundEvent, null);
    }

    public MachineType(String name, RecipeType<R> recipeType, SlotHolder slotHolder, SoundEvent soundEvent, ToIntFunction<R> processingTimeFunction) {
        this.resourceLocation = new ResourceLocation(BasicMachinesMod.MOD_ID, name);
        this.recipeManager = RecipeManager.createCheck(recipeType);
        this.slotHolder = slotHolder;
        this.soundEvent = soundEvent;
        this.processingTimeFunction = Objects.requireNonNullElseGet(processingTimeFunction, () -> r -> 200);

        this.block = this.registerBlock();
        this.blockItem = this.registerBlockItem();
        this.blockEntityType = this.registerBlockEntityType();
        this.menuType = this.registerMenuType();
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public ResourceLocation getResourceLocationOn() {
        return new ResourceLocation(BasicMachinesMod.MOD_ID, this.resourceLocation.getPath() + "_on");
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

    public SoundEvent getSoundEvent() {
        return this.soundEvent;
    }

    public RegistrySupplier<MachineBlock<R>> getBlockSupplier() {
        return this.block;
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
    private RegistrySupplier<MachineBlock<R>> registerBlock() {
        return BasicMachinesBlocks.BLOCKS.register(this.getResourceLocation(), () -> new MachineBlock<>(BlockBehaviour.Properties.of(Material.METAL), this));
    }

    private RegistrySupplier<BlockItem> registerBlockItem() {
        return BasicMachinesItems.registerBlockItem(this.block);
    }

    private RegistrySupplier<BlockEntityType<MachineBlockEntity<R>>> registerBlockEntityType() {
        return BasicMachinesBlocks.BLOCK_ENTITY_TYPES.register(this.getResourceLocation(), () -> BlockEntityType.Builder.of(this::getBlockEntity, this.getBlock()).build(null));
    }

    private RegistrySupplier<MenuType<MachineContainerMenu<R>>> registerMenuType() {
        return BasicMachinesBlocks.MENU_TYPES.register(this.getResourceLocation(), () -> new MenuType<>((syncId, inventory) -> new MachineContainerMenu<>(syncId, inventory, this)));
    }

    public void registerClient() {
        MenuScreens.register(this.getMenuType(), MachineScreen::new);
    }
}
