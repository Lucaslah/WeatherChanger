package me.lucaslah.weatherchanger.keybinding;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeybindingManager {
    private final HashMap<Identifier, Key> entries = new HashMap<>();

    public KeybindingManager add(Key entry) {
        entries.put(entry.getId(), entry);
        return this;
    }

    public Key get(Identifier identifier) {
        return entries.get(identifier);
    }

    public List<Key> getEntries() {
        if (!entries.isEmpty()) {
            return new ArrayList<>(entries.values());
        }

        return new ArrayList<>();
    }
}
