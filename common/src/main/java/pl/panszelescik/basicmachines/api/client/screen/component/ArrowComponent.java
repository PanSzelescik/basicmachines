package pl.panszelescik.basicmachines.api.client.screen.component;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import pl.panszelescik.basicmachines.BasicMachinesMod;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

public class ArrowComponent extends ProgressComponent implements IHasTooltip {

    private static final ResourceLocation TEXTURE = BasicMachinesMod.id("textures/gui/component/arrow.png");
    private final MachineContainerMenu<?> machineContainerMenu;

    public ArrowComponent(MachineContainerMenu<?> machineContainerMenu) {
        super(TEXTURE, 24, 17, ProgressDirection.LEFT_TO_RIGHT);
        this.machineContainerMenu = machineContainerMenu;
    }

    @Override
    public float getProgress() {
        return this.machineContainerMenu.getProgress();
    }

    @Override
    public float getMaxProgress() {
        return this.machineContainerMenu.getProcessingTime();
    }

    public boolean isProcessing() {
        return this.machineContainerMenu.isProcessing();
    }

    @Override
    public Component getTooltip() {
        var progress = this.getProgress();
        return this.isProcessing() || progress > 0 ? Component.translatable("tooltip.basicmachines.progress", (int) progress, (int) this.getMaxProgress()) : null;
    }
}
