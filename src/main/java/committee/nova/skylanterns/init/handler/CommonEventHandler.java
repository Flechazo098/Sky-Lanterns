package committee.nova.skylanterns.init.handler;

import committee.nova.skylanterns.SkyLanterns;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/12 8:04
 * Version: 1.0
 */
@EventBusSubscriber(modid = SkyLanterns.MOD_ID)
public class CommonEventHandler {

    @SubscribeEvent
    public static void onCommonSetup(final FMLCommonSetupEvent event) {

    }
}
