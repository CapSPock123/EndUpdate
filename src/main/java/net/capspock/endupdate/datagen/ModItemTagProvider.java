package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider {
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.BEACON_PAYMENT_ITEMS)
                .add(ModItems.ENDERSTEEL_INGOT.get());
        this.tag(ItemTags.SWORDS)
                .add(ModItems.ENDERSTEEL_PICKAXE.get());
        this.tag(ItemTags.PICKAXES)
                .add(ModItems.ENDERSTEEL_HAMMER.get(),
                        ModItems.ENDERSTEEL_PICKAXE.get());
        this.tag(ItemTags.SHOVELS)
                .add(ModItems.ENDERSTEEL_PICKAXE.get());
        this.tag(ItemTags.AXES)
                .add(ModItems.ENDERSTEEL_PICKAXE.get());
        this.tag(ItemTags.HOES)
                .add(ModItems.ENDERSTEEL_PICKAXE.get());
        this.tag(ItemTags.TRIMMABLE_ARMOR)
                .add(ModItems.ENDERSTEEL_HELMET.get(),
                        ModItems.ENDERSTEEL_CHESTPLATE.get(),
                        ModItems.ENDERSTEEL_LEGGINGS.get(),
                        ModItems.ENDERSTEEL_BOOTS.get())
                .add(ModItems.DIAMOND_ELYTRA_CHESTPLATE.get(),
                        ModItems.NETHERITE_ELYTRA_CHESTPLATE.get(),
                        ModItems.ENDERSTEEL_ELYTRA_CHESTPLATE.get());
        this.tag(ItemTags.TRIM_MATERIALS)
                .add(ModItems.ENDERSTEEL_INGOT.get());
    }
}
