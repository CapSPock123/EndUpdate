package net.capspock.endupdate.block.entity;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.block.entity.custom.VoidInfuserBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, EndUpdate.MOD_ID);

    public static final RegistryObject<BlockEntityType<VoidInfuserBlockEntity>> VOID_INFUSER_BE =
            BLOCK_ENTITIES.register("void_infuser_be", () -> BlockEntityType.Builder.of(
                    VoidInfuserBlockEntity::new, ModBlocks.VOID_INFUSER.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
