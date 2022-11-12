package pl.panszelescik.basicmachines.datagen.server;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.datagen.server.builder.ExtendedShapedRecipeBuilder;
import pl.panszelescik.basicmachines.datagen.server.builder.MachineRecipeBuilder;
import pl.panszelescik.basicmachines.datagen.server.builder.RecipeTags;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class BasicMachinesCommonRecipeProvider extends RecipeProvider {

    public BasicMachinesCommonRecipeProvider(DataGenerator arg) {
        super(arg);
    }

    private void shaped(RegistrySupplier<? extends ItemLike> itemLike, Consumer<FinishedRecipe> consumer, UnaryOperator<ExtendedShapedRecipeBuilder> builderUnaryOperator) {
        builderUnaryOperator
                .apply(ExtendedShapedRecipeBuilder.create(itemLike))
                .save(consumer);
    }

    public void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        shaped(BasicMachinesBlocks.IRON_CASING, consumer, builder -> builder
                .pattern("III")
                .pattern("IBI")
                .pattern("III")
                .define('I', RecipeTags.INGOTS_IRON)
                .define('B', RecipeTags.STORAGE_BLOCKS_IRON));

        shaped(BasicMachinesTypes.ELECTRIC_FURNACE.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', RecipeTags.INGOTS_COPPER)
                .define('R', RecipeTags.DUSTS_REDSTONE)
                .define('M', Items.FURNACE)
                .define('G', RecipeTags.NUGGETS_GOLD));

        shaped(BasicMachinesTypes.ELECTRIC_BLAST_FURNACE.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', RecipeTags.INGOTS_COPPER)
                .define('R', RecipeTags.DUSTS_REDSTONE)
                .define('M', Items.BLAST_FURNACE)
                .define('G', RecipeTags.NUGGETS_GOLD));

        shaped(BasicMachinesTypes.ELECTRIC_SMOKER.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', RecipeTags.INGOTS_COPPER)
                .define('R', RecipeTags.DUSTS_REDSTONE)
                .define('M', Items.SMOKER)
                .define('G', RecipeTags.NUGGETS_GOLD));

        shaped(BasicMachinesTypes.ELECTRIC_CRUSHER.getBlockSupplier(), consumer, builder -> builder
                .pattern("GRG")
                .pattern("ROR")
                .pattern("CMC")
                .define('O', BasicMachinesBlocks.IRON_CASING.get())
                .define('C', RecipeTags.INGOTS_COPPER)
                .define('R', RecipeTags.DUSTS_REDSTONE)
                .define('M', Items.STONECUTTER)
                .define('G', RecipeTags.NUGGETS_GOLD));

        shaped(BasicMachinesItems.BLANK_UPGRADE, consumer, builder -> builder
                .pattern("GBG")
                .pattern("RDR")
                .pattern("GRG")
                .define('B', Items.BLAZE_POWDER)
                .define('R', RecipeTags.DUSTS_REDSTONE)
                .define('D', Items.IRON_DOOR)
                .define('G', RecipeTags.NUGGETS_GOLD));

        shaped(UpgradeType.SPEED.getItemSupplier(), consumer, builder -> builder
                .pattern("TPT")
                .pattern("LUL")
                .pattern("TLT")
                .define('U', BasicMachinesItems.BLANK_UPGRADE.get())
                .define('P', RecipeTags.ENDER_PEARLS)
                .define('L', RecipeTags.GEMS_LAPIS)
                .define('T', Items.REDSTONE_TORCH));

        shaped(UpgradeType.ENERGY.getItemSupplier(), consumer, builder -> builder
                .pattern("TPT")
                .pattern("GUG")
                .pattern("TGT")
                .define('U', BasicMachinesItems.BLANK_UPGRADE.get())
                .define('P', RecipeTags.ENDER_PEARLS)
                .define('G', RecipeTags.INGOTS_GOLD)
                .define('T', Items.REDSTONE_TORCH));

        MachineRecipeBuilder
                .crusher()
                .input(Blocks.COBBLESTONE)
                .output(Blocks.GRAVEL)
                .save(consumer, "gravel_from_cobblestone");
    }
}
