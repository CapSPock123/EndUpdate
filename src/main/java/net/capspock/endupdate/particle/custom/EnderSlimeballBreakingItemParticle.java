package net.capspock.endupdate.particle.custom;

import net.capspock.endupdate.item.ModItems;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.BreakingItemParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class EnderSlimeballBreakingItemParticle extends BreakingItemParticle {
    protected EnderSlimeballBreakingItemParticle(ClientLevel pLevel, double pX, double pY, double pZ, ItemStack pStack) {
        super(pLevel, pX, pY, pZ, pStack);
    }

    @OnlyIn(Dist.CLIENT)
    public static class EnderSlimeProvider implements ParticleProvider<SimpleParticleType> {
        @Override
        public @Nullable Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            return new EnderSlimeballBreakingItemParticle(pLevel, pX, pY, pZ, new ItemStack(ModItems.ENDER_SLIMEBALL.get()));
        }
    }
}
