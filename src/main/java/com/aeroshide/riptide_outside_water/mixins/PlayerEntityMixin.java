package com.aeroshide.riptide_outside_water.mixins;

import com.aeroshide.riptide_outside_water.AllowModPayload;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.aeroshide.riptide_outside_water.Riptide_outside_water.refresh;


@Mixin(Player.class)
public abstract class PlayerEntityMixin extends LivingEntity{


    @Shadow public abstract ItemCooldowns getCooldowns();

    @Shadow public abstract Inventory getInventory();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At("HEAD"))
    private void tick(CallbackInfo ci) {

        if (this.isInWaterOrRain() && refresh)
        {
            ItemStack offhandStack = this.getOffhandItem();
            for (ItemStack itemStack : this.getInventory().getNonEquipmentItems()) {
                if (itemStack.getItem() == Items.TRIDENT) {
                    this.getCooldowns().removeCooldown(this.getCooldowns().getCooldownGroup(itemStack));
                    break;
                }
            }

            if (offhandStack.getItem() == Items.TRIDENT) {
                this.getCooldowns().removeCooldown(this.getCooldowns().getCooldownGroup(offhandStack));
            }
        }
    }

}