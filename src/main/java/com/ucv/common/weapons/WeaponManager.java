package com.ucv.common.weapons;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * In-code weapon definitions plus optional datapack overrides.
 */
public final class WeaponManager {
    private static final Map<String, WeaponProperties> CODE = new LinkedHashMap<>();
    private static final Map<String, WeaponProperties> DATAPACK = new LinkedHashMap<>();

    private WeaponManager() {
    }

    public static synchronized void register(WeaponProperties properties) {
        CODE.put(properties.id(), properties);
    }

    public static synchronized void replaceDataPackDefinitions(Map<String, WeaponProperties> loaded) {
        DATAPACK.clear();
        DATAPACK.putAll(loaded);
    }

    public static synchronized Optional<WeaponProperties> get(String id) {
        WeaponProperties pack = DATAPACK.get(id);
        if (pack != null) {
            return Optional.of(pack);
        }
        return Optional.ofNullable(CODE.get(id));
    }

    public static synchronized Collection<WeaponProperties> all() {
        Map<String, WeaponProperties> merged = new LinkedHashMap<>(CODE);
        merged.putAll(DATAPACK);
        return Collections.unmodifiableCollection(merged.values());
    }

    public static synchronized int definitionCount() {
        Set<String> ids = new LinkedHashSet<>(CODE.keySet());
        ids.addAll(DATAPACK.keySet());
        return ids.size();
    }

    public static synchronized int codeDefinitionCount() {
        return CODE.size();
    }

    public static synchronized int dataPackDefinitionCount() {
        return DATAPACK.size();
    }
}
