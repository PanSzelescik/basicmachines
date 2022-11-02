package pl.panszelescik.basicmachines.api.client.screen.component;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

public class EnergyComponent extends ProgressComponent implements Hoverable {

    private static final ResourceLocation TEXTURE = BasicMachinesMod.id("textures/gui/component/energy.png");
    private final MachineContainerMenu<?> machineContainerMenu;

    public EnergyComponent(MachineContainerMenu<?> machineContainerMenu) {
        super(TEXTURE, 10, 42, ProgressDirection.BOTTOM_TO_TOP);
        this.machineContainerMenu = machineContainerMenu;
    }

    @Override
    public float getProgress() {
        return this.machineContainerMenu.getCurrentEnergy();
    }

    @Override
    public float getMaxProgress() {
        return this.machineContainerMenu.getMaxEnergy();
    }

    @Override
    public Component getTooltip() {
        return Component.translatable("tooltip.basicmachines.energy", (int) this.getProgress(), (int) this.getMaxProgress(), BasicMachinesPlatform.getEnergyType());
    }
}
