package com.ucv.common.weapons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WeaponPropertiesTest {
    @Test
    void builderRejectsInvalidId() {
        assertThrows(IllegalArgumentException.class, () ->
                WeaponProperties.builder("Pistol").type(WeaponType.PISTOL).damage(7).magazineSize(15).build());
    }

    @Test
    void assaultRifleDefaultsIncludeFullAndSemi() {
        WeaponProperties properties = sampleRifle().build();
        assertTrue(properties.fireModes().contains(FireMode.FULL_AUTO));
        assertTrue(properties.fireModes().contains(FireMode.SEMI_AUTO));
        assertEquals(FireMode.FULL_AUTO, properties.defaultFireMode());
        assertEquals(AmmoType.RIFLE_AMMO, properties.ammoType());
    }

    @Test
    void rpmConvertsToTicks() {
        assertEquals(2, WeaponProperties.ticksFromRpm(600));
        assertEquals(8, WeaponProperties.builder("probe")
                .type(WeaponType.PISTOL)
                .damage(7)
                .magazineSize(15)
                .roundsPerMinute(150)
                .build()
                .fireRateTicks());
    }

    @Test
    void fireModeCyclesThroughSupportedOnly() {
        WeaponProperties properties = sampleRifle().build();
        FireMode next = properties.defaultFireMode().next(properties.fireModes());
        assertEquals(FireMode.SEMI_AUTO, next);
        assertEquals(FireMode.FULL_AUTO, next.next(properties.fireModes()));
    }

    @Test
    void magazineModifierIncreasesCapacity() {
        WeaponProperties extended = sampleRifle().build()
                .with(new WeaponPropertyModifiers().magazineDelta(15).recoilMultiplier(0.85f));
        assertEquals(45, extended.magazineSize());
        assertTrue(extended.recoil() < 1.1f);
    }

    @Test
    void stateBlocksFireDuringCooldownAndReloads() {
        WeaponProperties properties = sampleRifle().build();
        WeaponState state = WeaponState.full(properties);
        assertTrue(state.consumeShot(properties));
        assertFalse(state.canFire(properties));
        WeaponState empty = WeaponState.empty(properties);
        assertTrue(empty.startReload(properties));
        for (int i = 0; i < properties.reloadTimeTicks(); i++) {
            empty.tick(properties);
        }
        assertEquals(30, empty.ammoInMagazine());
        assertFalse(empty.isReloading());
    }

    @Test
    void frameworkSelfCheckPasses() {
        WeaponFramework.verify();
    }

    private static WeaponProperties.Builder sampleRifle() {
        return WeaponProperties.builder("framework_rifle")
                .type(WeaponType.ASSAULT_RIFLE)
                .damage(8)
                .magazineSize(30)
                .reloadTimeTicks(40)
                .range(72)
                .recoil(1.1f)
                .spread(0.8f)
                .ammoType(AmmoType.RIFLE_AMMO);
    }
}
