package com.ucv.weapons;

import com.ucv.common.weapons.FireMode;
import com.ucv.common.weapons.WeaponProperties;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;
import java.util.Objects;

/**
 * Shared weapon item. Concrete guns are {@code new BaseWeapon(properties)}
 * registered through {@code ModItems.registerWeapon}. Shooting is Phase 5.
 */
public class BaseWeapon extends Item {
    private final WeaponProperties properties;

    public BaseWeapon(WeaponProperties properties) {
        super(itemProperties(properties));
        this.properties = Objects.requireNonNull(properties, "properties");
    }

    public WeaponProperties properties() {
        return properties;
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        WeaponStackData.initialize(stack, properties);
        int ammo = WeaponStackData.getAmmo(stack, properties);
        FireMode fireMode = WeaponStackData.getFireMode(stack, properties);
        tooltip.add(Component.translatable(
                "ucv.weapon.tooltip.ammo",
                ammo,
                properties.magazineSize()).withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.translatable(
                "ucv.weapon.tooltip.fire_mode",
                Component.translatable(fireMode.translationKey())).withStyle(ChatFormatting.DARK_GRAY));
        tooltip.add(Component.translatable(properties.ammoType().translationKey()).withStyle(ChatFormatting.DARK_GRAY));
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return true;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int magazine = properties.magazineSize();
        if (magazine <= 0) {
            return 0;
        }
        int ammo = WeaponStackData.getAmmo(stack, properties);
        return Math.round(13.0f * ammo / magazine);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        float ratio = properties.magazineSize() == 0
                ? 0.0f
                : (float) WeaponStackData.getAmmo(stack, properties) / properties.magazineSize();
        int red = Math.round(255 * (1.0f - ratio));
        int green = Math.round(255 * ratio);
        return (red << 16) | (green << 8);
    }

    private static Item.Properties itemProperties(WeaponProperties properties) {
        Item.Properties itemProperties = new Item.Properties().stacksTo(1);
        if (properties.durability() > 0) {
            itemProperties.durability(properties.durability());
        }
        return itemProperties;
    }
}
