package me.lucaslah.weatherchanger;

import com.google.gson.Gson;
import me.lucaslah.weatherchanger.keybind.KeybindManager;
import me.lucaslah.weatherchanger.keybindings.ToggleClearKey;
import me.lucaslah.weatherchanger.keybindings.ToggleOffKey;
import me.lucaslah.weatherchanger.keybindings.ToggleRainKey;
import me.lucaslah.weatherchanger.keybindings.ToggleThunderKey;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;

public class WeatherChanger implements ModInitializer {
    public static WeatherChanger instance;
    public static final Logger LOGGER = LogManager.getLogger("weather-changer");

    public Mode mode = Mode.OFF;
    @Override
    public void onInitialize() {
        instance = this;
        // Create config if it does not exist
        File configFile = new File(FabricLoader.getInstance().getConfigDir().resolve("weather-changer.json").toUri());
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
                writeModeToConfig();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        loadModeFromFile();

        // Keybindings
        new KeybindManager()
                .add(new ToggleOffKey())
                .add(new ToggleClearKey())
                .add(new ToggleRainKey())
                .add(new ToggleThunderKey());

        // Command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> dispatcher.register(ClientCommandManager.literal("clientweather")
                .then(ClientCommandManager.literal("off")
                        .executes(context -> {
                            setOff();
                            context.getSource().sendFeedback(Text.literal("Set client weather to: Off"));
                            return 1;
                        })
                ).then(ClientCommandManager.literal("clear")
                        .executes(context -> {
                            setClear();
                            context.getSource().sendFeedback(Text.literal("Set client weather to: Clear"));
                            return 1;
                        })
                ).then(ClientCommandManager.literal("rain")
                        .executes(context -> {
                            setRain();
                            context.getSource().sendFeedback(Text.literal("Set client weather to: Rain"));
                            return 1;
                        })
                ).then(ClientCommandManager.literal("thunder")
                        .executes(context -> {
                            setThunder();
                            context.getSource().sendFeedback(Text.literal("Set client weather to: Thunder"));
                            return 1;
                        })
                ).executes((context -> {
                    context.getSource().sendFeedback(Text.literal("Client weather is set to: " + getMode().toString().toLowerCase()));
                    return 1;
                }))
        ));
    }

    public static WeatherChanger getInstance() {
        return instance;
    }

    public enum Mode {
        OFF,
        CLEAR,
        RAIN,
        THUNDER
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Mode getMode() {
        return mode;
    }
    public void writeModeToConfig() {
        Gson gson = new Gson();
        Config config = new Config(getMode().toString().toUpperCase());

        Writer writer;
        try {
            writer = Files.newBufferedWriter(FabricLoader.getInstance().getConfigDir().resolve("weather-changer.json"));
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

    public void loadModeFromFile() {
        Gson gson = new Gson();

        Reader reader = null;
        try {
            reader = Files.newBufferedReader(FabricLoader.getInstance().getConfigDir().resolve("weather-changer.json"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Config config = gson.fromJson(reader, Config.class);

        setMode(Mode.valueOf(config.getMode().toUpperCase()));

        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setOff() {
        setMode(Mode.OFF);
        writeModeToConfig();
    }
    public void setClear() {
        setMode(Mode.CLEAR);
        writeModeToConfig();
    }
    public void setRain() {
        setMode(Mode.RAIN);
        writeModeToConfig();
    }
    public void setThunder() {
        setMode(Mode.THUNDER);
        writeModeToConfig();
    }
}
