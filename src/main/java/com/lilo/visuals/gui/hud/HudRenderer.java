package com.lilo.visuals.gui.hud;

import com.lilo.visuals.Config;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.awt.*;

public class HudRenderer {
    private final Minecraft mc = Minecraft.getInstance();
    
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (!Config.ENABLE_HUD.get() || event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        
        MatrixStack matrixStack = event.getMatrixStack();
        int width = event.getWindow().getGuiScaledWidth();
        int height = event.getWindow().getGuiScaledHeight();
        
        int yOffset = 5;
        
        // Draw FPS
        if (Config.SHOW_FPS.get()) {
            String fpsText = "FPS: " + Minecraft.getDebugFPS();
            drawText(matrixStack, fpsText, 5, yOffset, Color.WHITE.getRGB());
            yOffset += 10;
        }
        
        // Draw Coordinates
        if (Config.SHOW_COORDS.get() && mc.player != null) {
            BlockPos pos = mc.player.blockPosition();
            String coordsText = String.format("XYZ: %.1f / %.1f / %.1f", 
                mc.player.getX(), mc.player.getY(), mc.player.getZ());
            drawText(matrixStack, coordsText, 5, yOffset, Color.WHITE.getRGB());
            yOffset += 10;
            
            String blockCoordsText = String.format("Block: %d, %d, %d", pos.getX(), pos.getY(), pos.getZ());
            drawText(matrixStack, blockCoordsText, 5, yOffset, Color.GRAY.getRGB());
            yOffset += 10;
        }
        
        // Draw Biome
        if (Config.SHOW_BIOME.get() && mc.player != null) {
            World world = mc.level;
            if (world != null) {
                BlockPos pos = mc.player.blockPosition();
                RegistryKey<World> dimensionKey = world.dimension();
                ResourceLocation dimensionId = dimensionKey.location();
                
                String biomeName = world.getBiome(pos).getRegistryName().getPath();
                String dimensionName = dimensionId.getPath();
                
                String biomeText = "Biome: " + biomeName + " (" + dimensionName + ")";
                drawText(matrixStack, biomeText, 5, yOffset, Color.CYAN.getRGB());
            }
        }
        
        // Draw module list (future expansion)
        drawModuleList(matrixStack, width);
    }
    
    private void drawText(MatrixStack matrixStack, String text, int x, int y, int color) {
        mc.font.draw(matrixStack, text, x, y, color);
    }
    
    private void drawModuleList(MatrixStack matrixStack, int width) {
        // Placeholder for module list - can be expanded later
        int moduleY = 5;
        String[] modules = {"Lilo's Visuals", "v1.0.0"};
        
        for (String module : modules) {
            int textWidth = mc.font.width(module);
            drawText(matrixStack, module, width - textWidth - 5, moduleY, new Color(0, 200, 255).getRGB());
            moduleY += 10;
        }
    }
}
