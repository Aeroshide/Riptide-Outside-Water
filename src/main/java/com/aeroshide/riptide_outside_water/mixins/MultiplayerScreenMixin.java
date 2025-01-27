package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameMenuScreen.class)
public class MultiplayerScreenMixin {

    @Inject(method = "disconnect", at = @At("HEAD"))
    private void reset(CallbackInfo ci)
    {
        RiptideOutsideWaterClient.clientAllowMod = false;
    }
}
