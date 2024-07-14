package me.lyuxc.multiblock.event;

import me.lyuxc.multiblock.utils.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

import java.util.HashMap;
import java.util.Map;

import static me.lyuxc.multiblock.utils.Utils.getBlock;

@EventBusSubscriber
public class LodeStoneAltar {
    private static final String[] matrix = {
            "XXXXXXOXXXXXX",
            "XGMXXXONNNXXX",
            "XMXXXXOXXXXXX",
            "XXXXSSOXXGXNX",
            "XXXSXXOXXXXNX",
            "XXXSXXOXXXXNX",
            "OOOOOOXOOOOOO",
            "XNXXXXOXXSXXX",
            "XNXXXXOXXSXXX",
            "XNXGXXOSSXXXX",
            "XXXXXXOXXXXMX",
            "XXXNNNOXXXMGX",
            "XXXXXXOXXXXXX"
    };

    private static final String[] matrix2 = {
            "RRRRR",
            "RXXXR",
            "RXXXR",
            "RXXXR",
            "RRRRR"
    };

    private static final Map<Character, Block> blockMap = new HashMap<>();
    static {
        blockMap.put('O',Blocks.OBSIDIAN);
        blockMap.put('G',Blocks.GLOWSTONE);
        blockMap.put('M',Blocks.MAGMA_BLOCK);
        blockMap.put('S',Blocks.SOUL_SAND);
        blockMap.put('N',Blocks.NETHERRACK);
        blockMap.put('R',Blocks.REDSTONE_WIRE);
    }

    @SubscribeEvent
    public static void onPlayerRightLodeStone(UseItemOnBlockEvent event) {
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        Player player = event.getEntity();
        ItemStack itemStack = player.getItemBySlot(EquipmentSlot.MAINHAND);
        if(!level.isClientSide()) {
            if(event.getUsePhase() == UseItemOnBlockEvent.UsePhase.ITEM_AFTER_BLOCK) {
                if(getBlock(level,pos) == Blocks.LODESTONE) {
                    if(isValid(level,pos) && itemStack.is(Items.ENCHANTED_BOOK)) {
                        spawnEntity(level,pos);
                    }
                }
            }
        }
    }

    private static void spawnEntity(Level level, BlockPos pos) {
        Creeper creeper = new Creeper(EntityType.CREEPER,level);
        creeper.moveTo(pos.getX(),pos.getY(),pos.getZ());
        creeper.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(40);
        creeper.setHealth(creeper.getMaxHealth());
        level.addFreshEntity(creeper);
    }

    private static boolean isValid(Level level,BlockPos pos) {
        return Utils.isValid(level,pos,matrix,blockMap,-1,13,6) &&
                Utils.isValid(level,pos,matrix2,blockMap,0,5,2);
    }
}
