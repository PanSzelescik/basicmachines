package pl.panszelescik.basicmachines.forge.datagen.provider.server;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.Tags;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.forge.datagen.provider.server.builder.ExtendedShapedRecipeBuilder;
import pl.panszelescik.basicmachines.forge.datagen.provider.server.builder.MachineRecipeBuilder;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class BasicMachinesRecipeProvider extends RecipeProvider {

    public BasicMachinesRecipeProvider(DataGenerator arg) {
        super(arg);
    }

    private void shaped(RegistrySupplier<? extends ItemLike> itemLike, Consumer<FinishedRecipe> consumer, UnaryOperator<ExtendedShapedRecipeBuilder> builderUnaryOperator) {
        builderUnaryOperator
                .apply(ExtendedShapedRecipeBuilder.create(itemLike))
                .save(consumer);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        shaped(BasicMachinesBlocks.IRON_CASING, consumer, builder -> builder
                .pattern("III")
                .pattern("IBI")
                .pattern("III")
                .define('I', Tags.Items.INGOTS_IRON)
                .define('B', Tags.Items.STORAGE_BLOCKS_IRON));

        shaped(BasicMachinesTypes.ELECTRIC_FURNACE.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('M', Items.FURNACE)
                .define('G', Tags.Items.NUGGETS_GOLD));

        shaped(BasicMachinesTypes.ELECTRIC_BLAST_FURNACE.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('M', Items.BLAST_FURNACE)
                .define('G', Tags.Items.NUGGETS_GOLD));

        shaped(BasicMachinesTypes.ELECTRIC_SMOKER.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('M', Items.SMOKER)
                .define('G', Tags.Items.NUGGETS_GOLD));

        shaped(BasicMachinesTypes.ELECTRIC_CRUSHER.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', Tags.Items.INGOTS_COPPER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('M', Items.STONECUTTER)
                .define('G', Tags.Items.NUGGETS_GOLD));

        shaped(BasicMachinesItems.BLANK_UPGRADE, consumer, builder -> builder
                .pattern("GBG")
                .pattern("RDR")
                .pattern("GRG")
                .define('B', Items.BLAZE_POWDER)
                .define('R', Tags.Items.DUSTS_REDSTONE)
                .define('D', Items.IRON_DOOR)
                .define('G', Tags.Items.NUGGETS_GOLD));

        shaped(UpgradeType.SPEED.getItemSupplier(), consumer, builder -> builder
                .pattern("TPT")
                .pattern("LUL")
                .pattern("TLT")
                .define('U', BasicMachinesItems.BLANK_UPGRADE.get())
                .define('P', Tags.Items.ENDER_PEARLS)
                .define('L', Tags.Items.GEMS_LAPIS)
                .define('T', Items.REDSTONE_TORCH));

        shaped(UpgradeType.ENERGY.getItemSupplier(), consumer, builder -> builder
                .pattern("TPT")
                .pattern("GUG")
                .pattern("TGT")
                .define('U', BasicMachinesItems.BLANK_UPGRADE.get())
                .define('P', Tags.Items.ENDER_PEARLS)
                .define('G', Tags.Items.INGOTS_GOLD)
                .define('T', Items.REDSTONE_TORCH));

        MachineRecipeBuilder
                .crusher()
                .input(Blocks.COBBLESTONE)
                .output(Blocks.GRAVEL)
                .save(consumer, "gravel_from_cobblestone");
    }
}
