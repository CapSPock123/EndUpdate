package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.item.custom.ElytraChestplateItem;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static final LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
    static {
        trimMaterials.put(TrimMaterials.QUARTZ, 0.1F);
        trimMaterials.put(TrimMaterials.IRON, 0.2F);
        trimMaterials.put(TrimMaterials.NETHERITE, 0.3F);
        trimMaterials.put(TrimMaterials.REDSTONE, 0.4F);
        trimMaterials.put(TrimMaterials.COPPER, 0.5F);
        trimMaterials.put(TrimMaterials.GOLD, 0.6F);
        trimMaterials.put(TrimMaterials.EMERALD, 0.7F);
        trimMaterials.put(TrimMaterials.DIAMOND, 0.8F);
        trimMaterials.put(TrimMaterials.LAPIS, 0.9F);
        trimMaterials.put(TrimMaterials.AMETHYST, 1.0F);
    }

    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, EndUpdate.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        simpleItem(ModItems.ENDERSTEEL_INGOT);
        simpleItem(ModItems.RAW_ENDERSTEEL);
        simpleItem(ModItems.ENDERSTEEL_SHARD);
        simpleItem(ModItems.ENDERSTEEL_NUGGET);
        simpleItem(ModItems.ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE);
        simpleItem(ModItems.AURORA_POWDER);

        handheldItem(ModItems.ENDERSTEEL_SWORD);
        handheldItem(ModItems.ENDERSTEEL_PICKAXE);
        handheldItem(ModItems.ENDERSTEEL_SHOVEL);
        handheldItem(ModItems.ENDERSTEEL_AXE);
        handheldItem(ModItems.ENDERSTEEL_HOE);
        handheldItem(ModItems.ENDERSTEEL_HAMMER);

        handheldItem(ModItems.GOLD_HAMMER);
        handheldItem(ModItems.IRON_HAMMER);
        handheldItem(ModItems.DIAMOND_HAMMER);
        handheldItem(ModItems.NETHERITE_HAMMER);

        trimmedArmorItem(ModItems.ENDERSTEEL_HELMET);
        trimmedArmorItem(ModItems.ENDERSTEEL_CHESTPLATE);
        trimmedArmorItem(ModItems.ENDERSTEEL_LEGGINGS);
        trimmedArmorItem(ModItems.ENDERSTEEL_BOOTS);

        trimmedElytraChestplateItem(ModItems.DIAMOND_ELYTRA_CHESTPLATE);
        trimmedElytraChestplateItem(ModItems.NETHERITE_ELYTRA_CHESTPLATE);
        trimmedElytraChestplateItem(ModItems.ENDERSTEEL_ELYTRA_CHESTPLATE);
    }

    /*Methods from Forge TutorialMod 1.21.X by Kaupenjoe
    Distributed under the MIT License*/
    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.withDefaultNamespace("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "item/" + item.getId().getPath()));
    }

    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = "item/" + armorItem;
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.withDefaultNamespace(trimPath);
                ResourceLocation trimNameResLoc = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, currentTrimName);

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private void trimmedElytraChestplateItem(RegistryObject<Item> itemRegistryObject) {
        if(itemRegistryObject.get() instanceof ElytraChestplateItem elytraChestplateItem) {
            String brokenArmorItemPath = "item/broken_" + elytraChestplateItem;

            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String armorItemPath = "item/" + elytraChestplateItem;
                String trimPath = "trims/items/chestplate" + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                String currentTrimName1 = brokenArmorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, armorItemPath);
                ResourceLocation armorItemResLoc1 = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, brokenArmorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.withDefaultNamespace(trimPath);
                ResourceLocation trimNameResLoc = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, currentTrimName);

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc)
                        .texture("layer1", trimResLoc);

                getBuilder(currentTrimName1)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc1)
                        .texture("layer1", trimResLoc);

                this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0", ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, armorItemPath));
            });

            getBuilder(brokenArmorItemPath)
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, brokenArmorItemPath));

            this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated"))
                    .override()
                    .predicate(ResourceLocation.withDefaultNamespace("broken"), 1)
                    .model(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, brokenArmorItemPath)));

            trimMaterials.entrySet().forEach(entry -> {

                ResourceKey<TrimMaterial> trimMaterial = entry.getKey();
                float trimValue = entry.getValue();

                String trimPath = "trims/items/chestplate" + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = brokenArmorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation trimResLoc = ResourceLocation.withDefaultNamespace(trimPath);
                ResourceLocation trimNameResLoc = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, currentTrimName);

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                this.withExistingParent(itemRegistryObject.getId().getPath(), mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc))
                        .predicate(mcLoc("trim_type"), trimValue)
                        .predicate(ResourceLocation.withDefaultNamespace("broken"), 1).end();
            });
        }
    }
}