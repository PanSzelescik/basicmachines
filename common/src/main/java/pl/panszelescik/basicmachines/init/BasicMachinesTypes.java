package pl.panszelescik.basicmachines.init;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.crafting.*;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.api.common.type.SlotHolder;
import pl.panszelescik.basicmachines.recipe.CrusherRecipe;

import java.util.List;

public class BasicMachinesTypes {

    public static final MachineType<SmeltingRecipe> ELECTRIC_FURNACE = new MachineType<>("electric_furnace", RecipeType.SMELTING, SlotHolder.ONE_INPUT_ONE_OUTPUT, SoundEvents.FURNACE_FIRE_CRACKLE, AbstractCookingRecipe::getCookingTime);
    public static final MachineType<BlastingRecipe> ELECTRIC_BLAST_FURNACE = new MachineType<>("electric_blast_furnace", RecipeType.BLASTING, SlotHolder.ONE_INPUT_ONE_OUTPUT, SoundEvents.BLASTFURNACE_FIRE_CRACKLE, AbstractCookingRecipe::getCookingTime);
    public static final MachineType<SmokingRecipe> ELECTRIC_SMOKER = new MachineType<>("electric_smoker", RecipeType.SMOKING, SlotHolder.ONE_INPUT_ONE_OUTPUT, SoundEvents.SMOKER_SMOKE, AbstractCookingRecipe::getCookingTime);
    public static final MachineType<CrusherRecipe> ELECTRIC_CRUSHER = new MachineType<>("electric_crusher", CrusherRecipe.TYPE, SlotHolder.ONE_INPUT_ONE_OUTPUT, BasicMachinesSounds.CRUSHER_WORK);
    public static final List<MachineType<?>> MACHINE_TYPES = new ObjectArrayList<>();

    private BasicMachinesTypes() {
    }

    public static void register() {
        MACHINE_TYPES.add(ELECTRIC_FURNACE);
        MACHINE_TYPES.add(ELECTRIC_BLAST_FURNACE);
        MACHINE_TYPES.add(ELECTRIC_SMOKER);
        MACHINE_TYPES.add(ELECTRIC_CRUSHER);
    }

    public static void registerClient() {
        MACHINE_TYPES.forEach(MachineType::registerClient);
    }
}
