package me.lucaslah.weatherchanger.fabric;

import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class WeatherChangerExpectPlatformImpl {
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static KeybindingManager getKeybindingManager() {
        return new FabricKeybindingManager();
    }
}
