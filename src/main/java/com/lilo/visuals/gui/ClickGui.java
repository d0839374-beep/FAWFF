package com.lilo.visuals.gui;

import com.lilo.visuals.gui.components.Category;
import com.lilo.visuals.gui.components.GuiUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.StringTextComponent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
    private List<Category> categories;
    private static final Color BACKGROUND_COLOR = new Color(15, 15, 15, 230);
    private static final Color ACCENT_COLOR = new Color(0, 200, 255);
    
    public ClickGui() {
        super(StringTextComponent.literal("Lilo's Visuals - ClickGUI"));
        this.categories = new ArrayList<>();
        initializeCategories();
    }
    
    private void initializeCategories() {
        int startX = 20;
        int startY = 50;
        int spacing = 140;
        
        categories.add(new Category("Combat", startX, startY));
        categories.add(new Category("Movement", startX + spacing, startY));
        categories.add(new Category("Render", startX + spacing * 2, startY));
        categories.add(new Category("Player", startX + spacing * 3, startY));
        categories.add(new Category("Misc", startX + spacing * 4, startY));
    }
    
    @Override
    protected void init() {
        super.init();
        // Ensure categories are within screen bounds
        for (Category category : categories) {
            if (category.getX() + category.getWidth() > width) {
                category.setPosition(width - category.getWidth() - 10, category.getY());
            }
            if (category.getY() + category.getHeight() > height) {
                category.setPosition(category.getX(), height - category.getHeight() - 10);
            }
        }
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        GuiUtils.drawRect(matrixStack, 0, 0, width, height, BACKGROUND_COLOR);
        
        // Draw title
        GuiUtils.drawCenteredString(matrixStack, "§b§lLilo's Visuals§r", width / 2, 20, Color.WHITE, true);
        GuiUtils.drawCenteredString(matrixStack, "§7Right-click categories to open/close modules", 
            width / 2, 35, Color.GRAY, false);
        
        // Render categories
        for (Category category : categories) {
            category.render(matrixStack, mouseX, mouseY, partialTicks);
        }
        
        // Draw instructions
        String instructions = "§7[Left-Click] Toggle module | §7[Right-Click] Open/Close category | §7[Drag] Move category";
        GuiUtils.drawCenteredString(matrixStack, instructions, width / 2, height - 15, Color.GRAY, false);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        // Check categories in reverse order (top-most first)
        for (int i = categories.size() - 1; i >= 0; i--) {
            Category category = categories.get(i);
            if (category.mouseClicked(mouseX, mouseY, mouseButton)) {
                return true;
            }
        }
        
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int state) {
        for (Category category : categories) {
            if (category.mouseReleased(mouseX, mouseY, state)) {
                return true;
            }
        }
        
        return super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        for (Category category : categories) {
            if (category.mouseDragged(mouseX, mouseY, button, deltaX, deltaY)) {
                return true;
            }
        }
        
        return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
    }
    
    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        for (Category category : categories) {
            if (category.mouseScrolled(mouseX, mouseY, delta)) {
                return true;
            }
        }
        
        return super.mouseScrolled(mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
