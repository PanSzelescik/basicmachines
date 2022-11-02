package pl.panszelescik.basicmachines.fabric;

import net.fabricmc.fabric.api.transfer.v1.context.ContainerItemContext;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.fabricmc.loader.api.FabricLoader;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.util.MachineBlockEntityCreator;
import pl.panszelescik.basicmachines.fabric.api.block.entity.MachineBlockEntityFabric;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.EnergyStorageUtil;

import java.nio.file.Path;

public class BasicMachinesPlatformImpl {

    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static <R extends Recipe<Container>> MachineBlockEntityCreator<R> getMachineBlockEntityCreator() {
        return MachineBlockEntityFabric::new;
    }

    public static String getEnergyType() {
        return "E";
    }

    public static boolean isBatteryItem(ItemStack itemStack) {
        return EnergyStorageUtil.isEnergyStorage(itemStack);
    }

    public static <T extends MachineBlockEntity<?>> void takeEnergyFromItem(T machineBlockEntity, ItemStack stack) {
        var energyItem = getEnergyStorage(stack);
        if (energyItem == null) {
            return;
        }

        EnergyStorageUtil.move(energyItem, ((MachineBlockEntityFabric<?>) machineBlockEntity).energyStorage, machineBlockEntity.getInputMaxEnergy(), null);
    }

    private static EnergyStorage getEnergyStorage(ItemStack itemStack) {
        return ContainerItemContext.withInitial(itemStack).find(EnergyStorage.ITEM);
    }
}
