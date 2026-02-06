package net.capspock.endupdate.datagen;

import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider {
    protected ModBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.add(ModBlocks.ENDERSTEEL_ORE.get(),
                block -> createMutipleDrops(ModBlocks.ENDERSTEEL_ORE.get(), ModItems.RAW_ENDERSTEEL.get(), 1, 1));
        this.dropSelf(ModBlocks.ENDERSTEEL_BLOCK.get());
        this.dropSelf(ModBlocks.RAW_ENDERSTEEL_BLOCK.get());
        this.dropSelf(ModBlocks.ENDER_SLIME_BLOCK.get());
        this.dropSelf(ModBlocks.ENDER_BOG.get());
        this.dropWhenSilkTouch(ModBlocks.ENDER_SLIME_GEYSER.get());
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    protected LootTable.Builder createMutipleDrops(Block pBlock, Item pItem, float minDrops, float maxDrops) {
        return createSilkTouchDispatchTable(pBlock, this.applyExplosionDecay(pBlock, LootItem.lootTableItem(pItem)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(minDrops, maxDrops)))
                .apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))));
    }
}
