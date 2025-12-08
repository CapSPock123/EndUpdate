package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.ENDERSTEEL_INGOT);
        simpleItem(ModItems.RAW_ENDERSTEEL);
        simpleItem(ModItems.ENDERSTEEL_SHARD);
        simpleItem(ModItems.ENDERSTEEL_NUGGET);

        handheldItem(ModItems.ENDERSTEEL_SWORD);
        handheldItem(ModItems.ENDERSTEEL_PICKAXE);
        handheldItem(ModItems.ENDERSTEEL_SHOVEL);
        handheldItem(ModItems.ENDERSTEEL_AXE);
        handheldItem(ModItems.ENDERSTEEL_HOE);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(EndUpdate.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "item/" + item.getId().getPath()));
    }
}
