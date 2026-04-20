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

public class CustomMainMenu extends Screen {
    private static final Color BACKGROUND_COLOR = new Color(10, 10, 15, 240);
    private static final Color ACCENT_COLOR = new Color(0, 200, 255);
    private static final Color BUTTON_COLOR = new Color(30, 30, 40, 200);
    private static final Color BUTTON_HOVER_COLOR = new Color(50, 50, 70, 220);
    
    private Button singlePlayerButton;
    private Button multiPlayerButton;
    private Button optionsButton;
    private Button quitButton;
    private Button modsButton;
    
    public CustomMainMenu() {
        super(StringTextComponent.literal("Main Menu"));
    }
    
    @Override
    protected void init() {
        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = width / 2;
        int startY = height / 4 + 96;
        int spacing = 24;
        
        singlePlayerButton = addButton(new Button(centerX - buttonWidth / 2, startY, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.singleplayer"), this::onSinglePlayerClick));
        
        multiPlayerButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.multiplayer"), this::onMultiPlayerClick));
        
        modsButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing * 2, buttonWidth, buttonHeight,
            new StringTextComponent("Mods / ClickGUI"), this::onModsClick));
        
        optionsButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing * 3, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.options"), this::onOptionsClick));
        
        quitButton = addButton(new Button(centerX - buttonWidth / 2, startY + spacing * 4, buttonWidth, buttonHeight,
            new TranslationTextComponent("menu.quit"), this::onQuitClick));
    }
    
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        // Render background
        RenderSystem.enableBlend();
        GuiUtils.drawRect(matrixStack, 0, 0, width, height, BACKGROUND_COLOR);
        
        // Draw gradient overlay for modern look
        GuiUtils.drawGradientRect(matrixStack, 0, 0, width, height, 
            new Color(0, 100, 150, 50).getRGB(), new Color(0, 50, 100, 80).getRGB());
        
        // Draw title
        String title = "§b§lLilo's Visuals§r";
        String subtitle = "§7Modern Minecraft Experience";
        
        GuiUtils.drawCenteredString(matrixStack, title, width / 2, height / 4 - 20, Color.WHITE, true);
        GuiUtils.drawCenteredString(matrixStack, subtitle, width / 2, height / 4, ACCENT_COLOR, false);
        
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
        
        // Draw version info
        String versionInfo = "§8Lilo's Visuals v1.0.0 | §7Forge 1.16.5";
        GuiUtils.drawString(matrixStack, versionInfo, 5, height - 15, Color.GRAY, false);
        
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
    
    private void onSinglePlayerClick(Button button) {
        minecraft.displayGuiScreen(new net.minecraft.client.gui.screen.MainMenuScreen());
        minecraft.displayGuiScreen(new net.minecraft.client.gui.MultiplayerScreen(this));
    }
    
    private void onMultiPlayerClick(Button button) {
        minecraft.displayGuiScreen(new net.minecraft.client.gui.MultiplayerScreen(this));
    }
    
    private void onModsClick(Button button) {
        minecraft.displayGuiScreen(LilosVisuals.clickGui);
    }
    
    private void onOptionsClick(Button button) {
        minecraft.displayGuiScreen(new net.minecraft.client.gui.settings.OptionsScreen(this, minecraft.gameSettings));
    }
    
    private void onQuitClick(Button button) {
        minecraft.execute(() -> minecraft.stop());
    }
    
    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }
    
    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
