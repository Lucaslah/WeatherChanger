package me.lucaslah.weatherchanger.fabric;

import me.lucaslah.weatherchanger.WeatherChangerPlatform;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class WeatherChangerPlatformImpl implements WeatherChangerPlatform {
    @Override
    public Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }

    @Override
    public KeybindingManager getKeybindingManager() {
        return new FabricKeybindingManager();
    }
}
