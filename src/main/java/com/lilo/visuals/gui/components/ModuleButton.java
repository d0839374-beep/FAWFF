package com.lilo.visuals.gui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

import java.awt.*;
import java.util.function.Consumer;

public class ModuleButton extends AbstractGui {
    private final String name;
    private int x, y, width, height;
    private boolean enabled;
    private boolean open;
    private Consumer<Boolean> toggleAction;
    
    private static final Color ENABLED_COLOR = new Color(0, 200, 255);
    private static final Color DISABLED_COLOR = new Color(100, 100, 100);
    private static final Color BACKGROUND_COLOR = new Color(30, 30, 30, 200);
    private static final Color HOVER_COLOR = new Color(50, 50, 50, 200);
    
    public ModuleButton(String name, int x, int y, int width, int height, boolean enabled) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.enabled = enabled;
        this.open = false;
    }
    
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        
        // Background
        Color bgColor = isHovered ? HOVER_COLOR : BACKGROUND_COLOR;
        GuiUtils.drawRect(matrixStack, x, y, width, height, bgColor);
        
        // Side indicator
        Color indicatorColor = enabled ? ENABLED_COLOR : DISABLED_COLOR;
        GuiUtils.drawRect(matrixStack, x, y, 3, height, indicatorColor);
        
        // Text
        Color textColor = enabled ? Color.WHITE : Color.GRAY;
        GuiUtils.drawString(matrixStack, name, x + 8, y + (height - mc.font.lineHeight) / 2, textColor, true);
        
        // Open indicator
        if (open) {
            GuiUtils.drawString(matrixStack, "▼", x + width - 15, y + (height - mc.font.lineHeight) / 2, 
                new Color(0, 200, 255), true);
        }
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (mouseButton == 0) { // Left click
                enabled = !enabled;
                if (toggleAction != null) {
                    toggleAction.accept(enabled);
                }
                return true;
            } else if (mouseButton == 1) { // Right click
                open = !open;
                return true;
            }
        }
        return false;
    }
    
    public String getName() {
        return name;
    }
    
    public boolean isEnabled() {
        return enabled;
    }
    
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean isOpen() {
        return open;
    }
    
    public void setOpen(boolean open) {
        this.open = open;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
    public void setToggleAction(Consumer<Boolean> action) {
        this.toggleAction = action;
    }
}
