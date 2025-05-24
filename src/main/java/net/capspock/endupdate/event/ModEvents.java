package net.capspock.endupdate.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.potion.ModPotions;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Arrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.capspock.endupdate.item.custom.HammerItem;

import java.util.HashSet;
import java.util.Set;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

    private static Projectile projectile;
    private static Entity owner;
    private static ItemStack weaponItem;
    private static boolean haveVariablesChanged;

    // Done with the help of https://github.com/CoFH/CoFHCore/blob/1.19.x/src/main/java/cofh/core/event/AreaEffectEvents.java
    // Don't be a jerk License
    @SubscribeEvent
    public static void onHammerUsage(BlockEvent.BreakEvent event) {
        Player player = event.getPlayer();
        ItemStack mainHandItem = player.getMainHandItem();

        if(mainHandItem.getItem() instanceof HammerItem hammer && player instanceof ServerPlayer serverPlayer) {
            BlockPos initialBlockPos = event.getPos();
            if(HARVESTED_BLOCKS.contains(initialBlockPos)) {
                return;
            }

            for(BlockPos pos : HammerItem.getBlocksToBeDestroyed(1, initialBlockPos, serverPlayer)) {
                if(pos == initialBlockPos || !hammer.isCorrectToolForDrops(mainHandItem, event.getLevel().getBlockState(pos))) {
                    continue;
                }

                HARVESTED_BLOCKS.add(pos);
                serverPlayer.gameMode.destroyBlock(pos);
                HARVESTED_BLOCKS.remove(pos);
            }
        }
    }

    @SubscribeEvent
    public static void onBrewingRecipeRegister(BrewingRecipeRegisterEvent event) {
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(Potions.AWKWARD, Items.BEDROCK, ModPotions.VOID_POTION.getHolder().get());
        builder.addMix(ModPotions.VOID_POTION.getHolder().get(), Items.REDSTONE, ModPotions.LONG_VOID_POTION.getHolder().get());

        builder.addMix(Potions.AWKWARD, ModItems.ENDER_SLIMEBALL.get(), ModPotions.SLIMEY_POTION.getHolder().get());
    }

    @SubscribeEvent
    public static void onProjectileHit(ProjectileImpactEvent projectileImpactEvent) {
        haveVariablesChanged = true;
        projectile = projectileImpactEvent.getProjectile();
        owner = projectileImpactEvent.getProjectile().getOwner();
        if(owner != null) {
            weaponItem = projectileImpactEvent.getProjectile().getOwner().getWeaponItem();
        }
    }

    @SubscribeEvent
    public static void onTeleport(EntityTeleportEvent.EnderEntity event) {
        ItemStack firedFromWeapon = null;
        Entity owner1 = null;
        Projectile projectile1 = null;

        if(event.getEntity() instanceof EnderMan enderMan) {
            haveVariablesChanged = !event.isCanceled();

            if(haveVariablesChanged) {
                firedFromWeapon = weaponItem;
                owner1 = owner;
                projectile1 = projectile;
            }


            if (projectile1 instanceof Arrow arrow && owner1 instanceof Player player) {
                if(firedFromWeapon.getItem() == ModItems.ENDER_BOW.get() || player.getOffhandItem().getItem() == ModItems.ENDER_BOW.get()) {
                    event.setCanceled(true);

                    haveVariablesChanged = !event.isCanceled();

                    arrow.remove(Entity.RemovalReason.DISCARDED);

                    DamageSource damageSource = player.level().damageSources().indirectMagic(enderMan, player);

                    double baseDamage = arrow.getBaseDamage();
                    double multiplier = arrow.getDeltaMovement().length();

                    if (EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER.getOrThrow(player), firedFromWeapon) > 0) {
                        int powerLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.POWER.getOrThrow(player), firedFromWeapon);
                        if (powerLevel == 1) {
                            baseDamage++;
                        } else if (powerLevel > 1) {
                            baseDamage = baseDamage + 1 + 0.5 * (powerLevel - 1);
                        }
                    }

                    int finalDamage = Mth.ceil(Mth.clamp((multiplier * baseDamage), 0.0, 2.147483647E9));

                    if (arrow.isCritArrow()) {
                        long random = arrow.getRandom().nextInt(finalDamage / 2 + 2);
                        finalDamage = (int) Math.min(random + (long) finalDamage, 2147483647L);
                    }

                    enderMan.hurt(damageSource, finalDamage);
                    System.out.println(damageSource.getEntity() + " " + damageSource.getDirectEntity());

                    if(!player.level().isClientSide()) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;

                        String command = "advancement grant @a only minecraft:adventure/shoot_arrow";

                        CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withSuppressedOutput().withPermission(4);

                        CommandDispatcher<CommandSourceStack> commanddispatcher = serverPlayer.getServer().getCommands().getDispatcher();
                        ParseResults<CommandSourceStack> results = commanddispatcher.parse(command, commandSourceStack);

                        serverPlayer.getServer().getCommands().performCommand(results, command);
                    }

                    if (event.getEntity() instanceof LivingEntity livingEntity) {
                        double baseKnockback = arrow.level() instanceof ServerLevel serverlevel
                                ? EnchantmentHelper.modifyKnockback(serverlevel, firedFromWeapon, enderMan, damageSource, 0.015F)
                                : 0.0F;
                        double knockbackResistanceMultiplier = Math.max(0.0, 1.0 - enderMan.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        int punchLevel = EnchantmentHelper.getItemEnchantmentLevel(Enchantments.PUNCH.getOrThrow(player), firedFromWeapon);
                        boolean hasPunch = punchLevel > 0;

                        if (hasPunch) {
                            baseKnockback = baseKnockback * (1 + punchLevel - 0.6);
                        }

                        if (baseKnockback > 0) {
                            Vec3 vec3 = arrow.getDeltaMovement().multiply(1, 0, 1).normalize().scale(baseKnockback * 0.6 * knockbackResistanceMultiplier);

                            if (vec3.lengthSqr() > 0.0) {
                                if (hasPunch) {
                                    enderMan.push(vec3.x, 0.0075, vec3.z);
                                } else {
                                    enderMan.push(vec3.x, 0.005, vec3.z);
                                }
                            }
                        }

                        if (arrow.level() instanceof ServerLevel serverlevel1) {
                            EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, livingEntity, damageSource, firedFromWeapon);
                        }

                        if(EnchantmentHelper.getItemEnchantmentLevel(Enchantments.FLAME.getOrThrow(player), firedFromWeapon) > 0) {
                            enderMan.igniteForSeconds(5);
                        }

                        Entity entity = arrow.getEffectSource();
                        PotionContents potionContents = arrow.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                        if (potionContents.potion().isPresent()) {
                            for (MobEffectInstance mobeffectinstance : potionContents.potion().get().value().getEffects()) {
                                enderMan.addEffect(
                                        new MobEffectInstance(
                                                mobeffectinstance.getEffect(),
                                                Math.max(mobeffectinstance.mapDuration(p_268168_ -> p_268168_ / 8), 1),
                                                mobeffectinstance.getAmplifier(),
                                                mobeffectinstance.isAmbient(),
                                                mobeffectinstance.isVisible()
                                        ),
                                        entity
                                );
                            }
                        }

                        for (MobEffectInstance mobeffectinstance1 : potionContents.customEffects()) {
                            enderMan.addEffect(mobeffectinstance1, entity);
                        }
                    }
                }
            }
        }
    }
}
