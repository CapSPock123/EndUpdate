package net.capspock.endupdate.item;

import net.capspock.endupdate.EndUpdate;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
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
            () -> new SwordItem(ModToolTiers.ENDERSTEEL, 3, -2.4f, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_sword.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });
    public static final RegistryObject<Item> ENDERSTEEL_PICKAXE = ITEMS.register("endersteel_pickaxe",
            () -> new PickaxeItem(ModToolTiers.ENDERSTEEL, 1, -2.8f, new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_SHOVEL = ITEMS.register("endersteel_shovel",
            () -> new ShovelItem(ModToolTiers.ENDERSTEEL, 1.5f, -3f, new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_AXE = ITEMS.register("endersteel_axe",
            () -> new AxeItem(ModToolTiers.ENDERSTEEL, 5, -3f, new Item.Properties()));
    public static final RegistryObject<Item> ENDERSTEEL_HOE = ITEMS.register("endersteel_hoe",
            () -> new HoeItem(ModToolTiers.ENDERSTEEL, -4, 0, new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
                    if(Screen.hasShiftDown()) {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.endersteel_hoe.tooltip"));
                    } else {
                        pTooltipComponents.add(Component.translatable("tooltip.endupdate.shift.tooltip"));
                    }
                    super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
