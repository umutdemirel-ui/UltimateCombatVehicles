package com.ucv;

import com.mojang.logging.LogUtils;
import com.ucv.common.weapons.WeaponFramework;
import com.ucv.common.weapons.WeaponManager;
import com.ucv.registry.UCVRegistries;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

/**
 * Forge entry point for Ultimate Combat &amp; Vehicles.
 *
 * Client-only code lives in {@code com.ucv.client} and is attached via
 * {@code Dist.CLIENT} subscribers so dedicated servers never load it.
 * Server gameplay validation lives in {@code com.ucv.server}.
 */
@Mod(UCV.MOD_ID)
public final class UltimateCombatVehicles {
    public static final Logger LOGGER = LogUtils.getLogger();

    public UltimateCombatVehicles(FMLJavaModLoadingContext context) {
        IEventBus modBus = context.getModEventBus();
        UCVRegistries.register(modBus);
        modBus.addListener(this::onCommonSetup);
        LOGGER.info("[UCV] Ultimate Combat & Vehicles loading (id '{}')", UCV.MOD_ID);
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        WeaponFramework.verify();
        UCVRegistries.logSummary();
        LOGGER.info("[UCV] Weapon framework ready ({} code, {} datapack definitions)",
                WeaponManager.codeDefinitionCount(),
                WeaponManager.dataPackDefinitionCount());
        LOGGER.info("[UCV] Common setup complete");
        LOGGER.info("[UCV] Network initialized");
    }
}
