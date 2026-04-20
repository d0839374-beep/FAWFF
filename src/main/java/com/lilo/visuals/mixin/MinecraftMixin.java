package com.lilo.visuals.mixin;

import com.lilo.visuals.Config;
import com.lilo.visuals.screen.CustomMainMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
    
    @Inject(method = "displayGuiScreen", at = @At("HEAD"), cancellable = true)
    private void onDisplayGuiScreen(Screen guiScreen, CallbackInfo ci) {
        if (guiScreen instanceof MainMenuScreen && Config.ENABLE_CUSTOM_MAIN_MENU.get()) {
            Minecraft.getInstance().displayGuiScreen(new CustomMainMenu());
            ci.cancel();
        }
    }
}
