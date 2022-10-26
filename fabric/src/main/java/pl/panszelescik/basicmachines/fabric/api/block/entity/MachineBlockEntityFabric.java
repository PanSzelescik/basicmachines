package pl.panszelescik.basicmachines.fabric.api.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public class MachineBlockEntityFabric<R extends Recipe<Container>> extends MachineBlockEntity<R> {

    public final MachineEnergyStorageFabric<R> energyStorage;

    public MachineBlockEntityFabric(MachineType<R> machineType, BlockPos blockPos, BlockState blockState) {
        super(machineType, blockPos, blockState);
        this.energyStorage = new MachineEnergyStorageFabric<>(this);
    }
}
