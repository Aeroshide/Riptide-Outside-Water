package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.AllowModPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.aeroshide.riptide_outside_water.Riptide_outside_water.refresh;


@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity{


    @Shadow public abstract ItemCooldownManager getItemCooldownManager();


    @Shadow public abstract Arm getMainArm();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {

        if (this.getMainHandStack().getItem() == Items.TRIDENT && this.isTouchingWaterOrRain() && refresh)
        {
            this.getItemCooldownManager().remove(this.getItemCooldownManager().getGroup(this.getMainHandStack()));
        }


    }

}