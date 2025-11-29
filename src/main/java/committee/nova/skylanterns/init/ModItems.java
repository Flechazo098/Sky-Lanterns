package committee.nova.skylanterns.init;

import committee.nova.skylanterns.SkyLanterns;
import committee.nova.skylanterns.common.items.SkyLanternsItem;
import committee.nova.skylanterns.utils.EnumColor;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/1/20 20:04
 * Version: 1.0
 */
public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(SkyLanterns.MOD_ID);

    public static final DeferredItem<Item> SKY_LANTERN_ORANGE = ITEMS.register("sky_lantern_orange",
            () -> new SkyLanternsItem(EnumColor.ORANGE));
    public static final DeferredItem<Item> SKY_LANTERN_PINK = ITEMS.register("sky_lantern_pink",
            () -> new SkyLanternsItem(EnumColor.BRIGHT_PINK));

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}