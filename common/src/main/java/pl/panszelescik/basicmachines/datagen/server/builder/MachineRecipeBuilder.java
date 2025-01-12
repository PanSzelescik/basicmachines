package pl.panszelescik.basicmachines.datagen.server.builder;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.core.Registry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.recipe.MachineRecipe;
import pl.panszelescik.basicmachines.api.common.recipe.MachineRecipeSerializer;
import pl.panszelescik.basicmachines.api.common.util.RecipeUtil;
import pl.panszelescik.basicmachines.api.datagen.builder.IRecipeBuilder;
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.recipe.serializer.CrusherRecipeSerializer;

import java.util.Arrays;
import java.util.function.Consumer;

public class MachineRecipeBuilder<R extends MachineRecipe> implements IRecipeBuilder {

    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final MachineRecipeSerializer<R> recipeSerializer;
    private Ingredient input;
    private int inputSize = 1;
    private Ingredient output;
    private int outputSize = 1;
    private ResourceLocation id;

    private MachineRecipeBuilder(MachineRecipeSerializer<R> recipeSerializer) {
        this.recipeSerializer = recipeSerializer;
    }

    public static MachineRecipeBuilder<CrusherRecipe> crusher() {
        return new MachineRecipeBuilder<>(CrusherRecipeSerializer.INSTANCE);
    }

    public MachineRecipeBuilder<R> input(ItemLike... args) {
        return this.unlockedBy("has_input", RecipeProvider.inventoryTrigger(ItemPredicate.Builder
                        .item()
                        .of(args)
                        .build()))
                .input(Ingredient.of(args));
    }

    public MachineRecipeBuilder<R> input(ItemStack... args) {
        return this.input(Arrays.stream(args)
                .map(ItemStack::getItem)
                .toArray(ItemLike[]::new));
    }

    public MachineRecipeBuilder<R> input(TagKey<Item> tag) {
        return this.unlockedBy("has_input", RecipeProvider.has(tag))
                .input(Ingredient.of(tag));
    }

    public MachineRecipeBuilder<R> input(ResourceLocation tag) {
        return this.input(TagKey.create(Registry.ITEM_REGISTRY, tag));
    }

    private MachineRecipeBuilder<R> input(Ingredient input) {
        this.input = input;
        return this;
    }

    public MachineRecipeBuilder<R> output(ItemLike... args) {
        return this.output(Ingredient.of(args));
    }

    public MachineRecipeBuilder<R> output(ItemStack... args) {
        return this.output(Arrays.stream(args)
                .map(ItemStack::getItem)
                .toArray(ItemLike[]::new));
    }

    public MachineRecipeBuilder<R> output(TagKey<Item> tag) {
        return this.output(Ingredient.of(tag));
    }

    public MachineRecipeBuilder<R> output(ResourceLocation tag) {
        return this.output(TagKey.create(Registry.ITEM_REGISTRY, tag));
    }

    private MachineRecipeBuilder<R> output(Ingredient output) {
        this.output = output;
        return this;
    }

    public MachineRecipeBuilder<R> inputSize(int inputSize) {
        this.inputSize = inputSize;
        return this;
    }

    public MachineRecipeBuilder<R> outputSize(int outputSize) {
        this.outputSize = outputSize;
        return this;
    }

    private MachineRecipeBuilder<R> unlockedBy(String string, CriterionTriggerInstance arg) {
        this.advancement.addCriterion(string, arg);
        return this;
    }

    public MachineRecipeBuilder<R> id(ResourceLocation location) {
        this.id = location;
        return this;
    }

    public MachineRecipeBuilder<R> id(String name) {
        return this.id(BasicMachinesMod.id(this.recipeSerializer.getId().getPath() + "/" + name));
    }

    public void save(Consumer<FinishedRecipe> consumer, String name) {
        this.id(name).save(consumer);
    }

    private void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        this.id(id).save(consumer);
    }

    public void save(Consumer<FinishedRecipe> consumer) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);

        consumer.accept(new Result<>(this, this.id));
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    public static class Result<R extends MachineRecipe> implements FinishedRecipe {

        private final MachineRecipeBuilder<R> builder;
        private final ResourceLocation id;

        private Result(MachineRecipeBuilder<R> builder, ResourceLocation id) {
            this.builder = builder;
            this.id = id;
        }

        @Override
        public void serializeRecipeData(JsonObject jsonObject) {
            if (this.builder.input != null) {
                RecipeUtil.writeIngredientWithAmount(jsonObject, "input", this.builder.input, this.builder.inputSize);
            }

            if (this.builder.output != null) {
                RecipeUtil.writeIngredientWithAmount(jsonObject, "output", this.builder.output, this.builder.outputSize);
            }
        }

        @Override
        public ResourceLocation getId() {
            return this.id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return this.builder.recipeSerializer;
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.builder.advancement.serializeToJson();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return BasicMachinesMod.id("recipes/" + this.id.getPath());
        }
    }
}
