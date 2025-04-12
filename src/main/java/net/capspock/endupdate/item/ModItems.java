package net.capspock.endupdate.item;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EndUpdate.MOD_ID);

    /*Texture adapted (changed) from Draconic Evolution by brandon3055
      Link to his mod (Art released under the CC BY-NC-SA 4.0): https://github.com/Draconic-Inc/Draconic-Evolution*/
    public static final RegistryObject<Item> ENDERSTEEL_INGOT = ITEMS.register("endersteel_ingot",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENDERSTEEL_NUGGET = ITEMS.register("endersteel_nugget",
            () -> new Item(new Item.Properties()));

    //Texture adapted from FurfSky Reborn at https://furfsky.net/
    public static final RegistryObject<Item> ENDERSTEEL = ITEMS.register("endersteel",
            () -> new Item(new Item.Properties()));

    //Texture adapted from FurfSky Reborn at https://furfsky.net/
    public static final RegistryObject<Item> RAW_ENDERSTEEL = ITEMS.register("raw_endersteel",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SHULKER_PLATE = ITEMS.register("shulker_plate",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENDER_SLIMEBALL = ITEMS.register("ender_slimeball",
            () -> new EnderSlimeballItem(new Item.Properties()));

    /*Texture from Forge TutorialMod 1.21.X by Kaupenjoe
    Distributed under the MIT License*/
    public static final RegistryObject<Item> AURORA_ASHES = ITEMS.register("aurora_ashes",
            () -> new FuelItem(new Item.Properties(), 25600) {
                @Override
                public void appendHoverText(ItemStack pStack, TooltipContext tooltipContext, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    pTooltipComponents.add(Component.translatable("tooltip.endupdate.aurora_ashes.tooltip"));
                    super.appendHoverText(pStack, tooltipContext, pTooltipComponents, pIsAdvanced);
                }
            });

    public static final RegistryObject<Item> AURORA_POWDER = ITEMS.register("aurora_powder",
            () -> new AuroraPowderItem(new Item.Properties()));


    public static final RegistryObject<Item> ENDERSTEEL_SWORD = ITEMS.register("endersteel_sword",
            () -> new SwordItem(ModToolTiers.ENDERSTEEL, new Item.Properties()
                    .attributes(SwordItem.createAttributes(ModToolTiers.ENDERSTEEL, 3, -1.9f))));

    public static final RegistryObject<Item> ENDERSTEEL_PICKAXE = ITEMS.register("endersteel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ENDERSTEEL, new Item.Properties()
                    .attributes(PickaxeItem.createAttributes(ModToolTiers.ENDERSTEEL, 1, -2.8f))));

    public static final RegistryObject<Item> ENDERSTEEL_SHOVEL = ITEMS.register("endersteel_shovel",
            () -> new ShovelItem(ModToolTiers.ENDERSTEEL, new Item.Properties()
                    .attributes(ShovelItem.createAttributes(ModToolTiers.ENDERSTEEL, 1.5f, -3.0f))));

    public static final RegistryObject<Item> ENDERSTEEL_AXE = ITEMS.register("endersteel_axe",
            () -> new AxeItem(ModToolTiers.ENDERSTEEL, new Item.Properties()
                    .attributes(AxeItem.createAttributes(ModToolTiers.ENDERSTEEL, 5, -3.0f))));

    public static final RegistryObject<Item> ENDERSTEEL_HOE = ITEMS.register("endersteel_hoe",
            () -> new HoeItem(ModToolTiers.ENDERSTEEL, new Item.Properties()
                    .attributes(HoeItem.createAttributes(ModToolTiers.ENDERSTEEL, -4, 0f))));

    public static final RegistryObject<Item> ENDERSTEEL_HAMMER = ITEMS.register("endersteel_hammer",
            () -> new HammerItem(ModToolTiers.ENDERSTEEL_BLOCK, new Item.Properties()
                    .attributes(HammerItem.createAttributes(ModToolTiers.ENDERSTEEL_BLOCK, 6, -3.2f))));

    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(ModToolTiers.DIAMOND_BLOCK, new Item.Properties()
                    .attributes(HammerItem.createAttributes(ModToolTiers.DIAMOND_BLOCK, 6, -3.2f))));

    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
            () -> new HammerItem(ModToolTiers.NETHERITE_BLOCK, new Item.Properties()
                    .attributes(HammerItem.createAttributes(ModToolTiers.NETHERITE_BLOCK, 6, -3.2f)).fireResistant()));

    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(ModToolTiers.IRON_BLOCK, new Item.Properties()
                    .attributes(HammerItem.createAttributes(ModToolTiers.IRON_BLOCK, 6, -3.2f))));

    public static final RegistryObject<Item> ENDER_BOW = ITEMS.register("ender_bow",
            () -> new BowItem(new Item.Properties().durability(1208)));


    public static final RegistryObject<Item> SHULKER_PLATED_NETHERITE_HELMET = ITEMS.register("shulker_plated_netherite_helmet",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_PLATED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(40)).fireResistant()));
    public static final RegistryObject<Item> SHULKER_PLATED_NETHERITE_CHESTPLATE = ITEMS.register("shulker_plated_netherite_chestplate",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_PLATED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(40)).fireResistant()));
    public static final RegistryObject<Item> SHULKER_PLATED_NETHERITE_LEGGINGS = ITEMS.register("shulker_plated_netherite_leggings",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_PLATED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(40)).fireResistant()));
    public static final RegistryObject<Item> SHULKER_PLATED_NETHERITE_BOOTS = ITEMS.register("shulker_plated_netherite_boots",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_PLATED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(40)).fireResistant()));
    public static final RegistryObject<Item> SHULKER_HELMET = ITEMS.register("shulker_helmet",
            () ->  new ModArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(45))));
    public static final RegistryObject<Item> SHULKER_CHESTPLATE = ITEMS.register("shulker_chestplate",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(45))));
    public static final RegistryObject<Item> SHULKER_LEGGINGS = ITEMS.register("shulker_leggings",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(45))));
    public static final RegistryObject<Item> SHULKER_BOOTS = ITEMS.register("shulker_boots",
            () ->  new ArmorItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(45))));

    public static final RegistryObject<Item> DIAMOND_ELYTRA_CHESTPLATE = ITEMS.register("diamond_elytra_chestplate",
            () -> new ElytraChestplateItem(ArmorMaterials.DIAMOND, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(66))));

    public static final RegistryObject<Item> NETHERITE_ELYTRA_CHESTPLATE = ITEMS.register("netherite_elytra_chestplate",
            () -> new ElytraChestplateItem(ArmorMaterials.NETHERITE, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(74)).fireResistant()));

    public static final RegistryObject<Item> SHULKER_PLATED_NETHERITE_ELYTRA_CHESTPLATE = ITEMS.register("shulker_plated_netherite_elytra_chestplate",
            () -> new ElytraChestplateItem(ModArmorMaterials.SHULKER_PLATED_NETHERITE_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(74)).fireResistant()));

    public static final RegistryObject<Item> SHULKER_ELYTRA_CHESTPLATE = ITEMS.register("shulker_elytra_chestplate",
            () -> new ElytraChestplateItem(ModArmorMaterials.SHULKER_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(90))));

    /*Textures adapted from Tinkers' Construct by SlimeKnights
      Distributed under the MIT License*/
    public static final RegistryObject<Item> ENDER_SLIME_BOOTS = ITEMS.register("ender_slime_boots",
            () -> new EnderSlimeArmorItem(ModArmorMaterials.ENDER_SLIME, ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(20))));

    public static final RegistryObject<Item> SHULKER_SMITHING_TEMPLATE = ITEMS.register("shulker_smithing_template",
            () -> SmithingTemplateItem.createArmorTrimTemplate(ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "shulker")));

    public static final RegistryObject<Item> ENDER_SLIME_SPAWN_EGG = ITEMS.register("ender_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ENDER_SLIME, 0x711a9c, 0x8b00dc, new Item.Properties()));

    /*Textures adapted from SupersLegend by superworldsun at https://github.com/superworldsun/SupersLegend
      Distributed under the MIT License
     */
    public static final RegistryObject<Item> SLINGSHOT = ITEMS.register("slingshot",
            () -> new SlingshotItem(new Item.Properties().durability(577)));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}