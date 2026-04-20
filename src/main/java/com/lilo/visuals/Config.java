package com.lilo.visuals;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec SPEC;
    
    public static final ForgeConfigSpec.BooleanValue ENABLE_HUD;
    public static final ForgeConfigSpec.IntValue HUD_COLOR;
    public static final ForgeConfigSpec.BooleanValue SHOW_FPS;
    public static final ForgeConfigSpec.BooleanValue SHOW_COORDS;
    public static final ForgeConfigSpec.BooleanValue SHOW_BIOME;
    
    public static final ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_MAIN_MENU;
    public static final ForgeConfigSpec.BooleanValue ENABLE_CUSTOM_PAUSE_MENU;
    
    static {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.push("hud");
        ENABLE_HUD = builder.comment("Enable HUD rendering").define("enable_hud", true);
        HUD_COLOR = builder.comment("HUD color (hex)").defineInRange("hud_color", 0xFF000000, 0, 0xFFFFFFFF);
        SHOW_FPS = builder.comment("Show FPS counter").define("show_fps", true);
        SHOW_COORDS = builder.comment("Show player coordinates").define("show_coords", true);
        SHOW_BIOME = builder.comment("Show current biome").define("show_biome", true);
        builder.pop();
        
        builder.push("screens");
        ENABLE_CUSTOM_MAIN_MENU = builder.comment("Replace vanilla main menu").define("custom_main_menu", true);
        ENABLE_CUSTOM_PAUSE_MENU = builder.comment("Replace vanilla pause menu").define("custom_pause_menu", true);
        builder.pop();
        
        SPEC = builder.build();
    }
}
