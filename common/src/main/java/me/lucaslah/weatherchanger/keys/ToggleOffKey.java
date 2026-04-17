package me.lucaslah.weatherchanger.keys;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;
import me.lucaslah.weatherchanger.keybinding.Key;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class ToggleOffKey extends Key {
    @Override
    public void onPress(@NotNull Minecraft client) {
        WeatherChanger.setMode(WcMode.OFF);
        WeatherChanger.sendOverlayMessage(Component.translatable("commands.weatherchanger.set.off"));
    }

    @Override
    public String getId() {
        return "weatherchanger:toggleoffkey";
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return "keys.weatherchanger.off.name";
    }

    @Override
    public InputConstants.Type getKeyType() {
        return InputConstants.Type.KEYSYM;
    }

    @Override
    public int getKey() {
        return -1;
    }
}
