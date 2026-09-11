package com.ucv.common.weapons;

/**
 * Additive/multiplicative stat changes from attachments. Unused in Phase 3
 * beyond {@link #apply(WeaponProperties)} so later SCOPE / MAG / GRIP work
 * does not need a new properties type.
 */
public final class WeaponPropertyModifiers {
    private float damageMultiplier = 1.0f;
    private float recoilMultiplier = 1.0f;
    private float spreadMultiplier = 1.0f;
    private float rangeMultiplier = 1.0f;
    private int magazineDelta;
    private float adsSpeedMultiplier = 1.0f;

    public WeaponPropertyModifiers damageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
        return this;
    }

    public WeaponPropertyModifiers recoilMultiplier(float recoilMultiplier) {
        this.recoilMultiplier = recoilMultiplier;
        return this;
    }

    public WeaponPropertyModifiers spreadMultiplier(float spreadMultiplier) {
        this.spreadMultiplier = spreadMultiplier;
        return this;
    }

    public WeaponPropertyModifiers rangeMultiplier(float rangeMultiplier) {
        this.rangeMultiplier = rangeMultiplier;
        return this;
    }

    public WeaponPropertyModifiers magazineDelta(int magazineDelta) {
        this.magazineDelta = magazineDelta;
        return this;
    }

    public WeaponPropertyModifiers adsSpeedMultiplier(float adsSpeedMultiplier) {
        this.adsSpeedMultiplier = adsSpeedMultiplier;
        return this;
    }

    public WeaponProperties apply(WeaponProperties base) {
        int magazine = Math.max(1, base.magazineSize() + magazineDelta);
        float adsSensitivity = clampUnit(base.adsSensitivityMultiplier() * adsSpeedMultiplier);
        return base.toBuilder()
                .damage(Math.max(0.1f, base.damage() * damageMultiplier))
                .recoil(Math.max(0.0f, base.recoil() * recoilMultiplier))
                .spread(Math.max(0.0f, base.spread() * spreadMultiplier))
                .range(Math.max(1.0f, base.range() * rangeMultiplier))
                .magazineSize(magazine)
                .adsSensitivityMultiplier(adsSensitivity)
                .build();
    }

    private static float clampUnit(float value) {
        if (value < 0.05f) {
            return 0.05f;
        }
        return Math.min(1.0f, value);
    }
}
