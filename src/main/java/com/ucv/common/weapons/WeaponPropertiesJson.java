package com.ucv.common.weapons;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Shared JSON schema for {@code data/ucv/weapons/*.json}.
 */
public final class WeaponPropertiesJson {
    private WeaponPropertiesJson() {
    }

    public static WeaponProperties read(JsonObject json) {
        String id = json.get("id").getAsString();
        WeaponProperties.Builder builder = WeaponProperties.builder(id)
                .type(WeaponType.fromId(json.get("type").getAsString()))
                .damage(json.get("damage").getAsFloat())
                .magazineSize(json.get("magazineSize").getAsInt())
                .reloadTimeTicks(json.get("reloadTimeTicks").getAsInt())
                .range(json.get("range").getAsFloat())
                .ammoType(AmmoType.fromId(json.get("ammoType").getAsString()));

        if (json.has("fireRateTicks")) {
            builder.fireRateTicks(json.get("fireRateTicks").getAsInt());
        } else if (json.has("roundsPerMinute")) {
            builder.roundsPerMinute(json.get("roundsPerMinute").getAsInt());
        }

        if (json.has("recoil")) {
            builder.recoil(json.get("recoil").getAsFloat());
        }
        if (json.has("spread")) {
            builder.spread(json.get("spread").getAsFloat());
        }
        if (json.has("projectileSpeed")) {
            builder.projectileSpeed(json.get("projectileSpeed").getAsFloat());
        }
        if (json.has("criticalMultiplier")) {
            builder.criticalMultiplier(json.get("criticalMultiplier").getAsFloat());
        }
        if (json.has("headshotMultiplier")) {
            builder.headshotMultiplier(json.get("headshotMultiplier").getAsFloat());
        }
        if (json.has("durability")) {
            builder.durability(json.get("durability").getAsInt());
        }
        if (json.has("burstCount")) {
            builder.burstCount(json.get("burstCount").getAsInt());
        }
        if (json.has("adsFovMultiplier")) {
            builder.adsFovMultiplier(json.get("adsFovMultiplier").getAsFloat());
        }
        if (json.has("adsSensitivityMultiplier")) {
            builder.adsSensitivityMultiplier(json.get("adsSensitivityMultiplier").getAsFloat());
        }
        if (json.has("fireModes")) {
            JsonArray modes = json.getAsJsonArray("fireModes");
            for (JsonElement element : modes) {
                builder.fireMode(FireMode.fromId(element.getAsString()));
            }
        }
        if (json.has("defaultFireMode")) {
            builder.defaultFireMode(FireMode.fromId(json.get("defaultFireMode").getAsString()));
        }
        if (json.has("attachmentSlots")) {
            JsonArray slots = json.getAsJsonArray("attachmentSlots");
            for (JsonElement element : slots) {
                builder.allowAttachment(AttachmentSlot.valueOf(element.getAsString().trim().toUpperCase(java.util.Locale.ROOT)));
            }
        }
        return builder.build();
    }
}
