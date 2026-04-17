package me.lucaslah.weatherchanger.command;

import com.mojang.brigadier.CommandDispatcher;

public abstract class Command {
    public abstract <T> void register(CommandDispatcher<T> dispatcher);
    public abstract String getId();
    public abstract boolean isEnabled();
}
