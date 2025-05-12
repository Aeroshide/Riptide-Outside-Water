package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient.useOutsideWater;
@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow abstract boolean isBeingRainedOn();
    @Shadow public abstract boolean isTouchingWater();


    @Shadow public abstract World getWorld();

    @Inject(method = "isTouchingWaterOrRain", at = @At("HEAD"), cancellable = true)
    private void overrideWater(CallbackInfoReturnable<Boolean> cir) {
        useOutsideWater = false;
        if (!(isBeingRainedOn() || isTouchingWater()) && (!this.getWorld().isClient || RiptideOutsideWaterClient.clientAllowMod))
        {
            useOutsideWater = true;
            cir.setReturnValue(true);
        }

    }
}
