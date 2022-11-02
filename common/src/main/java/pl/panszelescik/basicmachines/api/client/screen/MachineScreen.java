package pl.panszelescik.basicmachines.api.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import pl.panszelescik.basicmachines.api.client.screen.component.ArrowComponent;
import pl.panszelescik.basicmachines.api.client.screen.component.EnergyComponent;
import pl.panszelescik.basicmachines.api.common.block.inventory.menu.MachineContainerMenu;

public class MachineScreen extends AbstractContainerScreen<MachineContainerMenu<?>> {

    private final ArrowComponent arrowComponent;
    private final EnergyComponent energyComponent;

    public MachineScreen(MachineContainerMenu machineContainerMenu, Inventory inventory, Component component) {
        super(machineContainerMenu, inventory, component);
        this.arrowComponent = new ArrowComponent(machineContainerMenu);
        this.energyComponent = new EnergyComponent(machineContainerMenu);
    }

    @Override
    protected void renderBg(PoseStack poseStack, float f, int i, int j) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.menu.machineType.getSlotHolder().getTexture());
        this.blit(poseStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        this.renderBackground(poseStack);
        super.render(poseStack, i, j, f);

        this.arrowComponent.render(poseStack, i, j, f);
        this.energyComponent.render(poseStack, i, j, f);

        this.renderTooltip(poseStack, i, j);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.arrowComponent.setPos(this.leftPos + 79, this.topPos + 34);
        this.energyComponent.setPos(this.leftPos + 8, this.topPos + 20);
    }

    @Override
    protected void renderTooltip(PoseStack poseStack, int i, int j) {
        super.renderTooltip(poseStack, i, j);
        if (this.energyComponent.isHovered(i, j)) {
            this.renderTooltip(poseStack, this.energyComponent.getTooltip(), i, j);
        }
    }
}
