package me.lucaslah.weatherchanger.forge;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.keybinding.Key;
import net.minecraft.client.MinecraftClient;
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
    private static WeatherChangerForge instance;
    private ForgeKeybindingManager forgeKeybindingManager;

    public WeatherChangerForge() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::onClientSetup);
    }

    private void onClientSetup(FMLClientSetupEvent event) {
        instance = this;
        forgeKeybindingManager = new ForgeKeybindingManager();
        MinecraftForge.EVENT_BUS.register(this);
        WeatherChanger.init(new WeatherChangerPlatformImpl());
    }

    public static WeatherChangerForge getInstance() {
        return instance;
    }

    public ForgeKeybindingManager getKeybindingManager() {
        return forgeKeybindingManager;
    }

    @SubscribeEvent
    public void registerBindings(RegisterKeyMappingsEvent event) {
        for (Key key : forgeKeybindingManager.getEntries()) {
            event.register(key.getKeyBinding());
        }
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase == TickEvent.Phase.END) {
            for (Key key : forgeKeybindingManager.getEntries()) {
                if (key.isEnabled() && key.getKeyBinding().wasPressed()) {
                    key.onPress(MinecraftClient.getInstance());
                }
            }
        }
    }
}
