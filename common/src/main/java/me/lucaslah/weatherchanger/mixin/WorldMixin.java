package me.lucaslah.weatherchanger.mixin;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(World.class)
public class WorldMixin {
    @Shadow
    protected float rainGradientPrev;
    @Shadow
    protected float rainGradient;
    @Shadow
    protected float thunderGradientPrev;
    @Shadow
    protected float thunderGradient;

    @Shadow @Final private RegistryEntry<DimensionType> dimensionEntry;

    @Unique
    private float weatherChanger$getRainGradientOg(float delta) {
        return MathHelper.lerp(delta, this.rainGradientPrev, this.rainGradient);
    }

    @Unique
    private float weatherChanger$getThunderGradientOg(float delta) {
        return MathHelper.lerp(delta, this.thunderGradientPrev, this.thunderGradient) * this.weatherChanger$getRainGradientOg(delta);
    }

    @Unique
    public DimensionType weatherChanger$getDimension() {
        return this.dimensionEntry.value();
    }

    @Inject(method = "getRainGradient", at = @At("HEAD"), cancellable = true)
    public void getRainGradient(float delta, CallbackInfoReturnable<Float> callback) {
        WcMode mode = WeatherChanger.getMode();

        if (mode == WcMode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mode == WcMode.RAIN || mode == WcMode.THUNDER) {
            callback.setReturnValue(1F);
        } else {
            callback.setReturnValue(MathHelper.lerp(delta, this.rainGradientPrev, this.rainGradient));
        }

        callback.cancel();
    }

    @Inject(method = "getThunderGradient", at = @At("HEAD"), cancellable = true)
    public void getThunderGradient(float delta, CallbackInfoReturnable<Float> callback) {
        WcMode mode = WeatherChanger.getMode();

        if (mode == WcMode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mode == WcMode.THUNDER) {
            callback.setReturnValue(1F);
        } else {
            callback.setReturnValue(MathHelper.lerp(delta, this.thunderGradientPrev, this.thunderGradient) * MathHelper.lerp(delta, this.rainGradientPrev, this.rainGradient));
        }

        callback.cancel();
    }

    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true)
    public void isRaining(CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue((double)this.weatherChanger$getRainGradientOg(1.0F) > 0.2);
        callback.cancel();
    }

    @Inject(method = "isThundering", at = @At("HEAD"), cancellable = true)
    public void isThundering(CallbackInfoReturnable<Boolean> callback) {

        if (this.weatherChanger$getDimension().hasSkyLight() && !(this.weatherChanger$getDimension().hasCeiling())) {
            callback.setReturnValue((double)this.weatherChanger$getThunderGradientOg(1.0F) > 0.9);
        } else {
            callback.setReturnValue(false);
        }

        callback.cancel();
    }
}
