# Lilo's Visuals - Modern Forge 1.16.5 Mod

A modern visual mod for Minecraft Forge 1.16.5 featuring a custom ClickGUI, HUD, and screen replacements.

## Features

### 🎨 Custom ClickGUI
- Modern, sleek design with smooth animations
- Draggable categories (Combat, Movement, Render, Player, Misc)
- Toggle modules with left-click
- Open/close categories with right-click
- Custom color scheme with cyan accents

### 📊 HUD (Heads-Up Display)
- FPS counter
- Player coordinates (XYZ and block position)
- Current biome and dimension info
- Module list display
- Configurable via config file

### 🖥️ Screen Replacements
- **Custom Main Menu**: Modern design with gradient backgrounds and styled buttons
- **Custom Pause Menu**: Sleek in-game menu with quick access to ClickGUI

### ⚙️ Configuration
All features are configurable through the Forge config system:
- Enable/disable HUD
- Show/hide FPS, coordinates, biome
- Enable/disable custom main menu
- Enable/disable custom pause menu

## Installation

1. Install Minecraft Forge 1.16.5 (version 36.2.39 recommended)
2. Download the mod JAR file
3. Place the JAR file in your `.minecraft/mods` folder
4. Launch Minecraft with Forge profile

## Controls

- **Right Shift** - Open ClickGUI (default keybind, configurable)
- **Left Click** - Toggle modules in ClickGUI
- **Right Click** - Open/close category in ClickGUI
- **Drag** - Move categories in ClickGUI

## Building from Source

```bash
# Clone the repository
git clone https://github.com/yourusername/LilosVisuals.git
cd LilosVisuals

# Build the mod
./gradlew build

# The compiled JAR will be in build/libs/
```

## Project Structure

```
src/main/java/com/lilo/visuals/
├── LilosVisuals.java          # Main mod class
├── Config.java                # Configuration handling
├── gui/
│   ├── ClickGui.java          # Main ClickGUI screen
│   ├── components/
│   │   ├── GuiUtils.java      # Rendering utilities
│   │   ├── Category.java      # Category component
│   │   └── ModuleButton.java  # Module button component
│   └── hud/
│       └── HudRenderer.java   # HUD rendering
├── screen/
│   ├── CustomMainMenu.java    # Custom main menu
│   └── CustomPauseMenu.java   # Custom pause menu
└── mixin/
    ├── MinecraftMixin.java    # Main menu replacement mixin
    └── PauseMenuMixin.java    # Pause menu replacement mixin
```

## Dependencies

- Minecraft 1.16.5
- Forge 36.2.39
- Java 8

## License

MIT License - Feel free to use and modify!

## Credits

- Created by Lilo
- Built with ForgeGradle and MixinGradle
