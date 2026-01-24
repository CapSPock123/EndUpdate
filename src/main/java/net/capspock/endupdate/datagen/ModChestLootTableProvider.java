package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.function.BiConsumer;

public class ModChestLootTableProvider implements LootTableSubProvider {
    @Override
    public void generate(@NotNull BiConsumer<ResourceLocation, LootTable.Builder> pOutput) {
        pOutput.accept(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "chests/end_altar"),
                LootTable.lootTable().withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0f))
                                .add(LootItem.lootTableItem(Items.ENDER_EYE).setWeight(4).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))
                                .add(LootItem.lootTableItem(ModItems.ENDERSTEEL_NUGGET.get()).setWeight(3).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                                .add(LootItem.lootTableItem(ModItems.ENDERSTEEL_SHARD.get()).setWeight(3).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f))))
                                .add(LootItem.lootTableItem(ModItems.VOID_ESSENCE.get()).setWeight(2).apply(SetItemCountFunction.setCount(ConstantValue.exactly(1.0f)))))
                        .withPool(LootPool.lootPool()
                                .setRolls(UniformGenerator.between(1.0f, 2.0f))
                                .add(LootItem.lootTableItem(Items.ENDER_PEARL).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f))))
                                .add(LootItem.lootTableItem(ModItems.ENDER_SLIMEBALL.get()).setWeight(7).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 5.0f))))
                                .add(LootItem.lootTableItem(Items.CHORUS_FRUIT).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0f, 3.0f))))
                                .add(LootItem.lootTableItem(Items.OBSIDIAN).setWeight(5).apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0f, 3.0f))))));
    }
}
