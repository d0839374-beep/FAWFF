package com.lilo.visuals.gui.components;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class GuiUtils {
    private static final Minecraft mc = Minecraft.getInstance();
    
    public static void drawRect(MatrixStack matrixStack, int x, int y, int width, int height, Color color) {
        drawRect(matrixStack, x, y, x + width, y + height, color.getRGB());
    }
    
    public static void drawRect(MatrixStack matrixStack, int x1, int y1, int x2, int y2, int color) {
        MatrixStack.Entry entry = matrixStack.last();
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        
        float alpha = (float)(color >> 24 & 255) / 255.0F;
        float red = (float)(color >> 16 & 255) / 255.0F;
        float green = (float)(color >> 8 & 255) / 255.0F;
        float blue = (float)(color & 255) / 255.0F;
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(entry.pose(), x1, y2, 0).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(entry.pose(), x2, y2, 0).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(entry.pose(), x2, y1, 0).color(red, green, blue, alpha).endVertex();
        bufferBuilder.vertex(entry.pose(), x1, y1, 0).color(red, green, blue, alpha).endVertex();
        tessellator.end();
        
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    
    public static void drawGradientRect(MatrixStack matrixStack, int x1, int y1, int x2, int y2, int color1, int color2) {
        MatrixStack.Entry entry = matrixStack.last();
        
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableTexture();
        
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuilder();
        
        float alpha1 = (float)(color1 >> 24 & 255) / 255.0F;
        float red1 = (float)(color1 >> 16 & 255) / 255.0F;
        float green1 = (float)(color1 >> 8 & 255) / 255.0F;
        float blue1 = (float)(color1 & 255) / 255.0F;
        
        float alpha2 = (float)(color2 >> 24 & 255) / 255.0F;
        float red2 = (float)(color2 >> 16 & 255) / 255.0F;
        float green2 = (float)(color2 >> 8 & 255) / 255.0F;
        float blue2 = (float)(color2 & 255) / 255.0F;
        
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
        bufferBuilder.vertex(entry.pose(), x1, y2, 0).color(red1, green1, blue1, alpha1).endVertex();
        bufferBuilder.vertex(entry.pose(), x2, y2, 0).color(red2, green2, blue2, alpha2).endVertex();
        bufferBuilder.vertex(entry.pose(), x2, y1, 0).color(red2, green2, blue2, alpha2).endVertex();
        bufferBuilder.vertex(entry.pose(), x1, y1, 0).color(red1, green1, blue1, alpha1).endVertex();
        tessellator.end();
        
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
    }
    
    public static void drawRoundedRect(MatrixStack matrixStack, int x, int y, int width, int height, int radius, Color color) {
        // Simplified rounded rect - can be enhanced with proper bezier curves
        drawRect(matrixStack, x + radius, y, width - radius * 2, height, color);
        drawRect(matrixStack, x, y + radius, width, height - radius * 2, color);
        
        // Draw corners (simplified as rectangles for now)
        drawRect(matrixStack, x + radius, y, radius, radius, color);
        drawRect(matrixStack, x + width - radius * 2, y, radius, radius, color);
        drawRect(matrixStack, x + radius, y + height - radius, radius, radius, color);
        drawRect(matrixStack, x + width - radius * 2, y + height - radius, radius, radius, color);
    }
    
    public static void drawString(MatrixStack matrixStack, String text, int x, int y, Color color, boolean shadow) {
        FontRenderer fontRenderer = mc.font;
        if (shadow) {
            fontRenderer.drawShadow(matrixStack, text, x, y, color.getRGB());
        } else {
            fontRenderer.draw(matrixStack, text, x, y, color.getRGB());
        }
    }
    
    public static void drawCenteredString(MatrixStack matrixStack, String text, int x, int y, Color color, boolean shadow) {
        FontRenderer fontRenderer = mc.font;
        int textWidth = fontRenderer.width(text);
        if (shadow) {
            fontRenderer.drawShadow(matrixStack, text, x - textWidth / 2, y, color.getRGB());
        } else {
            fontRenderer.draw(matrixStack, text, x - textWidth / 2, y, color.getRGB());
        }
    }
    
    public static int getStringWidth(String text) {
        return mc.font.width(text);
    }
    
    public static int getStringHeight() {
        return mc.font.lineHeight;
    }
}
