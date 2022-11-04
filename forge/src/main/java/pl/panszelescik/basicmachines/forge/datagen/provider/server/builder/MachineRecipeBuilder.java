package pl.panszelescik.basicmachines.forge.datagen.provider.server.builder;

import com.google.gson.JsonObject;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
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
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;
import pl.panszelescik.basicmachines.recipe.serializer.CrusherRecipeSerializer;

import java.util.Arrays;
import java.util.function.Consumer;

public class MachineRecipeBuilder<R extends MachineRecipe> {

    private final Advancement.Builder advancement = Advancement.Builder.advancement();
    private final MachineRecipeSerializer<R> recipeSerializer;
    private Ingredient input;
    private ItemStack output;

    private MachineRecipeBuilder(MachineRecipeSerializer<R> recipeSerializer) {
        this.recipeSerializer = recipeSerializer;
    }

    public static MachineRecipeBuilder<CrusherRecipe> crusher() {
        return new MachineRecipeBuilder<>(CrusherRecipeSerializer.INSTANCE);
    }

    public MachineRecipeBuilder<R> input(ItemLike... args) {
        return this.unlockedBy("has_input", RecipeProvider.inventoryTrigger(ItemPredicate.Builder.item().of(args).build()))
                .input(Ingredient.of(args));
    }

    public MachineRecipeBuilder<R> input(ItemStack... args) {
        return this.input(Arrays.stream(args).map(ItemStack::getItem).toArray(ItemLike[]::new));
    }

    public MachineRecipeBuilder<R> input(TagKey<Item> tag) {
        return this.unlockedBy("has_input", RecipeProvider.has(tag))
                .input(Ingredient.of(tag));
    }

    private MachineRecipeBuilder<R> input(Ingredient input) {
        this.input = input;
        return this;
    }

    public MachineRecipeBuilder<R> output(ItemLike item) {
        return this.output(new ItemStack(item));
    }

    public MachineRecipeBuilder<R> output(ItemStack output) {
        this.output = output;
        return this;
    }

    private MachineRecipeBuilder<R> unlockedBy(String string, CriterionTriggerInstance arg) {
        this.advancement.addCriterion(string, arg);
        return this;
    }

    public void save(Consumer<FinishedRecipe> consumer, String name) {
        this.save(consumer, BasicMachinesMod.id(this.recipeSerializer.getId().getPath() + "/" + name));
    }

    private void save(Consumer<FinishedRecipe> consumer, ResourceLocation id) {
        this.advancement.parent(new ResourceLocation("recipes/root"))
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(id))
                .rewards(AdvancementRewards.Builder.recipe(id))
                .requirements(RequirementsStrategy.OR);

        consumer.accept(new Result<>(this, id));
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
                RecipeUtil.writeIngredient(jsonObject, this.builder.input);
            }

            if (this.builder.output != null) {
                RecipeUtil.writeItemStack(jsonObject, this.builder.output);
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
