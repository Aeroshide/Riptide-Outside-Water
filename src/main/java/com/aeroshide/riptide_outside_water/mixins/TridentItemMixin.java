package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.AllowModPayload;
import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import com.aeroshide.riptide_outside_water.Riptide_outside_water;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

import static com.aeroshide.riptide_outside_water.Riptide_outside_water.LOG;
import static com.aeroshide.riptide_outside_water.Riptide_outside_water.cooldownTime;


@Mixin(TridentItem.class)
public class TridentItemMixin<T> extends Item {
    public TridentItemMixin(Properties settings) {
        super(settings);
    }

    @Unique
    boolean useOutsideWater = false;
    @Inject(method = "use", at = @At("HEAD"), cancellable = true, require = 0)
    private void useTrident(Level world, Player user, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir) {
        boolean canSpin;


        if (world.isClientSide()) {
            canSpin = !user.isInWaterOrRain()
                    && EnchantmentHelper.getTridentSpinAttackStrength(user.getItemInHand(hand), user) > 0.0F
                    && RiptideOutsideWaterClient.clientAllowMod;
            //LOG.info("CLIENT use() - canSpin: " + canSpin + ", hand: " + hand);
        } else {
            canSpin = !user.isInWaterOrRain()
                    && EnchantmentHelper.getTridentSpinAttackStrength(user.getItemInHand(hand), user) > 0.0F
                    && Riptide_outside_water.serverAllowMod;
            //LOG.info("SERVER use() - canSpin: " + canSpin + ", hand: " + hand);
        }

        if (canSpin) {
            useOutsideWater = true;
            user.startUsingItem(hand);
            //LOG.info((world.isClientSide() ? "CLIENT" : "SERVER") + " - set useOutsideWater=true");

            if (world.isClientSide()) {
                cir.setReturnValue(InteractionResult.SUCCESS);
            } else {
                cir.setReturnValue(InteractionResult.CONSUME);
            }
        }
    }

    @Redirect(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
    public boolean empty2(Player instance) {
        //LOG.info((instance.level().isClientSide() ? "CLIENT" : "SERVER") + " releaseUsing() - useOutsideWater: " + useOutsideWater);

        if (useOutsideWater && !instance.level().isClientSide()) {
            instance.getCooldowns().addCooldown(instance.getItemInHand(instance.getUsedItemHand()), cooldownTime);
            useOutsideWater = false;
        }
        return true;
    }


}