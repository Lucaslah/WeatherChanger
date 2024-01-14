package me.lucaslah.weatherchanger.keys;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;
import me.lucaslah.weatherchanger.keybinding.Key;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ToggleThunderKey extends Key {
    public ToggleThunderKey() {
        super("ToggleThunderKey");
    }

    @Override
    public void onPress(@NotNull MinecraftClient client) {
        WeatherChanger.setMode(WcMode.THUNDER);
        assert mc.player != null;
        mc.player.sendMessage(Text.of("Set client weather to: Thunder"), true);
    }

    @Override
    public Identifier getId() {
        return new Identifier("weatherchanger", "togglethunderkey");
    }

    @Override
    public KeyBinding getKeyBinding() {
        return this.keybind;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return "Toggle Weather Thunder";
    }

    @Override
    public InputUtil.Type getKeyType() {
        return InputUtil.Type.KEYSYM;
    }

    @Override
    public String getCategory() {
        return "Weather Changer";
    }

    @Override
    public int getKey() {
        return -1;
    }
}
