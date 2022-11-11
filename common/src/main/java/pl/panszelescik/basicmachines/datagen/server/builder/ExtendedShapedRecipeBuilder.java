package pl.panszelescik.basicmachines.datagen.server.builder;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import pl.panszelescik.basicmachines.BasicMachinesMod;

import java.util.function.Consumer;

public class ExtendedShapedRecipeBuilder {

    private final ShapedRecipeBuilder builder;
    private final ResourceLocation location;

    public ExtendedShapedRecipeBuilder(ItemLike arg, int i, ResourceLocation location) {
        this.builder = ShapedRecipeBuilder.shaped(arg, i).group(location.toString());
        this.location = location;
    }

    public static ExtendedShapedRecipeBuilder create(RegistrySupplier<? extends ItemLike> itemLike) {
        return create(itemLike.get(), itemLike.getId());
    }

    public static ExtendedShapedRecipeBuilder create(ItemLike arg, ResourceLocation location) {
        return create(arg, 1, location);
    }

    public static ExtendedShapedRecipeBuilder create(ItemLike arg, int i, ResourceLocation location) {
        return new ExtendedShapedRecipeBuilder(arg, i, location);
    }

    public ExtendedShapedRecipeBuilder pattern(String string) {
        this.builder.pattern(string);
        return this;
    }

    public ExtendedShapedRecipeBuilder define(char c, RecipeTags tag) {
        return this.define(c, tag.getTag());
    }

    private ExtendedShapedRecipeBuilder define(char c, TagKey<Item> arg) {
        this.builder
                .define(c, arg)
                .unlockedBy("has_item_" + c, RecipeProvider.has(arg));
        return this;
    }

    public ExtendedShapedRecipeBuilder define(char c, ItemLike arg) {
        this.builder
                .define(c, arg)
                .unlockedBy("has_item_" + c, RecipeProvider.has(arg));
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        this.builder.save(consumer, BasicMachinesMod.id("shaped/" + this.location.getPath()));
    }
}
