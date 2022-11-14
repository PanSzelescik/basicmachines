package pl.panszelescik.basicmachines.datagen.server;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import pl.panszelescik.basicmachines.api.common.type.UpgradeType;
import pl.panszelescik.basicmachines.api.datagen.IRecipeProvider;
import pl.panszelescik.basicmachines.datagen.server.builder.ExtendedShapedRecipeBuilder;
import pl.panszelescik.basicmachines.datagen.server.builder.MachineRecipeBuilder;
import pl.panszelescik.basicmachines.datagen.server.builder.OreMaterial;
import pl.panszelescik.basicmachines.datagen.server.builder.RecipeTags;
import pl.panszelescik.basicmachines.init.BasicMachinesBlocks;
import pl.panszelescik.basicmachines.init.BasicMachinesItems;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class BasicMachinesCommonRecipeProvider extends RecipeProvider {

    private final IRecipeProvider provider;

    public BasicMachinesCommonRecipeProvider(DataGenerator arg, IRecipeProvider provider) {
        super(arg);
        this.provider = provider;
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

        basicCrusher(consumer, Blocks.COBBLESTONE, Blocks.GRAVEL);
        basicCrusher(consumer, Blocks.GRAVEL, Blocks.SAND);

        for (var material : OreMaterial.MATERIALS) {
            this.provider.tagLocationsExists(consumer, MachineRecipeBuilder
                    .crusher()
                    .input(material.getOreTag())
                    .output(material.getRawOreTag())
                    .outputSize(2)
                    .id("1x_" + material.name() + "_ore_to_2x_raw_" + material.name() + "_ore"), material.getOreTag(), material.getRawOreTag());

            this.provider.tagLocationsExists(consumer, MachineRecipeBuilder
                    .crusher()
                    .input(material.getRawOreTag())
                    .inputSize(3)
                    .output(material.getDustTag())
                    .outputSize(4)
                    .id("3x_raw_" + material.name() + "_ore_to_4x_" + material.name() + "_dust"), material.getRawOreTag(), material.getDustTag());

            this.provider.tagLocationsExists(consumer, MachineRecipeBuilder
                    .crusher()
                    .input(material.getIngotTag())
                    .output(material.getDustTag())
                    .id("1x_" + material.name() + "_ingot_to_1x_" + material.name() + "_dust"), material.getIngotTag(), material.getDustTag());
        }
    }

    private void basicCrusher(Consumer<FinishedRecipe> consumer, ItemLike input, ItemLike output) {
        MachineRecipeBuilder
                .crusher()
                .input(input)
                .output(output)
                .id("1x_" + Registry.ITEM.getKey(input.asItem()).getPath() + "_to_1x_" + Registry.ITEM.getKey(output.asItem()).getPath())
                .save(consumer);
    }
}
