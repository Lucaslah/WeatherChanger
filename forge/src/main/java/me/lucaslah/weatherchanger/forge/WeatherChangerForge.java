package me.lucaslah.weatherchanger.forge;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.command.Command;
import me.lucaslah.weatherchanger.command.CommandManager;
import me.lucaslah.weatherchanger.keybinding.Key;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.fml.common.Mod;

@Mod("weatherchanger")
public class WeatherChangerForge {
    private final KeybindingManager keybindingManager;
    private final CommandManager commandManager;

    public WeatherChangerForge() {
        WeatherChanger.init(new WeatherChangerPlatformImpl());
        keybindingManager = WeatherChanger.getKeybindingManager();
        commandManager = WeatherChanger.getCommandManager();

        RegisterKeyMappingsEvent.BUS.addListener(this::registerBindings);
        RegisterClientCommandsEvent.BUS.addListener(this::onCommandRegister);
        TickEvent.ClientTickEvent.Post.BUS.addListener(this::onClientTick);
    }

    public void registerBindings(RegisterKeyMappingsEvent event) {
        for (Key key : keybindingManager.getEntries()) {
            event.register(key.getKeyBinding());
        }
    }

    public void onClientTick(TickEvent.ClientTickEvent.Post event) {
        for (Key key : keybindingManager.getEntries()) {
            if (key.isEnabled() && key.getKeyBinding().consumeClick()) {
                key.onPress(Minecraft.getInstance());
            }
        }
    }

    public void onCommandRegister(RegisterClientCommandsEvent event) {
        for (Command command : commandManager.getEntries()) {
            command.register(event.getDispatcher());
        }
    }
}
