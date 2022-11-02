package pl.panszelescik.basicmachines.api.client.screen.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;

public abstract class ProgressComponent extends GuiComponent implements Widget {

    private final ResourceLocation texture;
    private final int width;
    private final int height;
    private final ProgressDirection direction;
    private final int startX;
    private final int startY;
    private int leftPos;
    private int topPos;

    public ProgressComponent(ResourceLocation texture, int width, int height, ProgressDirection direction) {
        this(texture, width, height, direction, 0, 0);
    }

    // Texture must be 256x256
    public ProgressComponent(ResourceLocation texture, int width, int height, ProgressDirection direction, int startX, int startY) {
        super();
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.direction = direction;
        this.startX = startX;
        this.startY = startY;
    }

    public void setPos(int leftPos, int topPos) {
        this.leftPos = leftPos;
        this.topPos = topPos;
    }

    public int getXPos() {
        return this.leftPos;
    }

    public int getYPos() {
        return this.topPos;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public abstract float getProgress();

    public abstract float getMaxProgress();

    public int calculateProgress(int multiplier) {
        var progress = this.getProgress();
        if (progress <= 0) {
            return 0;
        }

        var max = this.getMaxProgress();
        if (max <= 0) {
            return 0;
        }

        return (int) (progress * multiplier / max);
    }

    public int getX() {
        return this.calculateProgress(this.width);
    }

    public int getY() {
        return this.calculateProgress(this.height);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, this.texture);

        switch (this.direction) {
            case LEFT_TO_RIGHT -> {
                var x = this.getX();
                this.blit(poseStack, this.leftPos + x, this.topPos, this.startX + x, this.startY, this.width - x, this.height);
                this.blit(poseStack, this.leftPos, this.topPos, this.startX + this.width, this.startY, x, this.height);
            }
            case BOTTOM_TO_TOP -> {
                var y = this.getY();
                this.blit(poseStack, this.leftPos, this.topPos, this.startX, this.startY, this.width, this.height - y);
                this.blit(poseStack, this.leftPos, this.topPos + this.height - y, this.startX + this.width, this.startY + this.height - y, this.width, y);
            }
            default -> throw new IllegalStateException("Not implemented value: " + this.direction);
        }
    }

    public enum ProgressDirection {

        LEFT_TO_RIGHT,
        RIGHT_TO_LEFT,
        TOP_TO_BOTTOM,
        BOTTOM_TO_TOP,
    }
}
