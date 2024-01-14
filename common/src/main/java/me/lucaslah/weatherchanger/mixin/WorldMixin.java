package me.lucaslah.weatherchanger.mixin;

import me.lucaslah.weatherchanger.WeatherChanger;
import me.lucaslah.weatherchanger.config.WcMode;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
}
