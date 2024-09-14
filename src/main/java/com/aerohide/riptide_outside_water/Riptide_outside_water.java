package com.aerohide.riptide_outside_water;

import net.fabricmc.api.ModInitializer;

import net.minecraft.item.Item;

import net.minecraft.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aeroshide.rose_bush.config.Config;

public class Riptide_outside_water implements ModInitializer {
    public static final Logger LOG = LogManager.getLogger("RiptideOutsideWater");
    public static int cooldownTime = 200;
    public static boolean refresh = true;
    public static Config config = Config.getInstance("config/RiptideOutsideWater.json");

    @Override
    public void onInitialize() {

        if (config.getOption("appliedCooldown") == null)
        {
            config.setOption("appliedCooldown", 200.0);
        }
        if (config.getOption("refreshCooldownOnTouchingWaterOrRain") == null)
        {
            config.setOption("refreshCooldownOnTouchingWaterOrRain", true);
        }
        cooldownTime = ((Double) config.getOption("appliedCooldown")).intValue();
        refresh = ((boolean) config.getOption("refreshCooldownOnTouchingWaterOrRain"));
    }
}
