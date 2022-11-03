package pl.panszelescik.basicmachines.fabric;

import net.fabricmc.api.ModInitializer;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.fabric.api.block.entity.MachineBlockEntityFabric;
import pl.panszelescik.basicmachines.init.BasicMachinesTypes;
import team.reborn.energy.api.EnergyStorage;

public class BasicMachinesModFabric implements ModInitializer {

    public static void registerEnergyStorage() {
        BasicMachinesTypes.MACHINE_TYPES.forEach(machineType -> {
            EnergyStorage.SIDED.registerForBlockEntity((myBlockEntity, direction) -> ((MachineBlockEntityFabric) myBlockEntity).energyStorage, machineType.getBlockEntityType());
        });
    }

    @Override
    public void onInitialize() {
        BasicMachinesMod.init();
        registerEnergyStorage();
    }
}
