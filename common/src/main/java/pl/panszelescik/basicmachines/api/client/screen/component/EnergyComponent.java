package pl.panszelescik.basicmachines.api.client.screen.component;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

import java.util.List;

public class EnergyComponent extends ProgressComponent implements IHasTooltip {

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

    public boolean isProcessing() {
        return this.machineContainerMenu.isProcessing();
    }

    public int getEnergyUsage() {
        return this.machineContainerMenu.getEnergyUsagePerTick();
    }

    @Override
    public List<Component> getTooltips() {
        var component = Component.translatable("tooltip.basicmachines.energy", (int) this.getProgress(), (int) this.getMaxProgress(), BasicMachinesPlatform.getEnergyType());
        return this.isProcessing()
                ? List.of(component, Component.translatable("tooltip.basicmachines.energy_usage", this.getEnergyUsage(), BasicMachinesPlatform.getEnergyType()))
                : List.of(component);
    }
}
