package com.ucv.common.weapons;

/**
 * In-memory magazine / cooldown / reload machine. Server gameplay owns this;
 * the client must not decide damage from it.
 */
public final class WeaponState {
    private int ammoInMagazine;
    private int cooldownTicks;
    private int reloadTicksRemaining;
    private ReloadState reloadState = ReloadState.IDLE;
    private FireMode fireMode;
    private int burstShotsRemaining;

    private WeaponState(int ammoInMagazine, FireMode fireMode) {
        this.ammoInMagazine = ammoInMagazine;
        this.fireMode = fireMode;
    }

    public static WeaponState full(WeaponProperties properties) {
        return new WeaponState(properties.magazineSize(), properties.defaultFireMode());
    }

    public static WeaponState empty(WeaponProperties properties) {
        return new WeaponState(0, properties.defaultFireMode());
    }

    public int ammoInMagazine() {
        return ammoInMagazine;
    }

    public int cooldownTicks() {
        return cooldownTicks;
    }

    public int reloadTicksRemaining() {
        return reloadTicksRemaining;
    }

    public ReloadState reloadState() {
        return reloadState;
    }

    public FireMode fireMode() {
        return fireMode;
    }

    public boolean isReloading() {
        return reloadState == ReloadState.RELOADING;
    }

    public boolean canFire(WeaponProperties properties) {
        return !isReloading()
                && cooldownTicks <= 0
                && ammoInMagazine > 0
                && properties.magazineSize() > 0;
    }

    public boolean consumeShot(WeaponProperties properties) {
        if (!canFire(properties)) {
            return false;
        }
        ammoInMagazine--;
        cooldownTicks = properties.fireRateTicks();
        if (fireMode == FireMode.BURST) {
            if (burstShotsRemaining <= 0) {
                burstShotsRemaining = properties.burstCount();
            }
            burstShotsRemaining--;
        }
        return true;
    }

    public boolean startReload(WeaponProperties properties) {
        if (isReloading() || ammoInMagazine >= properties.magazineSize()) {
            return false;
        }
        reloadState = ReloadState.RELOADING;
        reloadTicksRemaining = properties.reloadTimeTicks();
        burstShotsRemaining = 0;
        return true;
    }

    public boolean cancelReload() {
        if (!isReloading()) {
            return false;
        }
        reloadState = ReloadState.IDLE;
        reloadTicksRemaining = 0;
        return true;
    }

    public void cycleFireMode(WeaponProperties properties) {
        fireMode = fireMode.next(properties.fireModes());
    }

    public void tick(WeaponProperties properties) {
        if (cooldownTicks > 0) {
            cooldownTicks--;
        }
        if (reloadState != ReloadState.RELOADING) {
            return;
        }
        reloadTicksRemaining--;
        if (reloadTicksRemaining <= 0) {
            ammoInMagazine = properties.magazineSize();
            reloadTicksRemaining = 0;
            reloadState = ReloadState.IDLE;
        }
    }
}
