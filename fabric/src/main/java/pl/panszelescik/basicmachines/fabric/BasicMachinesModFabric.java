package pl.panszelescik.basicmachines.fabric;

import net.fabricmc.api.ModInitializer;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.type.MachineType;
import pl.panszelescik.basicmachines.fabric.api.block.entity.MachineBlockEntityFabric;
import team.reborn.energy.api.EnergyStorage;

public class BasicMachinesModFabric implements ModInitializer {

    public static void registerEnergyStorage() {
        MachineType.MACHINE_TYPES.forEach(machineType -> {
            EnergyStorage.SIDED.registerForBlockEntity((myBlockEntity, direction) -> ((MachineBlockEntityFabric) myBlockEntity).energyStorage, machineType.getBlockEntityType());
        });
    }

    @Override
    public void onInitialize() {
        BasicMachinesMod.init();
        registerEnergyStorage();
    }
}
