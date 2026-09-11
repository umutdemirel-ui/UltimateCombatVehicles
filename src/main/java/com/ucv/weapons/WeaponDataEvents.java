package com.ucv.weapons;

import com.ucv.UCV;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = UCV.MOD_ID)
public final class WeaponDataEvents {
    private WeaponDataEvents() {
    }

    @SubscribeEvent
    public static void onAddReloadListeners(AddReloadListenerEvent event) {
        event.addListener(WeaponDefinitionLoader.INSTANCE);
    }
}
