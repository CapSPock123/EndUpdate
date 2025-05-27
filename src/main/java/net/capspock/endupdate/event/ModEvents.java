package net.capspock.endupdate.event;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.potion.ModPotions;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
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
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.brewing.BrewingRecipeRegisterEvent;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.capspock.endupdate.item.custom.HammerItem;

import java.util.HashSet;
import java.util.Set;

import static java.lang.Thread.sleep;

@Mod.EventBusSubscriber(modid = EndUpdate.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {
    private static Projectile projectile;
    private static Entity owner;
    private static ItemStack weaponItem;
    private static Entity target;

    private static boolean cancelEvents;

    private static int ticksPassed = 0;

    private static final Set<BlockPos> HARVESTED_BLOCKS = new HashSet<>();

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
    public static void onProjectileImpact(ProjectileImpactEvent event) throws InterruptedException {
        projectile = event.getProjectile();
        owner = event.getProjectile().getOwner();


        if (event.getProjectile().getOwner() != null) {
            weaponItem = event.getProjectile().getOwner().getWeaponItem();
        }

        sleep(0, 1);

        if(projectile instanceof Arrow arrow && owner instanceof Player player && target instanceof EnderMan enderMan) {
            if(weaponItem.getItem() == ModItems.ENDER_BOW.get() || player.getOffhandItem().getItem() == ModItems.ENDER_BOW.get())
                if(cancelEvents) {
                    ItemStack firedFromWeapon = weaponItem;
                    double baseDamage = arrow.getBaseDamage();
                    float multiplier = (float)arrow.getDeltaMovement().length();
                    DamageSource damageSource = enderMan.damageSources().mobAttack(player);

                    if(firedFromWeapon.isEnchanted()) {
                        int powerLevel = firedFromWeapon.getEnchantments().getLevel(Enchantments.POWER.getOrThrow(player));
                        if(powerLevel > 0) {
                            for(int i = 0; i < powerLevel; i++) {
                                baseDamage = baseDamage + 0.5;
                                System.out.println(baseDamage);
                            }
                        }
                    }

                    int finalDamage = Mth.ceil(Mth.clamp((double) multiplier * baseDamage, 0.0, 2.147483647E9));
                    if (arrow.isCritArrow()) {
                        long k = arrow.getRandom().nextInt(finalDamage / 2 + 2);
                        finalDamage = (int)Math.min(k + (long)finalDamage, 2147483647L);
                    }

                    double baseKnockback = (arrow.level() instanceof ServerLevel serverlevel
                            ? EnchantmentHelper.modifyKnockback(serverlevel, firedFromWeapon, enderMan, damageSource, 0.0F)
                            : 0.0F);
                    if (baseKnockback > 0.0) {
                        double knockbackResistance = Math.max(0.0, 1.0 - enderMan.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE));
                        Vec3 vec3 = arrow.getDeltaMovement().multiply(1.0, 0.0, 1.0).normalize().scale(baseKnockback * 0.6 * knockbackResistance);
                        if (vec3.lengthSqr() > 0.0) {
                            enderMan.push(vec3.x, 0.1, vec3.z);
                        }
                    }

                    if(arrow.isOnFire()) {
                        enderMan.igniteForSeconds(5f);
                    }

                    enderMan.hurt(damageSource, finalDamage);

                    if(!player.level().isClientSide()) {
                        ServerPlayer serverPlayer = (ServerPlayer) player;
                        String command = "advancement grant @a only minecraft:adventure/shoot_arrow";
                        CommandSourceStack commandSourceStack = serverPlayer.createCommandSourceStack().withSuppressedOutput().withPermission(4);
                        CommandDispatcher<CommandSourceStack> commanddispatcher = serverPlayer.getServer().getCommands().getDispatcher();
                        ParseResults<CommandSourceStack> results = commanddispatcher.parse(command, commandSourceStack);

                        serverPlayer.getServer().getCommands().performCommand(results, command);
                    }

                    if (arrow.level() instanceof ServerLevel serverlevel1) {
                        EnchantmentHelper.doPostAttackEffectsWithItemSource(serverlevel1, enderMan, damageSource, firedFromWeapon);
                    }

                    Entity entity = arrow.getEffectSource();
                    PotionContents potioncontents = arrow.getPickupItemStackOrigin().getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                    if (potioncontents.potion().isPresent()) {
                        for (MobEffectInstance mobeffectinstance : potioncontents.potion().get().value().getEffects()) {
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

                    for (MobEffectInstance mobeffectinstance1 : potioncontents.customEffects()) {
                        enderMan.addEffect(mobeffectinstance1, entity);
                    }

                    arrow.discard();

                    cancelEvents = false;
                    resetVariables();
            }
        }
    }

    @SubscribeEvent
    public static void onTeleport(EntityTeleportEvent.EnderEntity event) {
        target = event.getEntity();
        if(projectile instanceof Arrow && owner instanceof Player &&
                weaponItem.getItem() == ModItems.ENDER_BOW.get() && target instanceof EnderMan) {
            cancelEvents = true;
            event.setCanceled(true);
        }
    }

    private static void resetVariables() {
        projectile = null;
        owner = null;
        weaponItem = null;
    }

    @SubscribeEvent
    public static void tick(TickEvent event) {
        if(projectile instanceof Arrow && owner instanceof Player player && target instanceof EnderMan) {
            if(weaponItem.getItem() == ModItems.ENDER_BOW.get() || player.getOffhandItem().getItem() == ModItems.ENDER_BOW.get()) {
                ticksPassed++;
                if (ticksPassed >= 15) {
                    resetVariables();
                    ticksPassed = 0;
                }
            }
        }
    }
}
