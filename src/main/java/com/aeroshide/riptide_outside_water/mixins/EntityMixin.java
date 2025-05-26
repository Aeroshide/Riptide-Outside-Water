package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "isTouchingWaterOrRain", at = @At("RETURN"))
    private void onWaterCheck(CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && (Entity)(Object)this instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity)(Object)this;
        }
    }
}
