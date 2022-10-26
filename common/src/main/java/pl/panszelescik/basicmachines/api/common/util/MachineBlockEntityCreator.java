package pl.panszelescik.basicmachines.api.common.util;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.state.BlockState;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public interface MachineBlockEntityCreator<R extends Recipe<Container>> {
    MachineBlockEntity<R> create(MachineType<R> machineType, BlockPos blockPos, BlockState blockState);
}
