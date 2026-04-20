package com.lilo.visuals.gui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Category {
    private final String name;
    private int x, y, width, height;
    private boolean dragged;
    private int dragOffsetX, dragOffsetY;
    private List<ModuleButton> modules;
    private boolean open;
    
    private static final Color HEADER_COLOR = new Color(0, 150, 200, 200);
    private static final Color BACKGROUND_COLOR = new Color(20, 20, 20, 220);
    
    public Category(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.width = 120;
        this.height = 20;
        this.open = false;
        this.modules = new ArrayList<>();
        
        // Add some default modules
        initializeModules();
    }
    
    private void initializeModules() {
        switch (name.toLowerCase()) {
            case "combat":
                addModule("KillAura", false);
                addModule("Velocity", false);
                addModule("AutoArmor", false);
                addModule("Criticals", false);
                break;
            case "movement":
                addModule("Fly", false);
                addModule("Speed", false);
                addModule("Sprint", true);
                addModule("NoFall", false);
                break;
            case "render":
                addModule("ESP", false);
                addModule("Fullbright", true);
                addModule("Nametags", false);
                addModule("Tracers", false);
                break;
            case "player":
                addModule("AutoEat", false);
                addModule("FastPlace", false);
                addModule("NoRotate", false);
                break;
            case "misc":
                addModule("AutoFish", false);
                addModule("Timer", false);
                addModule("Freecam", false);
                break;
        }
    }
    
    private void addModule(String moduleName, boolean enabled) {
        ModuleButton button = new ModuleButton(moduleName, x, y + height, width - 4, 16, enabled);
        modules.add(button);
    }
    
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft mc = Minecraft.getInstance();
        
        // Draw background
        GuiUtils.drawRect(matrixStack, x, y, width, getHeight(), BACKGROUND_COLOR);
        
        // Draw header
        GuiUtils.drawRect(matrixStack, x, y, width, height, HEADER_COLOR);
        GuiUtils.drawCenteredString(matrixStack, name, x + width / 2, y + (height - mc.font.lineHeight) / 2, 
            Color.WHITE, true);
        
        // Draw modules if category is open
        if (open) {
            int moduleY = y + height + 2;
            for (ModuleButton module : modules) {
                module.setPosition(x + 2, moduleY);
                module.render(matrixStack, mouseX, mouseY, partialTicks);
                moduleY += module.getHeight() + 2;
            }
        }
        
        // Update height based on open state
        updateHeight();
    }
    
    private void updateHeight() {
        if (open) {
            int totalHeight = height;
            for (ModuleButton module : modules) {
                totalHeight += module.getHeight() + 2;
            }
            this.height = totalHeight;
        } else {
            this.height = 20;
        }
    }
    
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        // Check header for dragging and opening
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + 20) {
            if (mouseButton == 0) {
                dragged = true;
                dragOffsetX = (int)(mouseX - x);
                dragOffsetY = (int)(mouseY - y);
                return true;
            } else if (mouseButton == 1) {
                open = !open;
                updateHeight();
                return true;
            }
        }
        
        // Check modules
        if (open) {
            for (ModuleButton module : modules) {
                if (module.mouseClicked(mouseX, mouseY, mouseButton)) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        dragged = false;
        return false;
    }
    
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        if (dragged && button == 0) {
            x = (int)(mouseX - dragOffsetX);
            y = (int)(mouseY - dragOffsetY);
            
            // Update module positions
            int moduleY = y + 20 + 2;
            for (ModuleButton module : modules) {
                module.setPosition(x + 2, moduleY);
                moduleY += module.getHeight() + 2;
            }
            
            return true;
        }
        return false;
    }
    
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        // Could implement scrolling here for long category lists
        return false;
    }
    
    public String getName() {
        return name;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        updateHeight();
        return open ? height : 20;
    }
    
    public boolean isOpen() {
        return open;
    }
    
    public List<ModuleButton> getModules() {
        return modules;
    }
}
