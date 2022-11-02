package pl.panszelescik.basicmachines.api.client.screen.component;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.network.chat.Component;
import pl.panszelescik.basicmachines.api.client.screen.MachineScreen;

import java.util.List;

public interface IHasTooltip {

    default Component getTooltip() {
        return null;
    }

    default List<Component> getTooltips() {
        var tooltip = this.getTooltip();
        return tooltip == null ? List.of() : List.of(tooltip);
    }

    boolean isHovered(int mouseX, int mouseY);

    default void renderTooltip(MachineScreen screen, PoseStack poseStack, int mouseX, int mouseY) {
        List<Component> tooltips = this.isHovered(mouseX, mouseY) ? this.getTooltips() : List.of();
        if (!tooltips.isEmpty()) {
            screen.renderComponentTooltip(poseStack, tooltips, mouseX, mouseY);
        }
    }
}
