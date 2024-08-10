package me.lucaslah.weatherchanger.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.command.Command;
import me.lucaslah.weatherchanger.config.WcMode;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static me.lucaslah.weatherchanger.WeatherChanger.sendClientMessage;

public class WeatherChangerCommand extends Command {
    @Override
    public <T> void register(CommandDispatcher<T> dispatcher) {
        LiteralArgumentBuilder<T> command = LiteralArgumentBuilder.literal("clientweather");

        command.then(LiteralArgumentBuilder.<T>literal("off")
                .executes(context -> {
                    WeatherChanger.setMode(WcMode.OFF);
                    sendClientMessage(Text.translatable("commands.weatherchanger.set.off"));
                    return 1;
                })
        );

        command.then(LiteralArgumentBuilder.<T>literal("clear")
                .executes(context -> {
                    WeatherChanger.setMode(WcMode.CLEAR);
                    sendClientMessage(Text.translatable("commands.weatherchanger.set.clear"));
                    return 1;
                })
        );

        command.then(LiteralArgumentBuilder.<T>literal("rain")
                .executes(context -> {
                    WeatherChanger.setMode(WcMode.RAIN);
                    sendClientMessage(Text.translatable("commands.weatherchanger.set.rain"));
                    return 1;
                })
        );

        command.then(LiteralArgumentBuilder.<T>literal("thunder")
                .executes(context -> {
                    WeatherChanger.setMode(WcMode.THUNDER);
                    sendClientMessage(Text.translatable("commands.weatherchanger.set.thunder"));
                    return 1;
                })
        );

        command.then(LiteralArgumentBuilder.<T>literal("toggleTimer")
                .executes(context -> {
                    WeatherChanger.toggleTimer();
                    Text message = WeatherChanger.isTimerEnabled() ? Text.translatable("commands.weatherchanger.timer.on") : Text.translatable("commands.weatherchanger.timer.off");
                    sendClientMessage(message);
                    return 1;
                })
        );

        LiteralCommandNode<T> node = dispatcher.register(command);
        dispatcher.register(LiteralArgumentBuilder.<T>literal("cweather").redirect(node));
    }

    @Override
    public Identifier getId() {
        return Identifier.of("weatherchanger", "corecommand");
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
