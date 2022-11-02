package pl.panszelescik.basicmachines.integration.wthit;

import mcp.mobius.waila.api.IRegistrar;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.TooltipPosition;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.block.entity.MachineBlockEntity;
import pl.panszelescik.basicmachines.integration.wthit.provider.MachineProvider;

public class BasicMachinesWailaPlugin implements IWailaPlugin {

    @Override
    public void register(IRegistrar registrar) {
        registrar.addMergedSyncedConfig(BasicMachinesMod.id("wthit_provider"), true, false);
        registrar.addComponent(MachineProvider.INSTANCE, TooltipPosition.BODY, MachineBlockEntity.class);
        registrar.addBlockData(MachineProvider.INSTANCE, MachineBlockEntity.class);
    }
}
