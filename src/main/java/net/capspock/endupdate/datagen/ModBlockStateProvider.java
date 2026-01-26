package net.capspock.endupdate.datagen;

import net.capspock.endupdate.EndUpdate;
import net.capspock.endupdate.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, EndUpdate.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        blockWithItem(ModBlocks.ENDERSTEEL_ORE);
        blockWithItem(ModBlocks.ENDERSTEEL_BLOCK);
        blockWithItem(ModBlocks.RAW_ENDERSTEEL_BLOCK);

        cubeBottomTopBlockWithItem(ModBlocks.ENDER_BOG);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void cubeBottomTopBlockWithItem(RegistryObject<Block> blockRegistryObject) {
        String path = blockRegistryObject.getId().getPath();
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeBottomTop(path,
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + path + "_side"),
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + path + "_bottom"),
                ResourceLocation.fromNamespaceAndPath(EndUpdate.MOD_ID, "block/" + path + "_top")));
    }
}
