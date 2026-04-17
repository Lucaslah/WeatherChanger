package me.lucaslah.weatherchanger.mixin;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = Level.class, remap = false)
public abstract class WorldMixin {
    @Shadow(remap = false)
    protected float oRainLevel;
    @Shadow(remap = false)
    protected float rainLevel;
    @Shadow(remap = false)
    protected float oThunderLevel;
    @Shadow(remap = false)
    protected float thunderLevel;

    @Shadow(remap = false)
    public abstract DimensionType dimensionType();

    @Unique
    private float weatherChanger$getRainGradientOg(float delta) {
        return Mth.lerp(delta, this.oRainLevel, this.rainLevel);
    }

    @Unique
    private float weatherChanger$getThunderGradientOg(float delta) {
        return Mth.lerp(delta, this.oThunderLevel, this.thunderLevel) * this.weatherChanger$getRainGradientOg(delta);
    }

    @Unique
    public DimensionType weatherChanger$getDimension() {
        return this.dimensionType();
    }

    @Inject(method = "getRainLevel", at = @At("HEAD"), cancellable = true, remap = false)
    public void getRainGradient(float delta, CallbackInfoReturnable<Float> callback) {
        WcMode mode = WeatherChanger.getMode();

        if (mode == WcMode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mode == WcMode.RAIN || mode == WcMode.THUNDER) {
            callback.setReturnValue(1F);
        } else {
            callback.setReturnValue(Mth.lerp(delta, this.oRainLevel, this.rainLevel));
        }

        callback.cancel();
    }

    @Inject(method = "getThunderLevel", at = @At("HEAD"), cancellable = true, remap = false)
    public void getThunderGradient(float delta, CallbackInfoReturnable<Float> callback) {
        WcMode mode = WeatherChanger.getMode();

        if (mode == WcMode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mode == WcMode.THUNDER) {
            callback.setReturnValue(1F);
        } else {
            callback.setReturnValue(Mth.lerp(delta, this.oThunderLevel, this.thunderLevel) * Mth.lerp(delta, this.oRainLevel, this.rainLevel));
        }

        callback.cancel();
    }

    @Inject(method = "isRaining", at = @At("HEAD"), cancellable = true, remap = false)
    public void isRaining(CallbackInfoReturnable<Boolean> callback) {
        callback.setReturnValue((double)this.weatherChanger$getRainGradientOg(1.0F) > 0.2);
        callback.cancel();
    }

    @Inject(method = "isThundering", at = @At("HEAD"), cancellable = true, remap = false)
    public void isThundering(CallbackInfoReturnable<Boolean> callback) {
        if (this.weatherChanger$getDimension().hasSkyLight() && !(this.weatherChanger$getDimension().hasCeiling())) {
            callback.setReturnValue((double)this.weatherChanger$getThunderGradientOg(1.0F) > 0.9);
        } else {
            callback.setReturnValue(false);
        }

        callback.cancel();
    }
}
