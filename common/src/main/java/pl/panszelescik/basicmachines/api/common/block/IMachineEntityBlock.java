package pl.panszelescik.basicmachines.api.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.type.IHasMachineType;

public interface IMachineEntityBlock<R extends Recipe<Container>> extends EntityBlock, IHasMachineType<R> {

    default MachineBlockEntity<R> getMachineBlockEntity(BlockPos blockPos, BlockState blockState) {
        return this.getMachineType().getBlockEntity(blockPos, blockState);
    }

    default BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return this.getMachineBlockEntity(blockPos, blockState);
    }
}
