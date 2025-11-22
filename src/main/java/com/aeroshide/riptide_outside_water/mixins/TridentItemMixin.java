package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.AllowModPayload;
import com.aeroshide.riptide_outside_water.RiptideOutsideWaterClient;
import com.aeroshide.riptide_outside_water.Riptide_outside_water;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.entity.event.v1.EntityElytraEvents;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.TridentItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
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
    public TridentItemMixin(Settings settings) {
        super(settings);
    }

    @Unique
    boolean useOutsideWater = false;
    @Inject(method = "use", at = @At("HEAD"), cancellable = true, require = 0)
    private void useTrident(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        boolean canSpin = !user.isTouchingWaterOrRain()
                && EnchantmentHelper.getTridentSpinAttackStrength(user.getStackInHand(hand), user) > 0.0F
                && (!world.isClient() || RiptideOutsideWaterClient.clientAllowMod || isIntegratedServer());

        if (canSpin) {
            useOutsideWater = true;
            user.setCurrentHand(hand);

            if (world.isClient()) {
                cir.setReturnValue(ActionResult.SUCCESS);
            } else {
                cir.setReturnValue(ActionResult.CONSUME);
            }
            cir.cancel();
        }
    }


    @Unique
    // equally bad code, in a rush.
    private static boolean isIntegratedServer() {
        try {
            if (FabricLoader.getInstance().getEnvironmentType() != EnvType.CLIENT) return false;

            Class<?> mcClass = Class.forName("net.minecraft.client.MinecraftClient");
            java.lang.reflect.Method getInstance = mcClass.getMethod("getInstance");
            Object mc = getInstance.invoke(null);
            java.lang.reflect.Method isIntegrated = mcClass.getMethod("isIntegratedServerRunning");
            Object val = isIntegrated.invoke(mc);
            return val instanceof Boolean && (Boolean) val;
        } catch (Throwable t) {
            return false;
        }
    }

    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    public boolean empty2(PlayerEntity instance) {
        if (useOutsideWater && !instance.getEntityWorld().isClient()) {
            instance.getItemCooldownManager().set(instance.getStackInHand(instance.getActiveHand()), cooldownTime);
            useOutsideWater = false;
        }
        return true;
    }


}