package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.AllowModPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
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

    @Shadow public abstract PlayerInventory getInventory();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {

        if (this.isTouchingWaterOrRain() && refresh)
        {
            ItemStack offhandStack = this.getOffHandStack();
            for (ItemStack itemStack : this.getInventory().getMainStacks()) {
                if (itemStack != null && itemStack.getItem() == Items.TRIDENT) {
                    this.getItemCooldownManager().remove(this.getItemCooldownManager().getGroup(itemStack));
                    break;
                }
            }

            if (offhandStack != null && offhandStack.getItem() == Items.TRIDENT) {
                this.getItemCooldownManager().remove(this.getItemCooldownManager().getGroup(offhandStack));
            }
        }
    }

}