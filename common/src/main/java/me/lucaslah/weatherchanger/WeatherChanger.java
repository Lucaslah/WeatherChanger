package me.lucaslah.weatherchanger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.lucaslah.weatherchanger.config.WcConfig;
import me.lucaslah.weatherchanger.config.WcMode;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import me.lucaslah.weatherchanger.keys.ToggleClearKey;
import me.lucaslah.weatherchanger.keys.ToggleOffKey;
import me.lucaslah.weatherchanger.keys.ToggleRainKey;
import me.lucaslah.weatherchanger.keys.ToggleThunderKey;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.util.Objects;

public class WeatherChanger {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static WcMode currentMode = WcMode.OFF;

    public static void init() {
        boolean fileCreated = false;
        File configPath = getConfigFile();

        if (!configPath.exists()) {
            try {
                fileCreated = configPath.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (fileCreated) {
            writeConfig();
        } else {
            loadConfig();
        }

        KeybindingManager keybindingManager = WeatherChangerExpectPlatform.getKeybindingManager();

        keybindingManager
                .add(new ToggleClearKey())
                .add(new ToggleOffKey())
                .add(new ToggleRainKey())
                .add(new ToggleThunderKey());

        keybindingManager.registerKeys();
    }

    private static void loadConfig() {
        Reader reader;

        try {
            reader = Files.newBufferedReader(getConfigFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        WcConfig config = gson.fromJson(reader, WcConfig.class);

        if (!Objects.equals(config.getVersion(), "1.0.0")) {
            throw new RuntimeException("Invalid weather changer config version! Try removing the config file and try again.");
        }

        currentMode = config.getMode();

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeConfig() {
        WcConfig config = new WcConfig();
        config.setMode(currentMode);
        config.setVersion("1.0.0");

        Writer writer;
        try {
            writer = Files.newBufferedWriter(getConfigFile().toPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        gson.toJson(config, writer);

        try {
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static File getConfigFile() {
        return WeatherChangerExpectPlatform.getConfigDirectory().resolve("weather-changer.json").toFile();
    }

    /**
     * Reloads the config file from disk
     * @return if the reload was successful
     */
    public static boolean reloadConfig() {
        if (getConfigFile().exists() && getConfigFile().isFile()) {
            loadConfig();
            return true;
        }

        return false;
    }

    /**
     * Sets the current mode
     * @param mode the mode
     */
    public static void setMode(WcMode mode) {
        currentMode = mode;
    }

    public static WcMode getMode() {
        return currentMode;
    }

    /**
     * Call on shutdown
     */
    public static void shutdown() {
        writeConfig();
    }
}