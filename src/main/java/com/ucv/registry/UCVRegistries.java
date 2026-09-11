package com.ucv.registry;

import com.ucv.UltimateCombatVehicles;
import net.minecraftforge.eventbus.api.IEventBus;

/**
 * Boots every deferred register in a safe order and prints a one-shot summary.
 *
 * Order: blocks → items (block items depend on blocks) → entities / sounds /
 * menus / particles → creative tabs (tabs read items).
 */
public final class UCVRegistries {
    private UCVRegistries() {
    }

    public static void register(IEventBus modBus) {
        ModBlocks.register(modBus);
        ModItems.register(modBus);
        ModEntities.register(modBus);
        ModSounds.register(modBus);
        ModMenus.register(modBus);
        ModParticles.register(modBus);
        ModCreativeTabs.register(modBus);
    }

    public static void logSummary() {
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} items", ModItems.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} blocks", ModBlocks.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} entities", ModEntities.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} sounds", ModSounds.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} menus", ModMenus.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} particles", ModParticles.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registered {} creative tabs", ModCreativeTabs.count());
        UltimateCombatVehicles.LOGGER.info("[UCV] Registry architecture ready");
    }
}
