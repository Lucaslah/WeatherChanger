package me.lucaslah.weatherchanger.forge;

import me.lucaslah.weatherchanger.WeatherChangerPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class WeatherChangerPlatformImpl implements WeatherChangerPlatform {
    @Override
    public Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
