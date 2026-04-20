package com.lilo.visuals.mixin;

import com.lilo.visuals.Config;
import com.lilo.visuals.screen.CustomPauseMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.GameMenuScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class PauseMenuMixin {
    
    @Inject(method = "displayInGameMenu", at = @At("HEAD"), cancellable = true)
    private void onDisplayInGameMenu(CallbackInfo ci) {
        if (Config.ENABLE_CUSTOM_PAUSE_MENU.get()) {
            Minecraft.getInstance().displayGuiScreen(new CustomPauseMenu());
            ci.cancel();
        }
    }
}
