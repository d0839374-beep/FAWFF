package com.lilo.visuals.screen;

import com.lilo.visuals.LilosVisuals;
import com.lilo.visuals.gui.ClickGui;
import com.lilo.visuals.gui.components.GuiUtils;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class CustomPauseMenu extends Screen {
    private static final Color BACKGROUND_COLOR = new Color(10, 10, 15, 200);
    private static final Color ACCENT_COLOR = new Color(0, 200, 255);
    private static final Color BUTTON_COLOR = new Color(30, 30, 40, 200);
    private static final Color BUTTON_HOVER_COLOR = new Color(50, 50, 70, 220);
    
    private Button resumeButton;
    private Button optionsButton;
    private Button clickGuiButton;
    private Button disconnectButton;
    
    public CustomPauseMenu() {
        super(StringTextComponent.literal("Game Menu"));
    }
    
    @Override
    protected void init() {
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = width / 2;
        int startY = height / 4 + 72;
        int spacing = 24;
        
        resumeButton = addButton(new Button(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.returnToGame"), this::onResumeClick));
        
        optionsButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.options"), this::onOptionsClick));
        
        clickGuiButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight,
            new StringTextComponent("ClickGUI"), this::onClickGuiClick));
        
        disconnectButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.disconnect"), this::onDisconnectClick));
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render background blur effect (simplified as semi-transparent overlay)
        RenderSystem.enableBlend();
        GuiUtils.drawRect(matrixStack, 0, 0, width, height, BACKGROUND_COLOR);
        
        // Draw gradient overlay
        GuiUtils.drawGradientRect(matrixStack, 0, 0, width, height,
            new Color(0, 100, 150, 30).getRGB(), new Color(0, 50, 100, 50).getRGB());
        
        // Draw title
        String title = "§b§lGame Menu§r";
        GuiUtils.drawCenteredString(matrixStack, title, width / 2, height / 4 - 24, Color.WHITE, true);
        
        // Render buttons with custom styling
        RenderSystem.disableTexture();
        for (Button button : buttons) {
            boolean isHovered = mouseX >= button.x && mouseX <= button.x + button.getWidth() &&
                mouseY >= button.y && mouseY <= button.y + button.getHeight();
            
            Color bgColor = isHovered ? BUTTON_HOVER_COLOR : BUTTON_COLOR;
            GuiUtils.drawRect(matrixStack, button.x, button.y, button.getWidth(), button.getHeight(), bgColor);
            
            // Draw accent line on hover
            if (isHovered) {
                GuiUtils.drawRect(matrixStack, button.x, button.y, 3, button.getHeight(), ACCENT_COLOR);
            }
        }
        RenderSystem.enableTexture();
        
        // Render button text
        for (Button button : buttons) {
            GuiUtils.drawCenteredString(matrixStack, button.getMessage().getString(),
                button.x + button.getWidth() / 2,
                button.y + (button.getHeight() - minecraft.font.lineHeight) / 2,
                Color.WHITE, true);
        }
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    private void onResumeClick(Button button) {
        minecraft.displayGuiScreen(null);
        minecraft.mouseHelper.grabMouse();
    }
    
    private void onOptionsClick(Button button) {
        minecraft.displayGuiScreen(new net.minecraft.client.gui.settings.OptionsScreen(this, minecraft.gameSettings));
    }
    
    private void onClickGuiClick(Button button) {
        minecraft.displayGuiScreen(LilosVisuals.clickGui);
    }
    
    private void onDisconnectClick(Button button) {
        resumeButton.onPress();
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return true;
    }
    
    @Override
    public boolean isPauseScreen() {
        return true;
    }
}
