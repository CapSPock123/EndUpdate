package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimMaterials;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.LinkedHashMap;

public class ModItemModelProvider extends ItemModelProvider {
    private static LinkedHashMap<ResourceKey<TrimMaterial>, Float> trimMaterials = new LinkedHashMap<>();
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
        basicItem(ModItems.ENDERSTEEL.get());
        basicItem(ModItems.ENDERSTEEL_INGOT.get());
        basicItem(ModItems.ENDERSTEEL_NUGGET.get());
        basicItem(ModItems.RAW_ENDERSTEEL.get());
        basicItem(ModItems.SHULKER_PLATE.get());
        basicItem(ModItems.ENDER_SLIMEBALL.get());
        basicItem(ModItems.AURORA_ASHES.get());
        basicItem(ModItems.AURORA_POWDER.get());
        basicItem(ModItems.SHULKER_SMITHING_TEMPLATE.get());

        handheldItem(ModItems.ENDERSTEEL_SWORD);
        handheldItem(ModItems.ENDERSTEEL_PICKAXE);
        handheldItem(ModItems.ENDERSTEEL_SHOVEL);
        handheldItem(ModItems.ENDERSTEEL_AXE);
        handheldItem(ModItems.ENDERSTEEL_HOE);
        handheldItem(ModItems.ENDERSTEEL_HAMMER);
        handheldItem(ModItems.IRON_HAMMER);
        handheldItem(ModItems.DIAMOND_HAMMER);
        handheldItem(ModItems.NETHERITE_HAMMER);

        trimmedArmorItem(ModItems.SHULKER_HELMET);
        trimmedArmorItem(ModItems.SHULKER_CHESTPLATE);
        trimmedArmorItem(ModItems.SHULKER_LEGGINGS);
        trimmedArmorItem(ModItems.SHULKER_BOOTS);


        trimmedArmorItem(ModItems.SHULKER_PLATED_NETHERITE_HELMET);
        trimmedArmorItem(ModItems.SHULKER_PLATED_NETHERITE_CHESTPLATE);
        trimmedArmorItem(ModItems.SHULKER_PLATED_NETHERITE_LEGGINGS);
        trimmedArmorItem(ModItems.SHULKER_PLATED_NETHERITE_BOOTS);

        armorItem(ModItems.ENDER_SLIME_BOOTS);

        saplingItem(ModBlocks.ABYSSAL_SAPLING);

        buttonItem(ModBlocks.ABYSSAL_BUTTON, ModBlocks.ABYSSAL_PLANKS);
        fenceItem(ModBlocks.ABYSSAL_FENCE, ModBlocks.ABYSSAL_PLANKS);

        simpleBlockItem(ModBlocks.ABYSSAL_DOOR);

        withExistingParent(ModItems.ENDER_SLIME_SPAWN_EGG.getId().getPath(), mcLoc("item/template_spawn_egg"));
    }

    public void buttonItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void fenceItem(RegistryObject<? extends Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID,
                        "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<? extends Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID,"block/" + item.getId().getPath()));
    }

    // Shoutout to El_Redstoniano for making this
    private void trimmedArmorItem(RegistryObject<Item> itemRegistryObject) {
        final String MOD_ID = EndUpdate.MOD_ID;

        if(itemRegistryObject.get() instanceof ArmorItem armorItem) {
            trimMaterials.forEach((trimMaterial, value) -> {
                float trimValue = value;

                String armorType = switch (armorItem.getEquipmentSlot()) {
                    case HEAD -> "helmet";
                    case CHEST -> "chestplate";
                    case LEGS -> "leggings";
                    case FEET -> "boots";
                    default -> "";
                };

                String armorItemPath = armorItem.toString();
                String trimPath = "trims/items/" + armorType + "_trim_" + trimMaterial.location().getPath();
                String currentTrimName = armorItemPath + "_" + trimMaterial.location().getPath() + "_trim";
                ResourceLocation armorItemResLoc = ResourceLocation.parse(armorItemPath);
                ResourceLocation trimResLoc = ResourceLocation.parse(trimPath); // minecraft namespace
                ResourceLocation trimNameResLoc = ResourceLocation.parse(currentTrimName);

                /* This is used for making the ExistingFileHelper acknowledge that this texture exist, so this will
                avoid an IllegalArgumentException
                */

                existingFileHelper.trackGenerated(trimResLoc, PackType.CLIENT_RESOURCES, ".png", "textures");

                // Trimmed armorItem files
                getBuilder(currentTrimName)
                        .parent(new ModelFile.UncheckedModelFile("item/generated"))
                        .texture("layer0", armorItemResLoc.getNamespace() + ":item/" + armorItemResLoc.getPath())
                        .texture("layer1", trimResLoc);

                // Non-trimmed armorItem file (normal variant)
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .override()
                        .model(new ModelFile.UncheckedModelFile(trimNameResLoc.getNamespace()  + ":item/" + trimNameResLoc.getPath()))
                        .predicate(mcLoc("trim_type"), trimValue).end()
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
            });
        }
    }

    private void armorItem(RegistryObject<Item> itemRegistryObject) {
        if(itemRegistryObject.get() instanceof ArmorItem) {
                this.withExistingParent(itemRegistryObject.getId().getPath(),
                                mcLoc("item/generated"))
                        .texture("layer0",
                                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID,
                                        "item/" + itemRegistryObject.getId().getPath()));
        }
    }


    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
        ResourceLocation.parse("item/handheld")).texture("layer0",
        ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "item/" + item.getId().getPath()));
    }
}
