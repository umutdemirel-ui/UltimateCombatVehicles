package com.ucv.registry;

import com.ucv.UCV;
import com.ucv.common.item.UCVItemCategory;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

/**
 * Central block deferred register. Block items are created automatically.
 */
public final class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, UCV.MOD_ID);

    private ModBlocks() {
    }

    public static void register(IEventBus modBus) {
        BLOCKS.register(modBus);
    }

    public static <T extends Block> RegistryObject<T> register(String name, UCVItemCategory category, Supplier<T> factory) {
        RegistryObject<T> block = BLOCKS.register(name, factory);
        ModItems.register(name, category, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static int count() {
        return BLOCKS.getEntries().size();
    }
}
