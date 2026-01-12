package net.capspock.endupdate.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModArmorMaterials;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.item.custom.*;
import net.capspock.endupdate.util.ModTags;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.*;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Logger log = LoggerFactory.getLogger(ModEvents.class);

    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        Entity hurtEntity = event.getEntity();
        Entity sourceEntity = event.getSource().getDirectEntity();
        if(hurtEntity instanceof Sheep sheep && sourceEntity instanceof Player player) {
            if(player.getMainHandItem().getItem() == Items.END_ROD) {
                player.sendSystemMessage(Component.literal(player.getName().getString() + " JUST HIT A SHEEP WITH AN END ROD! YOU SICK FRICK!!!!"));
                sheep.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 4));
                player.getMainHandItem().shrink(1);
            }
        }

        if(hurtEntity instanceof ServerPlayer player && isWearingFullSet(player, ModArmorMaterials.ENDERSTEEL) &&
                player.getHealth() <= player.getMaxHealth() * 0.5 && Math.random() <= 0.5 && sourceEntity instanceof LivingEntity &&
                player.getInventory().getArmor(3).getItem() instanceof EndersteelArmorItem endersteelArmorItem && endersteelArmorItem.cooldown == 0) {
            endersteelArmorItem.isEchoScheduled = true;
            endersteelArmorItem.amount = event.getAmount();
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event) {
        if(event.getEntity() instanceof Player player && player.getInventory().getArmor(2).getItem() instanceof ElytraChestplateItem elytraChestplateItem) {
            if(player.getInventory().getArmor(2).getDamageValue() == elytraChestplateItem.getDefaultInstance().getMaxDamage() - 1) {
                ItemStack elytra = new ItemStack(Items.ELYTRA);
                player.setItemSlot(EquipmentSlot.CHEST, elytra);
                elytra.setDamageValue(player.getInventory().getArmor(2).getTag().getInt("endupdate.elytra_damage"));
            }
        }
    }

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        Item mainHandItem = player.getMainHandItem().getItem();
        LevelAccessor level = event.getLevel();
        BlockPos blockPos = event.getPos();
        BlockState blockState = level.getBlockState(blockPos);
        Block block = blockState.getBlock();

        if(mainHandItem instanceof EndersteelHoeItem endersteelHoeItem && blockState.is(BlockTags.CROPS) &&
                !(blockState.getBlock() instanceof StemBlock) && !(blockState.getBlock() instanceof PitcherCropBlock)) {
            if(((CropBlock) block).isMaxAge(blockState)) {
                endersteelHoeItem.isEchoScheduled = true;
                endersteelHoeItem.blockState = blockState;
                endersteelHoeItem.player = player;
                endersteelHoeItem.blockPos = blockPos;
            }
        } else if(mainHandItem instanceof EndersteelAxeItem endersteelAxeItem) {
            if(blockState.is(BlockTags.LOGS) && endersteelAxeItem.isEchoOn()) {
                int blocksBroken = 1;
                List<BlockPos> positionsToCheck = new ArrayList<>();
                List<BlockPos> savedPositions = new ArrayList<>();
                List<BlockPos> positionsToBreak = new ArrayList<>();
                savedPositions.add(blockPos);
                while(!savedPositions.isEmpty()) {
                    BlockPos.betweenClosed(savedPositions.get(0).below().south().west(), savedPositions.get(0).above().north().east())
                            .forEach(position -> positionsToCheck.add(position.immutable()));
                    savedPositions.remove(0);
                    for(BlockPos pos : positionsToCheck) {
                        if(level.getBlockState(pos).is(BlockTags.LOGS) && !positionsToBreak.contains(pos)) {
                            savedPositions.add(pos);
                            positionsToBreak.add(pos);
                        }
                    }
                    positionsToCheck.clear();
                }
                for(BlockPos pos : positionsToBreak) {
                    if (blocksBroken <= 27) {
                        endersteelAxeItem.toBreak.add(pos);
                        blocksBroken++;
                    }
                }
            }
        } else if(mainHandItem instanceof HammerItem hammerItem) {
            Level level1 = (Level) level;
            if (player instanceof ServerPlayer serverPlayer) {
                for (BlockPos pos : hammerItem.getBlocksToBeDestroyed(1, blockPos, serverPlayer)) {
                    if (pos == blockPos || !hammerItem.isCorrectToolForDrops(mainHandItem.getDefaultInstance(), event.getLevel().getBlockState(pos))) {
                        continue;
                    }
                    BlockState state1 = level.getBlockState(pos);
                    level1.destroyBlock(pos, serverPlayer.gameMode.isSurvival(), serverPlayer);
                    serverPlayer.awardStat(Stats.BLOCK_MINED.get(block));

                    if (hammerItem instanceof EndersteelHammerItem endersteelHammerItem && state1.is(Tags.Blocks.ORES) && block != Blocks.ANCIENT_DEBRIS) {
                        endersteelHammerItem.checkedBlocks.add(pos);
                        endersteelHammerItem.checkedBlocksBlockstates.add(state1);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLeftClickAir(PlayerInteractEvent.LeftClickEmpty event) {
        Player player = event.getEntity();
        Item mainHandItem = player.getMainHandItem().getItem();
        if(player.isShiftKeyDown()) {
            if(mainHandItem instanceof EndersteelShovelItem endersteelShovelItem) {
                if(endersteelShovelItem.isEchoOn()) {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_off"), true);
                    endersteelShovelItem.setEchoOn(false);
                } else {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_on"), true);
                    endersteelShovelItem.setEchoOn(true);
                }
            } else if(mainHandItem instanceof EndersteelAxeItem endersteelAxeItem) {
                if(endersteelAxeItem.isEchoOn()) {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_off"), true);
                    endersteelAxeItem.setEchoOn(false);
                } else {
                    player.displayClientMessage(Component.translatable("actionbar.endupdate.echo_toggle_on"), true);
                    endersteelAxeItem.setEchoOn(true);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onEnderPearlTeleport(EntityTeleportEvent.EnderPearl event) {
        if(isWearingFullSet(event.getPlayer(), ModArmorMaterials.ENDERSTEEL)) {
            event.setAttackDamage(0);
        }
    }

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent event) {
        Projectile projectile = event.getProjectile();
        Entity owner = event.getProjectile().getOwner();
        if(owner != null && !projectile.level().isClientSide()) {
            if(event.getRayTraceResult() instanceof EntityHitResult entityHitResult && entityHitResult.getEntity() instanceof EnderMan enderMan &&
                    owner instanceof Player player && projectile instanceof Arrow arrow && (player.getMainHandItem().getItem() == ModItems.ENDER_BOW.get() ||
                    player.getOffhandItem().getItem() == ModItems.ENDER_BOW.get())) {
                event.setCanceled(true);

                float f = (float)arrow.getDeltaMovement().length();
                int i = Mth.ceil(Mth.clamp((double)f * arrow.getBaseDamage(), 0.0D, Integer.MAX_VALUE));

                if (arrow.isCritArrow()) {
                    long j = RandomSource.create().nextInt(i / 2 + 2);
                    i = (int)Math.min(j + (long)i, 2147483647L);
                }

                DamageSource damagesource = enderMan.damageSources().playerAttack(player);
                player.setLastHurtMob(enderMan);

                if (arrow.isOnFire()) {
                    enderMan.setSecondsOnFire(5);
                }

                if (enderMan.hurt(damagesource, (float)i)) {
                    if (!arrow.level().isClientSide && arrow.getPierceLevel() <= 0) {
                        enderMan.setArrowCount(enderMan.getArrowCount() + 1);
                    }

                    double d0 = player.getX() - arrow.getX();

                    double d1;
                    for(d1 = player.getZ() - arrow.getZ(); d0 * d0 + d1 * d1 < 1.0E-4D; d1 = (Math.random() - Math.random()) * 0.01D) {
                        d0 = (Math.random() - Math.random()) * 0.01D;
                    }

                    enderMan.knockback(0.4F, d0, d1);

                    if (arrow.getKnockback() > 0) {
                        double d2 = Math.max(0.0D, 1.0D - enderMan.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        Vec3 vec3 = arrow.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D)
                                .normalize().scale((double) arrow.getKnockback() * 0.6D * d2);
                        if (vec3.lengthSqr() > 0.0D) {
                            enderMan.push(vec3.x, 0.1D, vec3.z);
                        }
                    }

                    if (!arrow.level().isClientSide) {
                        EnchantmentHelper.doPostHurtEffects(enderMan, player);
                        EnchantmentHelper.doPostDamageEffects(player, enderMan);
                    }

                    arrow.playSound(SoundEvents.ARROW_HIT, 1.0F, 1.2F / (RandomSource.create().nextFloat() * 0.2F + 0.9F));
                    arrow.doPostHurtEffects(enderMan);
                    arrow.discard();

                    if(!player.level().isClientSide()) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        if (serverPlayer.getServer() != null) {
                            String command = "advancement grant @s only minecraft:adventure/shoot_arrow";
                            CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withSuppressedOutput().withPermission(4);
                            CommandDispatcher<CommandSourceStack> commanddispatcher = serverPlayer.getServer().getCommands().getDispatcher();
                            ParseResults<CommandSourceStack> results = commanddispatcher.parse(command, commandSourceStack);

                            serverPlayer.getServer().getCommands().performCommand(results, command);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onAnvilChange(AnvilUpdateEvent event) {
        ItemStack left = event.getLeft();
        ItemStack right = event.getRight();
        String key = "endupdate.elytra_damage";
        if(left.is(ModTags.Items.ELYTRA_CHESTPLATE) && right.is(Items.PHANTOM_MEMBRANE)) {
            ItemStack output = left.copy();
            ElytraChestplateItem elytraChestplateItem = (ElytraChestplateItem) output.getItem();
            int elytraDamageValue = elytraChestplateItem.getElytraDamageValue(output);
            int maxElytraDamageValue = 432;
            if(elytraChestplateItem.isElytraBarVisible(output)) {
                int materialCost = Math.min(4,right.getCount());
                double materialRepairValue = maxElytraDamageValue * materialCost * 0.25;
                double actualRepairValue = Math.min(elytraDamageValue, materialRepairValue);
                output.getTag().putInt(key, elytraDamageValue - (int) actualRepairValue);
                if(output.getTag().getInt(key) == 0) {
                    output.removeTagKey(key);
                }
                event.setOutput(output);

                int repairCost = output.getBaseRepairCost();
                if(repairCost == 0) {
                    repairCost = 1;
                }

                event.setCost(repairCost);
                output.setRepairCost(repairCost * 2);

                int materialAmountToBeConsumed = materialCost;

                for(int i = 1; i < materialCost; i++) {
                    double materialRepairValue1 = maxElytraDamageValue * i * 0.25;
                    double actualRepairValue1 = Math.min(elytraDamageValue, materialRepairValue1);
                    if(materialRepairValue1 != actualRepairValue1) {
                        if(materialRepairValue1 - actualRepairValue1 >= maxElytraDamageValue * 0.25 * (i - 1) &&
                                materialRepairValue1 - actualRepairValue1 < maxElytraDamageValue * 0.25 * (i + 1)) {
                            System.out.println("hi2");
                            materialAmountToBeConsumed = i;
                            break;
                        }
                    }
                }

                event.setMaterialCost(materialAmountToBeConsumed);
            }
        }
    }

    private static boolean isWearingFullSet(ServerPlayer player, ArmorMaterial armorMaterial) {
        Item boots = player.getInventory().getArmor(0).getItem();
        Item leggings = player.getInventory().getArmor(1).getItem();
        Item chestplate = player.getInventory().getArmor(2).getItem();
        Item helmet = player.getInventory().getArmor(3).getItem();

        if (helmet instanceof ArmorItem && chestplate instanceof ArmorItem &&
                leggings instanceof ArmorItem && boots instanceof ArmorItem) {
            return ((ArmorItem) boots).getMaterial() == armorMaterial &&
                    ((ArmorItem) leggings).getMaterial() == armorMaterial &&
                    ((ArmorItem) chestplate).getMaterial() == armorMaterial &&
                    ((ArmorItem) helmet).getMaterial() == armorMaterial;
        } else {
            return false;
        }
    }
}