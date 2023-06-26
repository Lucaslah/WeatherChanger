package me.lucaslah.weatherchanger.mixin;

import me.lucaslah.weatherchanger.WeatherChanger;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldMixin {

    private WeatherChanger mod = WeatherChanger.getInstance();

    @Shadow
    protected float rainGradientPrev;
    @Shadow
    protected float rainGradient;

    @Inject(method = "getRainGradient", at = @At("HEAD"), cancellable = true)
    public void changeRainGradient(float delta, CallbackInfoReturnable<Float> callback) {
        if (mod.mode == WeatherChanger.Mode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mod.mode == WeatherChanger.Mode.RAIN || mod.mode == WeatherChanger.Mode.THUNDER) {
            callback.setReturnValue(1F);
        } else {
            callback.setReturnValue(MathHelper.lerp(delta, this.rainGradientPrev, this.rainGradient));
        }

        callback.cancel();
    }

    @Inject(method = "getThunderGradient", at = @At("HEAD"), cancellable = true)
    public void getThunderGradient(float delta, CallbackInfoReturnable<Float> callback) {
        if (mod.mode == WeatherChanger.Mode.CLEAR) {
            callback.setReturnValue(0F);
        } else if (mod.mode == WeatherChanger.Mode.THUNDER) {
            callback.setReturnValue(1F);
        } else {
            callback.setReturnValue(this.getRainGradient(delta));
        }

        callback.cancel();
    }

    public float getRainGradient(float delta) {
        return MathHelper.lerp(delta, this.rainGradientPrev, this.rainGradient);
    }

}
