package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

    @Inject(method = "disconnect(Lnet/minecraft/text/Text;)V", at = @At("HEAD"))
    private void reset(CallbackInfo ci)
    {
        RiptideOutsideWaterClient.clientAllowMod = false;
    }
}
