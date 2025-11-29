package committee.nova.skylanterns.init;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.resources.ResourceLocation;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/16 13:05
 * Version: 1.0
 */
public class ModRenderTypes {
    public static ShaderInstance LANTERN_SHADER;

    public static RenderType glowing(ResourceLocation texture) {
        return RenderType.create(
                "sky_lantern_glow",
                DefaultVertexFormat.NEW_ENTITY,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(GameRenderer::getRendertypeEntityTranslucentShader))
                        .setTextureState(new RenderStateShard.TextureStateShard(texture, false, false))
                        .setTransparencyState(RenderStateShard.TRANSLUCENT_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                        .setOverlayState(new RenderStateShard.OverlayStateShard(true))
                        .setWriteMaskState(RenderStateShard.COLOR_DEPTH_WRITE)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .createCompositeState(false)
        );
    }

    public static RenderType lanternGlow() {
        return RenderType.create(
                "lantern_glow_quad",
                DefaultVertexFormat.POSITION_TEX_COLOR,
                VertexFormat.Mode.QUADS,
                256,
                false,
                true,
                RenderType.CompositeState.builder()
                        .setShaderState(new RenderStateShard.ShaderStateShard(() -> LANTERN_SHADER != null ? LANTERN_SHADER : GameRenderer.getPositionTexColorShader()))
                        .setTextureState(new RenderStateShard.TextureStateShard(ResourceLocation.withDefaultNamespace("textures/misc/white.png"), true, false))
                        .setTransparencyState(RenderStateShard.LIGHTNING_TRANSPARENCY)
                        .setCullState(RenderStateShard.NO_CULL)
                        .setLightmapState(new RenderStateShard.LightmapStateShard(true))
                        .setOverlayState(new RenderStateShard.OverlayStateShard(false))
                        .setWriteMaskState(RenderStateShard.COLOR_WRITE)
                        .setDepthTestState(RenderStateShard.LEQUAL_DEPTH_TEST)
                        .createCompositeState(false)
        );
    }
}