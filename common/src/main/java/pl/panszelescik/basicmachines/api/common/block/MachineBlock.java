package pl.panszelescik.basicmachines.api.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.api.common.type.MachineType;

public class MachineBlock<R extends Recipe<Container>> extends DirectionalBlock implements IMachineEntityBlock<R> {

    private final MachineType<R> machineType;

    public MachineBlock(Properties properties, MachineType<R> machineType) {
        super(properties);
        this.machineType = machineType;
    }

    public MachineType<R> getMachineType() {
        return this.machineType;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide) {
            var menuProvider = state.getMenuProvider(level, pos);

            if (menuProvider != null) {
                player.openMenu(menuProvider);
            }
        }

        return InteractionResult.SUCCESS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean bl) {
        if (!state.is(newState.getBlock())) {
            var blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof MachineBlockEntity<?> machineBlockEntity) {
                if (!level.isClientSide) {
                    Containers.dropContents(level, pos, machineBlockEntity);
                }

                level.updateNeighbourForOutputSignal(pos, this);
            }

            super.onRemove(state, level, pos, newState, bl);
        }
    }

    @Override
    public boolean hasAnalogOutputSignal(BlockState blockState) {
        return true;
    }

    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos blockPos) {
        return AbstractContainerMenu.getRedstoneSignalFromBlockEntity(level.getBlockEntity(blockPos));
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? null : createTickerHelper(blockEntityType, this.machineType.getBlockEntityType(), MachineBlockEntity::serverTick);
    }
}
