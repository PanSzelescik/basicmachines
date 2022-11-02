package pl.panszelescik.basicmachines.api.client.screen.component;

import net.minecraft.network.chat.Component;

public interface Hoverable {

    boolean isHovered(int mouseX, int mouseY);

    void setHovered(boolean hovered);

    Component getTooltip();
}
