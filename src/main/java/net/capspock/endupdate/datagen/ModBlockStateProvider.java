package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.capspock.endupdate.block.custom.AuroraLampBlock;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EndUpdate.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.ENDERSTEEL_BLOCK);
        blockWithItem(ModBlocks.ENDERSTEEL_ORE);
        blockWithItem(ModBlocks.MAGIC_BLOCK);
        blockWithItem(ModBlocks.VOID_INFUSER);

        customLamp();

        logBlock(ModBlocks.ABYSSAL_LOG.get());
        axisBlock(ModBlocks.ABYSSAL_WOOD.get(), blockTexture(ModBlocks.ABYSSAL_LOG.get()), blockTexture(ModBlocks.ABYSSAL_LOG.get()));
        logBlock(ModBlocks.STRIPPED_ABYSSAL_LOG.get());
        axisBlock(ModBlocks.STRIPPED_ABYSSAL_WOOD.get(), blockTexture(ModBlocks.STRIPPED_ABYSSAL_LOG.get()), blockTexture(ModBlocks.STRIPPED_ABYSSAL_LOG.get()));

        blockItem(ModBlocks.ABYSSAL_LOG);
        blockItem(ModBlocks.ABYSSAL_WOOD);
        blockItem(ModBlocks.STRIPPED_ABYSSAL_LOG);
        blockItem(ModBlocks.STRIPPED_ABYSSAL_WOOD);

        blockWithItem(ModBlocks.ABYSSAL_PLANKS);

        leavesBlock(ModBlocks.ABYSSAL_LEAVES);
        saplingBlock(ModBlocks.ABYSSAL_SAPLING);

        simpleBlockWithItem(ModBlocks.ABYSSAL_MOSS.get(), models().cubeBottomTop(EndUpdate.MOD_ID, ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + "abyssal_moss_side"), ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + "abyssal_moss_bottom"), ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + "abyssal_moss_top")));

        stairsBlock(ModBlocks.ABYSSAL_STAIRS.get(), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()));
        slabBlock(ModBlocks.ABYSSAL_SLAB.get(), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()));
        buttonBlock(ModBlocks.ABYSSAL_BUTTON.get(), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()));
        pressurePlateBlock(ModBlocks.ABYSSAL_PRESSURE_PLATE.get(), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()));
        fenceBlock(ModBlocks.ABYSSAL_FENCE.get(), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()));
        fenceGateBlock(ModBlocks.ABYSSAL_FENCE_GATE.get(), blockTexture(ModBlocks.ABYSSAL_PLANKS.get()));
        doorBlockWithRenderType(ModBlocks.ABYSSAL_DOOR.get(), modLoc("block/abyssal_door_bottom"), modLoc("block/abyssal_door_top"), "cutout");
        trapdoorBlockWithRenderType(ModBlocks.ABYSSAL_TRAPDOOR.get(), modLoc("block/abyssal_trapdoor_bottom"), true, "cutout");

        blockItem(ModBlocks.ABYSSAL_STAIRS);
        blockItem(ModBlocks.ABYSSAL_SLAB);
        blockItem(ModBlocks.ABYSSAL_PRESSURE_PLATE);
        blockItem(ModBlocks.ABYSSAL_FENCE);
        blockItem(ModBlocks.ABYSSAL_FENCE_GATE);
        blockItem(ModBlocks.ABYSSAL_TRAPDOOR, "_bottom");
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void customLamp() {
        getVariantBuilder(ModBlocks.AURORA_LAMP.get()).forAllStates(state -> {
            if(state.getValue(AuroraLampBlock.CLICKED)) {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("aurora_lamp_on",
                        ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + "aurora_lamp_on")))};
            } else {
                return new ConfiguredModel[]{new ConfiguredModel(models().cubeAll("aurora_lamp_off",
                        ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + "aurora_lamp_off")))};
            }
        });
        simpleBlockItem(ModBlocks.AURORA_LAMP.get(), models().cubeAll("aurora_lamp_on",
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" +  "aurora_lamp_on")));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("endupdate:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }

    private void blockItem(RegistryObject<? extends Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("endupdate:block/" +
                ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }
}
