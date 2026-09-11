package com.ucv.registry;

import com.ucv.UCV;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

/**
 * Central entity deferred register for weapons projectiles and vehicles.
 */
public final class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, UCV.MOD_ID);

    private ModEntities() {
    }

    public static void register(IEventBus modBus) {
        ENTITIES.register(modBus);
    }

    public static <T extends Entity> RegistryObject<EntityType<T>> register(
            String name,
            EntityType.EntityFactory<T> factory,
            MobCategory category,
            float width,
            float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, category)
                .sized(width, height)
                .clientTrackingRange(10)
                .updateInterval(3)
                .build(name));
    }

    public static int count() {
        return ENTITIES.getEntries().size();
    }
}
