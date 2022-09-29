package me.lucaslah.weatherchanger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeatherChanger implements ModInitializer {
    public static WeatherChanger instance;
    public static final Logger LOGGER = LogManager.getLogger("weather-changer");

    public Mode mode = Mode.OFF;
    @Override
    public void onInitialize() {
        instance = this;

        // Command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("clientweather")
                    .then(ClientCommandManager.literal("off")
                            .executes(context -> {
                                setMode(Mode.OFF);
                                context.getSource().sendFeedback(Text.literal("Set client weather to: Off"));
                                return 1;
                            })
                    ).then(ClientCommandManager.literal("clear")
                            .executes(context -> {
                                setMode(Mode.CLEAR);
                                context.getSource().sendFeedback(Text.literal("Set client weather to: Clear"));
                                return 1;
                            })
                    ).then(ClientCommandManager.literal("rain")
                            .executes(context -> {
                                setMode(Mode.RAIN);
                                context.getSource().sendFeedback(Text.literal("Set client weather to: Rain"));
                                return 1;
                            })
                    ).then(ClientCommandManager.literal("thunder")
                            .executes(context -> {
                                setMode(Mode.THUNDER);
                                context.getSource().sendFeedback(Text.literal("Set client weather to: Thunder"));
                                return 1;
                            })
                    )
            );
        });
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
}
