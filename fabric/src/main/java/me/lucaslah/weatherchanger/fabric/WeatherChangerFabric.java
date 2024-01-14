package me.lucaslah.weatherchanger.fabric;

import me.lucaslah.weatherchanger.WeatherChanger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WeatherChangerFabric implements ClientModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("weather-changer");

    @Override
    public void onInitializeClient() {
        WeatherChanger.init();
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> WeatherChanger.shutdown());
    }
}
