package net.capspock.endupdate.datagen;

import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {

    protected ModBlockLootTableProvider(HolderLookup.Provider provider) {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), provider);
    }

    @Override
    protected void generate() {
        dropSelf(ModBlocks.ENDERSTEEL_BLOCK.get());
        dropSelf(ModBlocks.AURORA_LAMP.get());
        dropSelf(ModBlocks.ENDER_SLIME_BLOCK.get());

        dropWhenSilkTouch(ModBlocks.MAGIC_BLOCK.get());

        dropSelf(ModBlocks.ABYSSAL_MOSS.get());
        dropSelf(ModBlocks.ABYSSAL_STAIRS.get());
        dropSelf(ModBlocks.ABYSSAL_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ABYSSAL_BUTTON.get());
        dropSelf(ModBlocks.ABYSSAL_FENCE.get());
        dropSelf(ModBlocks.ABYSSAL_FENCE_GATE.get());
        dropSelf(ModBlocks.ABYSSAL_TRAPDOOR.get());

        this.add(ModBlocks.ABYSSAL_SLAB.get(),
                block -> createSlabItemTable(ModBlocks.ABYSSAL_SLAB.get()));
        this.add(ModBlocks.ABYSSAL_DOOR.get(),
                block -> createDoorTable(ModBlocks.ABYSSAL_DOOR.get()));

        this.add(ModBlocks.ENDERSTEEL_ORE.get(),
                block -> createMultipleDrops(ModBlocks.ENDERSTEEL_ORE.get(), ModItems.RAW_ENDERSTEEL.get(), 1, 1));

        this.dropSelf(ModBlocks.ABYSSAL_LOG.get());
        this.dropSelf(ModBlocks.ABYSSAL_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ABYSSAL_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ABYSSAL_WOOD.get());
        this.dropSelf(ModBlocks.ABYSSAL_SAPLING.get());
        this.dropSelf(ModBlocks.ABYSSAL_PLANKS.get());

        this.dropSelf(ModBlocks.VOID_INFUSER.get());

        this.add(ModBlocks.ABYSSAL_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ABYSSAL_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    protected LootTable.Builder createMultipleDrops(Block pBlock, Item item, float minDrops, float maxDrops) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        return this.createSilkTouchDispatchTable(
                pBlock, this.applyExplosionDecay(
                        pBlock, LootItem.lootTableItem(item)
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}