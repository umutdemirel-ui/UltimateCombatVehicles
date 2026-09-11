package com.ucv.common.weapons;

import java.util.Locale;

/**
 * Magazine/ammo kinds. Item bindings are registered later (Phase 4).
 */
public enum AmmoType {
    PISTOL_AMMO("pistol_ammo", 64),
    RIFLE_AMMO("rifle_ammo", 64),
    SHOTGUN_SHELL("shotgun_shell", 64),
    SNIPER_AMMO("sniper_ammo", 64);

    private final String itemId;
    private final int maxStack;

    AmmoType(String itemId, int maxStack) {
        this.itemId = itemId;
        this.maxStack = maxStack;
    }

    public String itemId() {
        return itemId;
    }

    public int maxStack() {
        return maxStack;
    }

    public String translationKey() {
        return "ucv.weapon.ammo_type." + itemId;
    }

    public static AmmoType fromId(String id) {
        String normalized = id.trim().toLowerCase(Locale.ROOT);
        for (AmmoType type : values()) {
            if (type.itemId.equals(normalized) || type.name().equalsIgnoreCase(normalized)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown ammo type: " + id);
    }
}
