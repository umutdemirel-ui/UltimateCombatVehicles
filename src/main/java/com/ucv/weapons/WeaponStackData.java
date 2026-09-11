package com.ucv.weapons;

import com.ucv.common.weapons.FireMode;
import com.ucv.common.weapons.WeaponProperties;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

/**
 * Persistent magazine / fire-mode data on a weapon ItemStack (1.21 data components).
 */
public final class WeaponStackData {
    private static final String ROOT = "ucv";
    private static final String AMMO = "ammo";
    private static final String FIRE_MODE = "fire_mode";
    private static final String INITIALIZED = "init";

    private WeaponStackData() {
    }

    public static void initialize(ItemStack stack, WeaponProperties properties) {
        CompoundTag root = copyRoot(stack);
        CompoundTag ucv = ucv(root);
        if (ucv.getBoolean(INITIALIZED)) {
            return;
        }
        ucv.putBoolean(INITIALIZED, true);
        ucv.putInt(AMMO, properties.magazineSize());
        ucv.putString(FIRE_MODE, properties.defaultFireMode().id());
        write(stack, root, ucv);
    }

    public static int getAmmo(ItemStack stack, WeaponProperties properties) {
        initialize(stack, properties);
        return ucv(copyRoot(stack)).getInt(AMMO);
    }

    public static void setAmmo(ItemStack stack, WeaponProperties properties, int ammo) {
        initialize(stack, properties);
        CompoundTag root = copyRoot(stack);
        CompoundTag ucv = ucv(root);
        int clamped = Math.max(0, Math.min(properties.magazineSize(), ammo));
        ucv.putInt(AMMO, clamped);
        write(stack, root, ucv);
    }

    public static FireMode getFireMode(ItemStack stack, WeaponProperties properties) {
        initialize(stack, properties);
        try {
            return FireMode.fromId(ucv(copyRoot(stack)).getString(FIRE_MODE));
        } catch (RuntimeException ignored) {
            return properties.defaultFireMode();
        }
    }

    public static void setFireMode(ItemStack stack, WeaponProperties properties, FireMode fireMode) {
        initialize(stack, properties);
        CompoundTag root = copyRoot(stack);
        CompoundTag ucv = ucv(root);
        FireMode resolved = properties.fireModes().contains(fireMode) ? fireMode : properties.defaultFireMode();
        ucv.putString(FIRE_MODE, resolved.id());
        write(stack, root, ucv);
    }

    private static CompoundTag copyRoot(ItemStack stack) {
        CustomData data = stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
        return data.copyTag();
    }

    private static CompoundTag ucv(CompoundTag root) {
        if (root.contains(ROOT)) {
            return root.getCompound(ROOT);
        }
        return new CompoundTag();
    }

    private static void write(ItemStack stack, CompoundTag root, CompoundTag ucv) {
        root.put(ROOT, ucv);
        stack.set(DataComponents.CUSTOM_DATA, CustomData.of(root));
    }
}
