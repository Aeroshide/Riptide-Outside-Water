package com.aerohide.riptide_outside_water.mixins;

import com.aerohide.riptide_outside_water.RiptideOutsideWaterClient;
import com.aerohide.riptide_outside_water.Riptide_outside_water;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.aerohide.riptide_outside_water.Riptide_outside_water.cooldownTime;


@Mixin(TridentItem.class)
public class TridentItemMixin<T> extends Item {
    public TridentItemMixin(Settings settings) {
        super(settings);
    }

    @Unique
    boolean useOutsideWater = false;
    @Inject(method = "use", at = @At("HEAD"), cancellable = true, require = 0)
    private void useTrident(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        useOutsideWater = false;
        if ((!user.isTouchingWaterOrRain() && EnchantmentHelper.getTridentSpinAttackStrength(user.getStackInHand(hand), user) > 0.0F) && Riptide_outside_water.legal)
        {
            useOutsideWater = true;
            user.setCurrentHand(hand);
            cir.setReturnValue(ActionResult.CONSUME);
            cir.cancel();
        }

    }


    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    public boolean empty2(PlayerEntity instance) {
        if (useOutsideWater)
            instance.getItemCooldownManager().set(instance.getStackInHand(instance.getActiveHand()), cooldownTime);
        return true;
    }


}