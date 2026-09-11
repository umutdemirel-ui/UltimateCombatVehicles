package com.ucv.common.weapons;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Immutable, data-driven combat stats for one weapon id.
 * Gameplay code should read these instead of hard-coded numbers.
 */
public final class WeaponProperties {
    private static final Pattern ID = Pattern.compile("[a-z][a-z0-9_]*");

    private final String id;
    private final WeaponType type;
    private final float damage;
    private final int fireRateTicks;
    private final int magazineSize;
    private final int reloadTimeTicks;
    private final float range;
    private final float recoil;
    private final float spread;
    private final float projectileSpeed;
    private final float criticalMultiplier;
    private final float headshotMultiplier;
    private final int durability;
    private final AmmoType ammoType;
    private final FireMode defaultFireMode;
    private final Set<FireMode> fireModes;
    private final int burstCount;
    private final float adsFovMultiplier;
    private final float adsSensitivityMultiplier;
    private final Set<AttachmentSlot> attachmentSlots;

    private WeaponProperties(Builder builder) {
        this.id = builder.id;
        this.type = builder.type;
        this.damage = builder.damage;
        this.fireRateTicks = builder.fireRateTicks;
        this.magazineSize = builder.magazineSize;
        this.reloadTimeTicks = builder.reloadTimeTicks;
        this.range = builder.range;
        this.recoil = builder.recoil;
        this.spread = builder.spread;
        this.projectileSpeed = builder.projectileSpeed;
        this.criticalMultiplier = builder.criticalMultiplier;
        this.headshotMultiplier = builder.headshotMultiplier;
        this.durability = builder.durability;
        this.ammoType = builder.ammoType;
        this.defaultFireMode = builder.defaultFireMode;
        this.fireModes = Collections.unmodifiableSet(EnumSet.copyOf(builder.fireModes));
        this.burstCount = builder.burstCount;
        this.adsFovMultiplier = builder.adsFovMultiplier;
        this.adsSensitivityMultiplier = builder.adsSensitivityMultiplier;
        this.attachmentSlots = builder.attachmentSlots.isEmpty()
                ? Set.of()
                : Collections.unmodifiableSet(EnumSet.copyOf(builder.attachmentSlots));
    }

    public static Builder builder(String id) {
        return new Builder(id);
    }

    public String id() {
        return id;
    }

    public WeaponType type() {
        return type;
    }

    public float damage() {
        return damage;
    }

    public int fireRateTicks() {
        return fireRateTicks;
    }

    public int magazineSize() {
        return magazineSize;
    }

    public int reloadTimeTicks() {
        return reloadTimeTicks;
    }

    public float range() {
        return range;
    }

    public float recoil() {
        return recoil;
    }

    public float spread() {
        return spread;
    }

    public float projectileSpeed() {
        return projectileSpeed;
    }

    public float criticalMultiplier() {
        return criticalMultiplier;
    }

    public float headshotMultiplier() {
        return headshotMultiplier;
    }

    public int durability() {
        return durability;
    }

    public AmmoType ammoType() {
        return ammoType;
    }

    public FireMode defaultFireMode() {
        return defaultFireMode;
    }

    public Set<FireMode> fireModes() {
        return fireModes;
    }

    public int burstCount() {
        return burstCount;
    }

    public float adsFovMultiplier() {
        return adsFovMultiplier;
    }

    public float adsSensitivityMultiplier() {
        return adsSensitivityMultiplier;
    }

    public Set<AttachmentSlot> attachmentSlots() {
        return attachmentSlots;
    }

    public String translationKey() {
        return "item.ucv." + id;
    }

    public Builder toBuilder() {
        Builder builder = builder(id)
                .type(type)
                .damage(damage)
                .fireRateTicks(fireRateTicks)
                .magazineSize(magazineSize)
                .reloadTimeTicks(reloadTimeTicks)
                .range(range)
                .recoil(recoil)
                .spread(spread)
                .projectileSpeed(projectileSpeed)
                .criticalMultiplier(criticalMultiplier)
                .headshotMultiplier(headshotMultiplier)
                .durability(durability)
                .ammoType(ammoType)
                .fireModes(fireModes)
                .defaultFireMode(defaultFireMode)
                .burstCount(burstCount)
                .adsFovMultiplier(adsFovMultiplier)
                .adsSensitivityMultiplier(adsSensitivityMultiplier);
        for (AttachmentSlot slot : attachmentSlots) {
            builder.allowAttachment(slot);
        }
        return builder;
    }

    public WeaponProperties with(WeaponPropertyModifiers modifiers) {
        return modifiers.apply(this);
    }

    /**
     * Minecraft runs 20 ticks/sec. {@code rpm} rounds per minute → delay between shots.
     */
    public static int ticksFromRpm(int rpm) {
        if (rpm <= 0) {
            throw new IllegalArgumentException("rpm must be positive");
        }
        return Math.max(1, Math.round(1200f / rpm));
    }

    public static final class Builder {
        private final String id;
        private WeaponType type = WeaponType.PISTOL;
        private float damage = 1.0f;
        private int fireRateTicks = 8;
        private int magazineSize = 1;
        private int reloadTimeTicks = 40;
        private float range = 32.0f;
        private float recoil = 1.0f;
        private float spread = 1.0f;
        private float projectileSpeed = 4.0f;
        private float criticalMultiplier = 1.5f;
        private float headshotMultiplier = 2.0f;
        private int durability = 0;
        private AmmoType ammoType = AmmoType.PISTOL_AMMO;
        private FireMode defaultFireMode;
        private EnumSet<FireMode> fireModes = EnumSet.noneOf(FireMode.class);
        private int burstCount = 3;
        private float adsFovMultiplier = 0.85f;
        private float adsSensitivityMultiplier = 0.7f;
        private final EnumSet<AttachmentSlot> attachmentSlots = EnumSet.noneOf(AttachmentSlot.class);

