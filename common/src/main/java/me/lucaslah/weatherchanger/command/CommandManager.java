package me.lucaslah.weatherchanger.command;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandManager {
    private final HashMap<Identifier, Command> entries = new HashMap<>();

    public CommandManager add(Command entry) {
        entries.put(entry.getId(), entry);
        return this;
    }

    public Command get(Identifier identifier) {
        return entries.get(identifier);
    }

    public List<Command> getEntries() {
        if (!entries.isEmpty()) {
            return new ArrayList<>(entries.values());
        }

        return new ArrayList<>();
    }
}
