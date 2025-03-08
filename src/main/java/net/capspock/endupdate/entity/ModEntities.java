package net.capspock.endupdate.entity;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.entity.custom.EnderSlimeEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, EndUpdate.MOD_ID);

    public static final RegistryObject<EntityType<EnderSlimeEntity>> ENDER_SLIME =
            ENTITY_TYPES.register("ender_slime", () -> EntityType.Builder.of(EnderSlimeEntity::new, MobCategory.MONSTER)
                    .sized(0.52f, 0.52f).build("ender_slime"));


    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
