package net.capspock.endupdate.block.entity.custom;

import net.capspock.endupdate.block.entity.ModBlockEntities;
import net.capspock.endupdate.item.ModItems;
import net.capspock.endupdate.screen.custom.VoidInfuserMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

public class VoidInfuserBlockEntity extends BlockEntity implements MenuProvider {
    public final ItemStackHandler itemStackHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if(!level.isClientSide()) {
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
            }
        }
    };

    private static final int INPUT_SLOT = 0;
    private static final int INPUT_SLOT_ESSENCE = 1;
    private static final int OUTPUT_SLOT = 2;

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 144;

    public VoidInfuserBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.VOID_INFUSER_BE.get(), pPos, pBlockState);
        data = new ContainerData() {
            @Override
            public int get(int i) {
                return switch (i) {
                    case 0 -> VoidInfuserBlockEntity.this.progress;
                    case 1 -> VoidInfuserBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int i, int value) {
                switch (i) {
                    case 0 -> VoidInfuserBlockEntity.this.progress = value;
                    case 1 -> VoidInfuserBlockEntity.this.maxProgress = value;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        };
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemStackHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemStackHandler.getSlots());
        for(int i = 0; i < itemStackHandler.getSlots(); i++) {
            inventory.setItem(i, itemStackHandler.getStackInSlot(i));
        }
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        pTag.put("inventory", itemStackHandler.serializeNBT(pRegistries));
        pTag.putInt("void_infuser.progress", progress);
        pTag.putInt("void_infuser.max_progress", maxProgress);

        super.saveAdditional(pTag, pRegistries);
    }

    @Override
    protected void loadAdditional(CompoundTag pTag, HolderLookup.Provider pRegistries) {
        super.loadAdditional(pTag, pRegistries);

        itemStackHandler.deserializeNBT(pRegistries, pTag.getCompound("inventory"));
        progress = pTag.getInt("void_infuser.progress");
        maxProgress = pTag.getInt("void_infuser.max_progress");
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.endupdate.void_infuser");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new VoidInfuserMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if(hasRecipe()) {
            increaseCraftingProgress();
            setChanged(level, blockPos, blockState);

            if(hasRecipeFinished()) {
                craftItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }
    }

    private void craftItem() {
        ItemStack output = new ItemStack(ModItems.ENDERSTEEL_SWORD.get());

        itemStackHandler.extractItem(INPUT_SLOT, 1, false);
        itemStackHandler.extractItem(INPUT_SLOT_ESSENCE, 1, false);
        itemStackHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(output.getItem(),
                itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + output.getCount()));
    }

    private void resetProgress() {
        this.progress = 0;
        this.maxProgress = 72;
    }

    private boolean hasRecipeFinished() {
        return this.maxProgress <= this.progress;
    }

    private boolean hasRecipe() {
        Item input = Items.DIAMOND_SWORD;
        Item essence = ModItems.VOID_ESSENCE.get();
        ItemStack output = new ItemStack(ModItems.ENDERSTEEL_SWORD.get());

        return itemStackHandler.getStackInSlot(INPUT_SLOT).is(input) &&
                itemStackHandler.getStackInSlot(INPUT_SLOT_ESSENCE).is(essence) &&
                canInsertItemIntoOutputSlot(output)
                && canInsertAmountIntoOutputSlot(output.getCount());
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        int maxCount = itemStackHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ? 64 : itemStackHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
        return itemStackHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <= maxCount;
    }

    private boolean canInsertItemIntoOutputSlot(ItemStack output) {
        return itemStackHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                itemStackHandler.getStackInSlot(OUTPUT_SLOT).getItem() == output.getItem();
    }

    private void increaseCraftingProgress() {
        progress++;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider pRegistries) {
        return saveWithoutMetadata(pRegistries);
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
