package com.ucv.registry;

import com.ucv.UCV;
import com.ucv.common.item.UCVItemCategory;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Central item deferred register. New items should be one {@link #register} call.
 */
public final class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, UCV.MOD_ID);

    private static final Map<RegistryObject<Item>, UCVItemCategory> CATEGORIES = new LinkedHashMap<>();

    private ModItems() {
    }

    public static void register(IEventBus modBus) {
        ITEMS.register(modBus);
    }

    public static RegistryObject<Item> register(String name, UCVItemCategory category, Supplier<Item> factory) {
        RegistryObject<Item> item = ITEMS.register(name, factory);
        CATEGORIES.put(item, category);
        return item;
    }

    public static RegistryObject<Item> registerSimple(String name, UCVItemCategory category) {
        return register(name, category, () -> new Item(new Item.Properties()));
    }

    public static Collection<RegistryObject<Item>> entries(UCVItemCategory category) {
        List<RegistryObject<Item>> matches = new ArrayList<>();
        for (Map.Entry<RegistryObject<Item>, UCVItemCategory> entry : CATEGORIES.entrySet()) {
            if (entry.getValue() == category) {
                matches.add(entry.getKey());
            }
        }
        return Collections.unmodifiableList(matches);
    }

    public static Collection<RegistryObject<Item>> entries() {
        return Collections.unmodifiableCollection(ITEMS.getEntries());
    }

    public static UCVItemCategory categoryOf(RegistryObject<Item> item) {
        return CATEGORIES.getOrDefault(item, UCVItemCategory.MISC);
    }

    public static int count() {
        return ITEMS.getEntries().size();
    }
}
