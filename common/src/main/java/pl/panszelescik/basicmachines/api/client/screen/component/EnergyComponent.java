package pl.panszelescik.basicmachines.api.client.screen.component;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.BasicMachinesPlatform;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

public class EnergyComponent extends ProgressComponent implements Hoverable {

    public static final ResourceLocation TEXTURE = BasicMachinesMod.id("textures/gui/component/energy.png");
    private final MachineContainerMenu<?> machineContainerMenu;
    private boolean hovered = false;

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
    public boolean isHovered(int mouseX, int mouseY) {
        return this.hovered;
    }

    @Override
    public void setHovered(boolean hovered) {
        this.hovered = hovered;
    }

    @Override
    public Component getTooltip() {
        return Component.translatable("tooltip.basicmachines.energy", this.getProgress(), this.getMaxProgress(), BasicMachinesPlatform.getEnergyType());
    }
}
