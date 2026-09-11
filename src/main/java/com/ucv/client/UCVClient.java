package com.ucv.client;

import com.ucv.UCV;
import com.ucv.UltimateCombatVehicles;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * Client-only bootstrap. Loaded only on the physical client.
 */
@Mod.EventBusSubscriber(modid = UCV.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class UCVClient {
    private UCVClient() {
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event) {
        UltimateCombatVehicles.LOGGER.info("[UCV] Client setup complete");
        UltimateCombatVehicles.LOGGER.info(
                "[UCV] Local player profile '{}'",
                Minecraft.getInstance().getUser().getName());
    }
}
