package com.ucv.common.weapons;

import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

/**
 * Weapon family. Not a specific item — instances use {@link WeaponProperties}.
 */
public enum WeaponType {
    PISTOL,
    SMG,
    ASSAULT_RIFLE,
    SHOTGUN,
    SNIPER_RIFLE;

    public String id() {
        return name().toLowerCase(Locale.ROOT);
    }

    public Set<FireMode> defaultFireModes() {
        return switch (this) {
            case PISTOL, SHOTGUN, SNIPER_RIFLE -> EnumSet.of(FireMode.SEMI_AUTO);
            case SMG -> EnumSet.of(FireMode.FULL_AUTO);
            case ASSAULT_RIFLE -> EnumSet.of(FireMode.FULL_AUTO, FireMode.SEMI_AUTO);
        };
    }

    public FireMode defaultFireMode() {
        return switch (this) {
            case PISTOL, SHOTGUN, SNIPER_RIFLE -> FireMode.SEMI_AUTO;
            case SMG, ASSAULT_RIFLE -> FireMode.FULL_AUTO;
        };
    }

    public static WeaponType fromId(String id) {
        return valueOf(id.trim().toUpperCase(Locale.ROOT));
    }
}
