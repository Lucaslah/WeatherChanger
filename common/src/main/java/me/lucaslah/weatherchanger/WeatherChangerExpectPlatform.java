package me.lucaslah.weatherchanger;

import dev.architectury.injectables.annotations.ExpectPlatform;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;

import java.nio.file.Path;

public class WeatherChangerExpectPlatform {
    @ExpectPlatform
    public static Path getConfigDirectory() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static KeybindingManager getKeybindingManager() {
        throw new AssertionError();
    }
}
