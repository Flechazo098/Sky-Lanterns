package committee.nova.skylanterns.init;

import committee.nova.skylanterns.SkyLanterns;
import committee.nova.skylanterns.common.blocks.LitBlock;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;


/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/24 9:47
 * Version: 1.0
 */
public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(SkyLanterns.MOD_ID);

    public static final DeferredBlock<Block> AIR_LIT = BLOCKS.register("air_lit", LitBlock::new);


    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}