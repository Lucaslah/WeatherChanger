package me.lucaslah.weatherchanger.fabric;

import me.lucaslah.weatherchanger.WeatherChanger;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class WeatherChangerFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeatherChanger.init(new WeatherChangerPlatformImpl());
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> WeatherChanger.shutdown());
    }
}
