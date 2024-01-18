package me.lucaslah.weatherchanger.forge;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.command.Command;
import me.lucaslah.weatherchanger.command.CommandManager;
import me.lucaslah.weatherchanger.keybinding.Key;
import me.lucaslah.weatherchanger.keybinding.KeybindingManager;
import net.minecraft.client.MinecraftClient;
import net.minecraftforge.client.event.RegisterClientCommandsEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod("weatherchanger")
public class WeatherChangerForge {
    private final KeybindingManager keybindingManager;
    private final CommandManager commandManager;

    public WeatherChangerForge() {
        WeatherChanger.init(new WeatherChangerPlatformImpl());
        keybindingManager = WeatherChanger.getKeybindingManager();
        commandManager = WeatherChanger.getCommandManager();

        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
        modEventBus.addListener(this::registerBindings);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void registerBindings(RegisterKeyMappingsEvent event) {
        for (Key key : keybindingManager.getEntries()) {
            event.register(key.getKeyBinding());
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (Key key : keybindingManager.getEntries()) {
                if (key.isEnabled() && key.getKeyBinding().wasPressed()) {
                    key.onPress(MinecraftClient.getInstance());
                }
            }
        }
    }

    @SubscribeEvent
    public void onCommandRegister(RegisterClientCommandsEvent event) {
        for (Command command : commandManager.getEntries()) {
            command.register(event.getDispatcher());
        }
    }
}
