package me.lucaslah.weatherchanger.keybinding;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.NotNull;

public abstract class Key {
    private static final KeyMapping.Category WEATHER_CHANGER_CATEGORY = KeyMapping.Category.register(Identifier.withDefaultNamespace("weatherchanger"));

    protected final KeyMapping keyBinding;

    public Key() {
        keyBinding = new KeyMapping(this.getDisplayName(), this.getKeyType(), this.getKey(), WEATHER_CHANGER_CATEGORY);
    }

    public abstract void onPress(@NotNull Minecraft client);
    public abstract String getId();

    public KeyMapping getKeyBinding() {
        return keyBinding;
    }

    public abstract boolean isEnabled();
    public abstract String getDisplayName();
    public abstract InputConstants.Type getKeyType();
    public abstract int getKey();
}
