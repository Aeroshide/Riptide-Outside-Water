package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isInWaterOrRain", at = @At("RETURN"))
    private void onWaterCheck(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && (Entity)(Object)this instanceof Player) {
            Player player = (Player)(Object)this;
        }
    }
}
