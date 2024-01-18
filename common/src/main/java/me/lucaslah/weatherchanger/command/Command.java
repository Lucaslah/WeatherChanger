package me.lucaslah.weatherchanger.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.util.Identifier;

public abstract class Command {
    public abstract <T> void register(CommandDispatcher<T> dispatcher);
    public abstract Identifier getId();
    public abstract boolean isEnabled();
}
