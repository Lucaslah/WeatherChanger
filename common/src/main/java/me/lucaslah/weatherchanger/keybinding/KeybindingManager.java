package me.lucaslah.weatherchanger.keybinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeybindingManager {
    private final HashMap<String, Key> entries = new HashMap<>();

    public KeybindingManager add(Key entry) {
        entries.put(entry.getId(), entry);
        return this;
    }

    public Key get(String id) {
        return entries.get(id);
    }

    public List<Key> getEntries() {
        if (!entries.isEmpty()) {
            return new ArrayList<>(entries.values());
        }

        return new ArrayList<>();
    }
}
