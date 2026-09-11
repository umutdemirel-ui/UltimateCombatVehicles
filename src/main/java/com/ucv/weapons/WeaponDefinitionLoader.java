package com.ucv.weapons;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.ucv.UCV;
import com.ucv.UltimateCombatVehicles;
import com.ucv.common.weapons.WeaponManager;
import com.ucv.common.weapons.WeaponProperties;
import com.ucv.common.weapons.WeaponPropertiesJson;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Loads {@code data/ucv/weapons/*.json} on datapack reload.
 */
public final class WeaponDefinitionLoader implements ResourceManagerReloadListener {
    public static final WeaponDefinitionLoader INSTANCE = new WeaponDefinitionLoader();

    private WeaponDefinitionLoader() {
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        Map<String, WeaponProperties> loaded = new LinkedHashMap<>();
        Map<ResourceLocation, Resource> resources = resourceManager.listResources(
                "weapons",
                location -> location.getNamespace().equals(UCV.MOD_ID) && location.getPath().endsWith(".json"));
        for (Map.Entry<ResourceLocation, Resource> entry : resources.entrySet()) {
            try (Reader reader = new InputStreamReader(entry.getValue().open(), StandardCharsets.UTF_8)) {
                JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
                WeaponProperties properties = WeaponPropertiesJson.read(json);
                loaded.put(properties.id(), properties);
            } catch (IOException | RuntimeException exception) {
                throw new IllegalStateException("Failed to load weapon definition " + entry.getKey(), exception);
            }
        }
        WeaponManager.replaceDataPackDefinitions(loaded);
        UltimateCombatVehicles.LOGGER.info("[UCV] Loaded {} weapon definitions from datapacks", loaded.size());
    }
}
