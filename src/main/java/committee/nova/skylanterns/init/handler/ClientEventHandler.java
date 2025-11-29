package committee.nova.skylanterns.init.handler;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import committee.nova.skylanterns.SkyLanterns;
import committee.nova.skylanterns.client.model.ModModelCache;
import committee.nova.skylanterns.client.model.PaperLanternPinkModel;
import committee.nova.skylanterns.client.render.SkyLanternRender;
import committee.nova.skylanterns.init.ModEntities;
import committee.nova.skylanterns.init.ModRenderTypes;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;

import java.io.IOException;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/12 12:11
 * Version: 1.0
 */
@EventBusSubscriber(modid = SkyLanterns.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(PaperLanternPinkModel.LAYER_LOCATION, PaperLanternPinkModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterAdditional(ModelEvent.RegisterAdditional event) {
        ModModelCache.instance.setup();
    }

    @SubscribeEvent
    public static void onModelBake(ModelEvent.BakingCompleted event) {
        ModModelCache.instance.onBake(event);
    }

    @SubscribeEvent
    public static void onRegisterShaders(RegisterShadersEvent event) throws IOException {
        event.registerShader(new ShaderInstance(event.getResourceProvider(), SkyLanterns.rl("lantern_glow"), DefaultVertexFormat.POSITION_TEX_COLOR), shader -> ModRenderTypes.LANTERN_SHADER = shader);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            EntityRenderers.register(ModEntities.SKY_LANTERN.get(), SkyLanternRender::new);
        });
    }
}