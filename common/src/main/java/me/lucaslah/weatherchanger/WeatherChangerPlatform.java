package me.lucaslah.weatherchanger;

import java.nio.file.Path;

public interface WeatherChangerPlatform {
    Path getConfigDirectory();
}
