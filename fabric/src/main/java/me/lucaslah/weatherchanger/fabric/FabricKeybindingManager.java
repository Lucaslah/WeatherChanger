package me.lucaslah.weatherchanger.fabric;

import me.lucaslah.weatherchanger.keybinding.Key;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

public class FabricKeybindingManager extends KeybindingManager {
    @Override
    public void registerKeys() {
        for (Key key : getEntries()) {
            KeyBindingHelper.registerKeyBinding(key.getKeyBinding());
        }

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Key key : getEntries()) {
                if (key.isEnabled() && key.getKeyBinding().wasPressed()) {
                    key.onPress(client);
                }
            }
        });
    }
}
