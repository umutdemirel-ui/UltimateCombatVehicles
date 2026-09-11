package com.ucv.common.weapons;

/**
 * Boot-time self check so a broken properties/state machine fails the server
 * instead of shipping silently.
 */
public final class WeaponFramework {
    private WeaponFramework() {
    }

    public static void verify() {
        WeaponProperties properties = WeaponProperties.builder("framework_probe")
                .type(WeaponType.ASSAULT_RIFLE)
                .damage(8.0f)
                .roundsPerMinute(600)
                .magazineSize(30)
                .reloadTimeTicks(45)
                .range(64.0f)
                .recoil(1.1f)
                .spread(0.9f)
                .projectileSpeed(6.0f)
                .ammoType(AmmoType.RIFLE_AMMO)
                .fireMode(FireMode.FULL_AUTO)
                .fireMode(FireMode.SEMI_AUTO)
                .allowAttachment(AttachmentSlot.SCOPE)
                .allowAttachment(AttachmentSlot.EXTENDED_MAGAZINE)
                .build();

        if (properties.fireRateTicks() != 2) {
            throw new IllegalStateException("600 rpm should be 2 ticks, got " + properties.fireRateTicks());
        }

        WeaponState state = WeaponState.full(properties);
        if (!state.canFire(properties) || !state.consumeShot(properties)) {
            throw new IllegalStateException("full magazine must be able to fire");
        }
        if (state.ammoInMagazine() != 29) {
            throw new IllegalStateException("ammo did not decrement");
        }
        if (state.canFire(properties)) {
            throw new IllegalStateException("cooldown should block the next shot");
        }
        for (int i = 0; i < properties.fireRateTicks(); i++) {
            state.tick(properties);
        }
        if (!state.canFire(properties)) {
            throw new IllegalStateException("cooldown should expire after fireRateTicks");
        }

        WeaponState empty = WeaponState.empty(properties);
        if (empty.canFire(properties) || !empty.startReload(properties)) {
            throw new IllegalStateException("empty magazine must reload and not fire");
        }
        for (int i = 0; i < properties.reloadTimeTicks(); i++) {
            empty.tick(properties);
        }
        if (empty.isReloading() || empty.ammoInMagazine() != 30) {
            throw new IllegalStateException("reload did not refill magazine");
        }

        WeaponProperties extended = properties.with(new WeaponPropertyModifiers().magazineDelta(10));
        if (extended.magazineSize() != 40) {
            throw new IllegalStateException("extended mag modifier failed");
        }
    }
}
