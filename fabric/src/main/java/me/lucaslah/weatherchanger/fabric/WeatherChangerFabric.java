package me.lucaslah.weatherchanger.fabric;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.command.Command;
import me.lucaslah.weatherchanger.keybinding.Key;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class WeatherChangerFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        WeatherChanger.init(new WeatherChangerPlatformImpl());
        ClientLifecycleEvents.CLIENT_STOPPING.register(client -> WeatherChanger.shutdown());

        KeybindingManager keybindingManager = WeatherChanger.getKeybindingManager();

        for (Key key : keybindingManager.getEntries()) {
            KeyBindingHelper.registerKeyBinding(key.getKeyBinding());
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Key key : keybindingManager.getEntries()) {
                if (key.isEnabled() && key.getKeyBinding().wasPressed()) {
                    key.onPress(client);
                }
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            for (Command command : WeatherChanger.getCommandManager().getEntries()) {
                command.register(dispatcher);
            }
        });
    }
}
