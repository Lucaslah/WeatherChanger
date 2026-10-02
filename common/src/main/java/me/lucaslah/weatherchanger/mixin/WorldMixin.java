package me.lucaslah.weatherchanger.mixin;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
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
    public abstract boolean isClientSide();

    @Shadow(remap = false)
    public abstract boolean canHaveWeather();

    @Unique
    private float weatherChanger$getRainGradientOg(float delta) {
        return Mth.lerp(delta, this.oRainLevel, this.rainLevel);
    }

    @Unique
    private float weatherChanger$getThunderGradientOg(float delta) {
        return Mth.lerp(delta, this.oThunderLevel, this.thunderLevel) * this.weatherChanger$getRainGradientOg(delta);
    }

    @Inject(method = "getRainLevel(F)F", at = @At("HEAD"), cancellable = true, remap = false)
    public void getRainGradient(float delta, CallbackInfoReturnable<Float> callback) {
        if (!this.isClientSide() || !this.canHaveWeather()) {
            return;
        }

        WcMode mode = WeatherChanger.getMode();

        if (mode == WcMode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mode == WcMode.RAIN || mode == WcMode.THUNDER) {
            callback.setReturnValue(1F);
        }
    }

    @Inject(method = "getThunderLevel(F)F", at = @At("HEAD"), cancellable = true, remap = false)
    public void getThunderGradient(float delta, CallbackInfoReturnable<Float> callback) {
        if (!this.isClientSide() || !this.canHaveWeather()) {
            return;
        }

        WcMode mode = WeatherChanger.getMode();

        if (mode == WcMode.CLEAR || mode == WcMode.RAIN) {
            callback.setReturnValue(0F);
        } else if (mode == WcMode.THUNDER) {
            callback.setReturnValue(1F);
        }
    }

    // Preserve the real weather for gameplay checks; only the visual gradients change.
    @Inject(method = "isRaining()Z", at = @At("HEAD"), cancellable = true, remap = false)
    public void isRaining(CallbackInfoReturnable<Boolean> callback) {
        if (this.isClientSide() && WeatherChanger.getMode() != WcMode.OFF) {
            callback.setReturnValue(this.canHaveWeather() && (double)this.weatherChanger$getRainGradientOg(1.0F) > 0.2);
        }
    }

    @Inject(method = "isThundering()Z", at = @At("HEAD"), cancellable = true, remap = false)
    public void isThundering(CallbackInfoReturnable<Boolean> callback) {
        if (this.isClientSide() && WeatherChanger.getMode() != WcMode.OFF) {
            callback.setReturnValue(this.canHaveWeather() && (double)this.weatherChanger$getThunderGradientOg(1.0F) > 0.9);
        }
    }
}
