package me.lyuxc.multiblock.event;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FurnaceBlock;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber
public class FurnMultiblock {
    @SubscribeEvent
    public static void rightClickEvent(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Block block = getBlock(level,pos);
        int x = event.getPos().getX();
        int y = event.getPos().getY();
        int z = event.getPos().getZ();
        if(block instanceof FurnaceBlock) {
            if(!isValid(level,x,y,z) || level.isRaining()) {
                event.setCanceled(true);
            }
        }
    }

    public static Block getBlock(Level level, BlockPos pos) {
        return level.getBlockState(pos).getBlock();
    }

    private static Boolean isValid(Level level, int x, int y, int z) {
        return getBlock(level,new BlockPos(x + 1,y,z)) == Blocks.COAL_BLOCK &&
                getBlock(level,new BlockPos(x - 1,y,z)) == Blocks.COAL_BLOCK &&
                getBlock(level,new BlockPos(x,y,z + 1)) == Blocks.COAL_BLOCK &&
                getBlock(level,new BlockPos(x,y,z - 1)) == Blocks.COAL_BLOCK;
    }
}
