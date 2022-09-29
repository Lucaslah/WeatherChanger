package me.lucaslah.weatherchanger.keybindings;

import me.lucaslah.weatherchanger.keybind.Key;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class ToggleOffKey extends Key {
    public ToggleOffKey() {
        super("ToggleOffKey");
    }

    @Override
    public void onPress(@NotNull MinecraftClient client) {
        mod.setOff();
        assert mc.player != null;
        mc.player.sendMessage(Text.of("Set client weather to: Off"), true);
    }

    @Override
    public Identifier getId() {
        return new Identifier("weatherchangerkeys", "toggleoffkey");
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
        return "Toggle Weather Off";
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
