package me.lucaslah.weatherchanger.keybinding;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class Key {
    public KeyBinding keybind;
    public MinecraftClient mc = MinecraftClient.getInstance();

    private static final KeyBinding.Category CATEGORY = KeyBinding.Category.create(Identifier.of("weatherchanger"));

    public Key() {
        keybind = new KeyBinding(this.getDisplayName(), this.getKeyType(), this.getKey(), CATEGORY);
    }

    public abstract void onPress(@NotNull MinecraftClient client);
    public abstract Identifier getId();
    public abstract KeyBinding getKeyBinding();
    public abstract boolean isEnabled();
    public abstract String getDisplayName();
    public abstract InputUtil.Type getKeyType();
    public abstract int getKey();
}