        private Builder(String id) {
            this.id = Objects.requireNonNull(id, "id");
        }

        public Builder type(WeaponType type) {
            this.type = Objects.requireNonNull(type);
            return this;
        }

        public Builder damage(float damage) {
            this.damage = damage;
            return this;
        }

        public Builder fireRateTicks(int fireRateTicks) {
            this.fireRateTicks = fireRateTicks;
            return this;
        }

        public Builder roundsPerMinute(int rpm) {
            return fireRateTicks(ticksFromRpm(rpm));
        }

        public Builder magazineSize(int magazineSize) {
            this.magazineSize = magazineSize;
            return this;
        }

        public Builder reloadTimeTicks(int reloadTimeTicks) {
            this.reloadTimeTicks = reloadTimeTicks;
            return this;
        }

        public Builder range(float range) {
            this.range = range;
            return this;
        }

        public Builder recoil(float recoil) {
            this.recoil = recoil;
            return this;
        }

        public Builder spread(float spread) {
            this.spread = spread;
            return this;
        }

        public Builder projectileSpeed(float projectileSpeed) {
            this.projectileSpeed = projectileSpeed;
            return this;
        }

        public Builder criticalMultiplier(float criticalMultiplier) {
            this.criticalMultiplier = criticalMultiplier;
            return this;
        }

        public Builder headshotMultiplier(float headshotMultiplier) {
            this.headshotMultiplier = headshotMultiplier;
            return this;
        }

        public Builder durability(int durability) {
            this.durability = durability;
            return this;
        }

        public Builder ammoType(AmmoType ammoType) {
            this.ammoType = Objects.requireNonNull(ammoType);
            return this;
        }

        public Builder fireModes(Set<FireMode> fireModes) {
            this.fireModes = EnumSet.noneOf(FireMode.class);
            this.fireModes.addAll(fireModes);
            return this;
        }

        public Builder fireMode(FireMode fireMode) {
            this.fireModes.add(Objects.requireNonNull(fireMode));
            return this;
        }

        public Builder defaultFireMode(FireMode defaultFireMode) {
            this.defaultFireMode = Objects.requireNonNull(defaultFireMode);
            return this;
        }

        public Builder burstCount(int burstCount) {
            this.burstCount = burstCount;
            return this;
        }

        public Builder adsFovMultiplier(float adsFovMultiplier) {
            this.adsFovMultiplier = adsFovMultiplier;
            return this;
        }

        public Builder adsSensitivityMultiplier(float adsSensitivityMultiplier) {
            this.adsSensitivityMultiplier = adsSensitivityMultiplier;
            return this;
        }

        public Builder allowAttachment(AttachmentSlot slot) {
            this.attachmentSlots.add(Objects.requireNonNull(slot));
            return this;
        }

        public WeaponProperties build() {
            if (!ID.matcher(id).matches()) {
                throw new IllegalArgumentException("Weapon id must be [a-z][a-z0-9_]*: " + id);
            }
            if (type == null) {
                throw new IllegalArgumentException("type is required");
            }
            if (fireModes.isEmpty()) {
                fireModes.addAll(type.defaultFireModes());
            }
            if (defaultFireMode == null) {
                defaultFireMode = fireModes.contains(type.defaultFireMode())
                        ? type.defaultFireMode()
                        : fireModes.iterator().next();
            }
            if (!fireModes.contains(defaultFireMode)) {
                throw new IllegalArgumentException("defaultFireMode is not in fireModes: " + defaultFireMode);
            }
            if (damage <= 0) {
                throw new IllegalArgumentException("damage must be > 0");
            }
            if (fireRateTicks < 1) {
                throw new IllegalArgumentException("fireRateTicks must be >= 1");
            }
            if (magazineSize < 1) {
                throw new IllegalArgumentException("magazineSize must be >= 1");
            }
            if (reloadTimeTicks < 1) {
                throw new IllegalArgumentException("reloadTimeTicks must be >= 1");
            }
            if (range <= 0) {
                throw new IllegalArgumentException("range must be > 0");
            }
            if (recoil < 0 || spread < 0) {
                throw new IllegalArgumentException("recoil and spread must be >= 0");
            }
            if (projectileSpeed <= 0) {
                throw new IllegalArgumentException("projectileSpeed must be > 0");
            }
            if (criticalMultiplier < 1 || headshotMultiplier < 1) {
                throw new IllegalArgumentException("multipliers must be >= 1");
            }
            if (durability < 0) {
                throw new IllegalArgumentException("durability must be >= 0");
            }
            if (burstCount < 1) {
                throw new IllegalArgumentException("burstCount must be >= 1");
            }
            if (adsFovMultiplier <= 0 || adsFovMultiplier > 1) {
                throw new IllegalArgumentException("adsFovMultiplier must be in (0, 1]");
            }
            if (adsSensitivityMultiplier <= 0 || adsSensitivityMultiplier > 1) {
                throw new IllegalArgumentException("adsSensitivityMultiplier must be in (0, 1]");
            }
            Objects.requireNonNull(ammoType, "ammoType");
            return new WeaponProperties(this);
        }
    }
}
