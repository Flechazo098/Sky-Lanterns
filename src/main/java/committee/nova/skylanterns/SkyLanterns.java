package committee.nova.skylanterns;

import committee.nova.skylanterns.common.entities.SkyLanternEntity;
import committee.nova.skylanterns.init.ModBlocks;
import committee.nova.skylanterns.init.ModEntities;
import committee.nova.skylanterns.init.ModItems;
import committee.nova.skylanterns.init.ModTabs;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SkyLanterns.MOD_ID)
public class SkyLanterns {

    public static final String MOD_ID = "skylanterns";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public SkyLanterns(IEventBus modEventBus, ModContainer modContainer) {

        modContainer.registerConfig(ModConfig.Type.COMMON, committee.nova.skylanterns.common.configs.ModConfig.CONFIG_SPEC);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModTabs.TABS.register(modEventBus);
        modEventBus.addListener(SkyLanterns::addAttributes);
    }

    public static ResourceLocation rl(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    @SubscribeEvent
    public static void addAttributes(final EntityAttributeCreationEvent event) {
        event.put(ModEntities.SKY_LANTERN.get(), SkyLanternEntity.setAttributes().build());
    }
}