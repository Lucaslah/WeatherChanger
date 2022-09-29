package me.lucaslah.weatherchanger.mixin;

import me.lucaslah.weatherchanger.WeatherChanger;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public abstract class WorldMixin extends World {
    protected WorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, RegistryEntry<DimensionType> dimension, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed, int maxChainedNeighborUpdates) {
        super(properties, registryRef, dimension, profiler, isClient, debugWorld, seed, maxChainedNeighborUpdates);
    }

    private WeatherChanger mod = WeatherChanger.getInstance();

    @Override
    public float getRainGradient(float delta) {
        if (mod.mode == WeatherChanger.Mode.OFF) {
            return super.getRainGradient(delta);
        } else if (mod.mode == WeatherChanger.Mode.CLEAR) {
            return 0;
        } else if (mod.mode == WeatherChanger.Mode.RAIN || mod.mode == WeatherChanger.Mode.THUNDER) {
            return 1;
        }

        return super.getRainGradient(delta);
    }

    @Override
    public float getThunderGradient(float delta) {
        if (mod.mode == WeatherChanger.Mode.OFF) {
            return super.getRainGradient(delta);
        } else if (mod.mode == WeatherChanger.Mode.CLEAR) {
            return 0;
        } else if (mod.mode == WeatherChanger.Mode.THUNDER) {
            return 1;
        }

        return super.getRainGradient(delta);
    }
}
