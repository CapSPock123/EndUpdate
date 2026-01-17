package net.capspock.endupdate.item;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.ModEntities;
import net.capspock.endupdate.item.custom.*;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, EndUpdate.MOD_ID);

    public static final RegistryObject<Item> ENDERSTEEL_INGOT = ITEMS.register("endersteel_ingot",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_ENDERSTEEL = ITEMS.register("raw_endersteel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_SHARD = ITEMS.register("endersteel_shard",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_NUGGET = ITEMS.register("endersteel_nugget",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENDERSTEEL_SWORD = ITEMS.register("endersteel_sword",
            () -> new EndersteelSwordItem(ModToolTiers.ENDERSTEEL, 3, -2.4f, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_sword.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_PICKAXE = ITEMS.register("endersteel_pickaxe",
            () -> new EndersteelPickaxeItem(ModToolTiers.ENDERSTEEL, 1, -2.8f, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_pickaxe.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_SHOVEL = ITEMS.register("endersteel_shovel",
            () -> new EndersteelShovelItem( 1.5f, -3f, ModToolTiers.ENDERSTEEL, BlockTags.MINEABLE_WITH_SHOVEL, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_shovel.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_AXE = ITEMS.register("endersteel_axe",
            () -> new EndersteelAxeItem(ModToolTiers.ENDERSTEEL, 5, -3f, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_axe.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_HOE = ITEMS.register("endersteel_hoe",
            () -> new EndersteelHoeItem(ModToolTiers.ENDERSTEEL, -4, 0, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_hoe.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    /*Hammer Textures adapted from Forge TutorialMod 1.21.X by Kaupenjoe
    Distributed under the MIT License*/
    public static final RegistryObject<Item> ENDERSTEEL_HAMMER = ITEMS.register("endersteel_hammer",
            () -> new EndersteelHammerItem(6, -3.2f, ModToolTiers.ENDERSTEEL_BLOCK, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_hammer.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });

    public static final RegistryObject<Item> NETHERITE_HAMMER = ITEMS.register("netherite_hammer",
        () -> new HammerItem(6, -3.2f, ModToolTiers.NETHERITE_BLOCK, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties().fireResistant()));
    public static final RegistryObject<Item> DIAMOND_HAMMER = ITEMS.register("diamond_hammer",
            () -> new HammerItem(6, -3.2f, ModToolTiers.DIAMOND_BLOCK, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties()));
    public static final RegistryObject<Item> IRON_HAMMER = ITEMS.register("iron_hammer",
            () -> new HammerItem(6, -3.2f, ModToolTiers.IRON_BLOCK, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties()));
    public static final RegistryObject<Item> GOLD_HAMMER = ITEMS.register("gold_hammer",
            () -> new HammerItem(6, -3.2f, ModToolTiers.GOLD_BLOCK, BlockTags.MINEABLE_WITH_PICKAXE, new Item.Properties()));

    public static final RegistryObject<Item> ENDERSTEEL_HELMET = ITEMS.register("endersteel_helmet",
            () -> new EndersteelArmorItem(new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_CHESTPLATE = ITEMS.register("endersteel_chestplate",
            () -> new ArmorItem(ModArmorMaterials.ENDERSTEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_armor.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_LEGGINGS = ITEMS.register("endersteel_leggings",
            () -> new ArmorItem(ModArmorMaterials.ENDERSTEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_armor.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_BOOTS = ITEMS.register("endersteel_boots",
            () -> new ArmorItem(ModArmorMaterials.ENDERSTEEL, ArmorItem.Type.BOOTS, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_armor.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDER_BOW = ITEMS.register("ender_bow",
            () -> new BowItem(new Item.Properties().durability(1528)));

    public static final RegistryObject<Item> DIAMOND_ELYTRA_CHESTPLATE = ITEMS.register("diamond_elytra_chestplate",
            () -> new ElytraChestplateItem(ArmorMaterials.DIAMOND, new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_ELYTRA_CHESTPLATE = ITEMS.register("netherite_elytra_chestplate",
            () -> new ElytraChestplateItem(ArmorMaterials.NETHERITE, new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_ELYTRA_CHESTPLATE = ITEMS.register("endersteel_elytra_chestplate",
            () -> new ElytraChestplateItem(ModArmorMaterials.ENDERSTEEL, new Item.Properties()) {
                @Override
                public void appendHoverText(@NotNull ItemStack pStack, @Nullable Level pLevel, @NotNull List<Component> pTooltipComponents, @NotNull TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_armor.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ELYTRA_CHESTPLATE_UPGRADE_SMITHING_TEMPLATE = ITEMS.register("elytra_chestplate_upgrade_smithing_template",
            ElytraChestplateSmithingTemplateItem::createElytraChestplateUpgradeTemplate);

    public static final RegistryObject<Item> AURORA_POWDER = ITEMS.register("aurora_powder",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ENDER_SLIMEBALL = ITEMS.register("ender_slimeball",
            () -> new EnderSlimeballItem(new Item.Properties()));
    public static final RegistryObject<Item> ENDER_SLIME_SPAWN_EGG = ITEMS.register("ender_slime_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntities.ENDER_SLIME, 0x5b1386, 0xa159cb, new Item.Properties()));

    public static final RegistryObject<Item> SLINGSHOT = ITEMS.register("slingshot",
            () -> new SlingshotItem(new Item.Properties().durability(577)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
