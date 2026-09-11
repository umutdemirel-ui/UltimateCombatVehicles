package com.ucv.registry;

import com.ucv.UCV;
import com.ucv.common.item.UCVItemCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

/**
 * Single creative tab: Ultimate Combat &amp; Vehicles.
 * Contents are filled from {@link ModItems} in category order.
 */
public final class ModCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, UCV.MOD_ID);

    public static final RegistryObject<CreativeModeTab> MAIN = CREATIVE_TABS.register("main", () ->
            CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.ucv"))
                    .withTabsBefore(CreativeModeTabs.SPAWN_EGGS)
                    .icon(ModCreativeTabs::icon)
                    .displayItems(ModCreativeTabs::fill)
                    .build());

    private ModCreativeTabs() {
    }

    public static void register(IEventBus modBus) {
        CREATIVE_TABS.register(modBus);
    }

    public static int count() {
        return CREATIVE_TABS.getEntries().size();
    }

    private static ItemStack icon() {
        for (RegistryObject<Item> item : ModItems.entries()) {
            return new ItemStack(item.get());
        }
        return new ItemStack(Items.COMPASS);
    }

    private static void fill(CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) {
        for (UCVItemCategory category : UCVItemCategory.values()) {
            for (RegistryObject<Item> item : ModItems.entries(category)) {
                output.accept(item.get());
            }
        }
    }
}
