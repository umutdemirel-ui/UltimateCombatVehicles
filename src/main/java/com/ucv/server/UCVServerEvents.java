package com.ucv.server;

import com.ucv.UCV;
import com.ucv.UltimateCombatVehicles;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * Dedicated/integrated server lifecycle hooks.
 * Gameplay validation belongs on the server, not the client.
 */
@Mod.EventBusSubscriber(modid = UCV.MOD_ID)
public final class UCVServerEvents {
    private UCVServerEvents() {
    }

    @SubscribeEvent
    public static void onServerStarting(ServerStartingEvent event) {
        UltimateCombatVehicles.LOGGER.info("[UCV] Server starting ({})", event.getServer().getMotd());
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        UltimateCombatVehicles.LOGGER.info("[UCV] Server stopping");
    }
}
