package com.lilo.visuals;

import com.lilo.visuals.gui.ClickGui;
import com.lilo.visuals.gui.hud.HudRenderer;
import net.minecraftforge.client.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("lilovisuals")
public class LilosVisuals {
    public static final String MOD_ID = "lilovisuals";
    public static final Logger LOGGER = LogManager.getLogger();
    
    public static ClickGui clickGui;
    public static HudRenderer hudRenderer;
    
    public LilosVisuals() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        
        modEventBus.addListener(this::clientSetup);
        
        MinecraftForge.EVENT_BUS.register(this);
        
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC);
    }
    
    private void clientSetup(final FMLClientSetupEvent event) {
        clickGui = new ClickGui();
        hudRenderer = new HudRenderer();
        
        MinecraftForge.EVENT_BUS.register(hudRenderer);
        
        LOGGER.info("Lilo's Visuals initialized successfully!");
    }
}
