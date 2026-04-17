package me.lucaslah.weatherchanger.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {
    private final HashMap<String, Command> entries = new HashMap<>();

    public CommandManager add(Command entry) {
        entries.put(entry.getId(), entry);
        return this;
    }

    public Command get(String id) {
        return entries.get(id);
    }

    public List<Command> getEntries() {
        if (!entries.isEmpty()) {
            return new ArrayList<>(entries.values());
        }

        return new ArrayList<>();
    }
}
