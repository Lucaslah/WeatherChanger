package me.lucaslah.weatherchanger.keybind;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeybindManager {
    private final HashMap<Identifier, Key> entries = new HashMap<>();

    private static KeybindManager INSTANCE;

    public KeybindManager() {
        INSTANCE = this;

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            for (Key key : getEntries()) {
                if (key.isEnabled() && key.getKeyBinding().wasPressed()) {
                    key.onPress(client);
                }
            }
        });
    }

    public KeybindManager add(Key entry) {
        entries.put(entry.getId(), entry);
        return this;
    }

    public Key get(Identifier identifier) {
        return entries.get(identifier);
    }

    public List<Key> getEntries() {
        if (entries.size() > 0) {
            return new ArrayList<>(entries.values());
        }
        return new ArrayList<>();
    }

    public static KeybindManager getInstance() {
        return INSTANCE;
    }
}