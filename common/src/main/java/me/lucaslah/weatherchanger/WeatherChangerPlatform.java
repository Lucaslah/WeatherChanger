package me.lucaslah.weatherchanger;

import me.lucaslah.weatherchanger.keybinding.KeybindingManager;

import java.nio.file.Path;

public interface WeatherChangerPlatform {
    Path getConfigDirectory();
    KeybindingManager getKeybindingManager();
}
