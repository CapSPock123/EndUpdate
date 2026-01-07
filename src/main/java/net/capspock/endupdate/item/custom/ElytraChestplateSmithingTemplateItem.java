package net.capspock.endupdate.item.custom;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.SmithingTemplateItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ElytraChestplateSmithingTemplateItem extends SmithingTemplateItem {
    public ElytraChestplateSmithingTemplateItem(Component pAppliesTo, Component pIngredients, Component pUpgradeDescription, Component pBaseSlotDescription, Component pAdditionsSlotDescription, List<ResourceLocation> pBaseSlotEmptyIcons, List<ResourceLocation> pAdditionalSlotEmptyIcons) {
        super(pAppliesTo, pIngredients, pUpgradeDescription, pBaseSlotDescription, pAdditionsSlotDescription, pBaseSlotEmptyIcons, pAdditionalSlotEmptyIcons);
    }

    private static final ChatFormatting TITLE_FORMAT = ChatFormatting.GRAY;
    private static final ChatFormatting DESCRIPTION_FORMAT = ChatFormatting.BLUE;

    private static final String DESCRIPTION_ID = Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "elytra_chestplate_upgrade_smithing_template"));

    private static final Component ELYTRA_CHESTPLATE_UPGRADE_APPLIES_TO = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "smithing_template.elytra_chestplate_upgrade.applies_to"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component ELYTRA_CHESTPLATE_UPGRADE_INGREDIENTS = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.withDefaultNamespace("elytra"))).withStyle(DESCRIPTION_FORMAT);
    private static final Component ELYTRA_CHESTPLATE_UPGRADE = Component.translatable(Util.makeDescriptionId("upgrade",
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "elytra_chestplate_upgrade"))).withStyle(TITLE_FORMAT);
    private static final Component ELYTRA_CHESTPLATE_UPGRADE_BASE_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "smithing_template.elytra_chestplate_upgrade.base_slot_description")));
    private static final Component ELYTRA_CHESTPLATE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION = Component.translatable(Util.makeDescriptionId("item",
            ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "smithing_template.elytra_chestplate_upgrade.additions_slot_description")));

    private static final ResourceLocation EMPTY_SLOT_ELYTRA = ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "item/empty_armor_slot_elytra");
    private static final ResourceLocation EMPTY_SLOT_CHESTPLATE = ResourceLocation.withDefaultNamespace("item/empty_armor_slot_chestplate");

    private static List<ResourceLocation> createElytraChestplateUpgradeIconList() { return List.of(EMPTY_SLOT_CHESTPLATE); }
    private static List<ResourceLocation> createElytraChestplateUpgradeMaterialList() {
        return List.of(EMPTY_SLOT_ELYTRA);
    }
    public static ElytraChestplateSmithingTemplateItem createElytraChestplateUpgradeTemplate() {
        return new ElytraChestplateSmithingTemplateItem(ELYTRA_CHESTPLATE_UPGRADE_APPLIES_TO, ELYTRA_CHESTPLATE_UPGRADE_INGREDIENTS,
                ELYTRA_CHESTPLATE_UPGRADE, ELYTRA_CHESTPLATE_UPGRADE_BASE_SLOT_DESCRIPTION, ELYTRA_CHESTPLATE_UPGRADE_ADDITIONS_SLOT_DESCRIPTION,
                createElytraChestplateUpgradeIconList(), createElytraChestplateUpgradeMaterialList());
    }

    @Override
    public @NotNull String getDescriptionId() {
        return DESCRIPTION_ID;
    }
}
