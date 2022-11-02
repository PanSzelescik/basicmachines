package pl.panszelescik.basicmachines.integration.wthit.provider;

import mcp.mobius.waila.api.*;
import mcp.mobius.waila.api.component.ItemComponent;
import mcp.mobius.waila.api.component.ProgressArrowComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;

public class MachineProvider<R extends Recipe<Container>> implements IBlockComponentProvider, IServerDataProvider<MachineBlockEntity<R>> {

    public static final MachineProvider<?> INSTANCE = new MachineProvider<>();

    @Override
    public void appendBody(ITooltip tooltip, IBlockAccessor accessor, IPluginConfig config) {
        var tag = accessor.getServerData();

        if (tag.contains(MachineBlockEntity.PROGRESS)) {
            var items = tag.getList(MachineBlockEntity.ITEMS, Tag.TAG_COMPOUND);

            float progress = tag.getInt(MachineBlockEntity.PROGRESS);
            float total = tag.getInt(MachineBlockEntity.PROCESSING_TIME);

            tooltip.addLine()
                    .with(new ItemComponent(ItemStack.of(items.getCompound(0))))
                    .with(new ProgressArrowComponent(progress / total))
                    .with(new ItemComponent(ItemStack.of(items.getCompound(1))));
        }

        if (tag.contains(MachineBlockEntity.ENERGY)) {
            tooltip.addLine(Component.translatable("tooltip.basicmachines.energy", tag.getInt(MachineBlockEntity.ENERGY), tag.getInt(MachineBlockEntity.MAX_ENERGY), BasicMachinesPlatform.getEnergyType()));
        }
    }

    @Override
    public void appendServerData(CompoundTag data, IServerAccessor<MachineBlockEntity<R>> accessor, IPluginConfig config) {
        var machineBlockEntity = accessor.getTarget();

        data.putInt(MachineBlockEntity.ENERGY, machineBlockEntity.getCurrentEnergy());
        data.putInt(MachineBlockEntity.MAX_ENERGY, machineBlockEntity.getMaxEnergy());

        var processingTime = machineBlockEntity.getProcessingTime();
        if (processingTime < 0) {
            return;
        }

        var items = new ListTag();
        items.add(machineBlockEntity.getItem(0).save(new CompoundTag()));
        items.add(machineBlockEntity.getItem(1).save(new CompoundTag()));
        data.put(MachineBlockEntity.ITEMS, items);

        data.putInt(MachineBlockEntity.PROGRESS, machineBlockEntity.getProgress());
        data.putInt(MachineBlockEntity.PROCESSING_TIME, processingTime);
    }
}
