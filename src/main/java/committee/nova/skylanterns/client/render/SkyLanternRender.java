package committee.nova.skylanterns.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import committee.nova.skylanterns.SkyLanterns;
import committee.nova.skylanterns.client.model.PaperLanternPinkModel;
import committee.nova.skylanterns.common.configs.ModConfig;
import committee.nova.skylanterns.common.entities.SkyLanternEntity;
import committee.nova.skylanterns.init.ModRenderTypes;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.DyeColor;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

import java.util.HashMap;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/2/12 9:04
 * Version: 1.0
 */
public class SkyLanternRender extends EntityRenderer<SkyLanternEntity> {

    private final PaperLanternPinkModel model;
    public HashMap<Integer, ResourceLocation> TEXTURES = new HashMap<>();

    public SkyLanternRender(EntityRendererProvider.Context context) {
        super(context);
        this.model = new PaperLanternPinkModel(context.bakeLayer(PaperLanternPinkModel.LAYER_LOCATION));

        TEXTURES.put(DyeColor.ORANGE.getId(), ResourceLocation.parse(SkyLanterns.MOD_ID + ":textures/entity/sky_lantern_orange.png"));
        TEXTURES.put(DyeColor.PINK.getId(), ResourceLocation.parse(SkyLanterns.MOD_ID + ":textures/entity/sky_lantern_pink.png"));
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(SkyLanternEntity entity) {
        return ResourceLocation.parse(SkyLanterns.MOD_ID + ":textures/entity/sky_lantern_" + entity.getColor().getRegistryPrefix() + ".png");
    }

    @Override
    public void render(SkyLanternEntity pEntity, float pEntityYaw, float pPartialTicks, PoseStack pPoseStack, MultiBufferSource pBuffer, int pPackedLight) {
        pPoseStack.pushPose();

        pPoseStack.translate(0, 0.25, 0);
        float scale = 0.25F;
        pPoseStack.scale(scale, scale, scale);
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(180));

        // 被牵引至指定地点
        if (pEntity.isLatchedToEntity()) {
            double x = pEntity.latchedEntity.xOld + (pEntity.latchedEntity.getX() - pEntity.latchedEntity.xOld) * pPartialTicks
                    - (pEntity.xOld + (pEntity.getX() - pEntity.xOld) * pPartialTicks);
            double y = pEntity.latchedEntity.yOld + (pEntity.latchedEntity.getY() - pEntity.latchedEntity.yOld) * pPartialTicks
                    - (pEntity.yOld + (pEntity.getY() - pEntity.yOld) * pPartialTicks)
                    + pEntity.getAddedHeight();
            double z = pEntity.latchedEntity.zOld + (pEntity.latchedEntity.getZ() - pEntity.latchedEntity.zOld) * pPartialTicks
                    - (pEntity.zOld + (pEntity.getZ() - pEntity.zOld) * pPartialTicks);
            pPoseStack.translate(x, y, z);
        }

        // 动画计算
        long time = pEntity.level().getGameTime();
        long timeBase = time + (pEntity.getId() * 10L);
        float rate = 5;

        float tiltMax = (float) Math.sin(Math.toRadians(((timeBase) * 1F) % 360)) * 5F;
        float tiltCurX = (float) Math.sin(Math.toRadians(((timeBase) * rate) % 360)) * tiltMax;
        float tiltCurY = (float) Math.sin(Math.toRadians(((timeBase + 45) * rate) % 360)) * tiltMax;
        float tiltCurZ = (float) Math.sin(Math.toRadians(((timeBase + 90) * rate) % 360)) * tiltMax;
        float rotateY = (((float) timeBase * 0.1F) % 360);

        pPoseStack.mulPose(Axis.XP.rotationDegrees(tiltCurX));
        pPoseStack.mulPose(Axis.YP.rotationDegrees(tiltCurY + rotateY));
        pPoseStack.mulPose(Axis.ZP.rotationDegrees(tiltCurZ));

        this.setupRotations(pEntity, pPoseStack, pEntityYaw, pPartialTicks);
        this.model.setupAnim(pEntity, pPartialTicks, 0.0F, -0.1F, pEntityYaw, 0.0F);

        // 渲染模型
        ResourceLocation texture = this.getTextureLocation(pEntity);
        VertexConsumer vertexconsumer = pBuffer.getBuffer(ModRenderTypes.glowing(texture));
        this.model.renderToBuffer(pPoseStack, vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY);

        if (ModConfig.COMMON.enableLanternShader.get()) {
            pPoseStack.pushPose();
            pPoseStack.translate(0.0D, 0.6D, 0.0D);
            pPoseStack.mulPose(Axis.YP.rotationDegrees(-this.entityRenderDispatcher.camera.getYRot()));
            pPoseStack.mulPose(Axis.XP.rotationDegrees(this.entityRenderDispatcher.camera.getXRot()));

            float flicker = 0.85F + 0.15F * Mth.sin(((float) timeBase) * 0.05F);
            float s = 1.6F * flicker;

            int glowLight = 15728880;

            VertexConsumer glowConsumer = pBuffer.getBuffer(ModRenderTypes.lanternGlow());
            Matrix4f matrix = pPoseStack.last().pose();

            // 获取颜色分量
            var colorEnum = pEntity.getColor();
            float cr = colorEnum.getColor(0);
            float cg = colorEnum.getColor(1);
            float cb = colorEnum.getColor(2);
            float ca = 0.9F;

            // 增加亮度系数
            float brightness = 3.0F; // 增加亮度
            cr *= brightness;
            cg *= brightness;
            cb *= brightness;

            glowConsumer.addVertex(matrix, -s, -s, 0)
                    .setColor(cr, cg, cb, ca)
                    .setUv(0.0F, 1.0F)
                    .setLight(glowLight);

            glowConsumer.addVertex(matrix, s, -s, 0)
                    .setColor(cr, cg, cb, ca)
                    .setUv(1.0F, 1.0F)
                    .setLight(glowLight);

            glowConsumer.addVertex(matrix, s, s, 0)
                    .setColor(cr, cg, cb, ca)
                    .setUv(1.0F, 0.0F)
                    .setLight(glowLight);

            glowConsumer.addVertex(matrix, -s, s, 0)
                    .setColor(cr, cg, cb, ca)
                    .setUv(0.0F, 0.0F)
                    .setLight(glowLight);

            pPoseStack.popPose();
        }

        pPoseStack.popPose();
        super.render(pEntity, pEntityYaw, pPartialTicks, pPoseStack, pBuffer, pPackedLight);
    }

    protected void setupRotations(SkyLanternEntity entity, PoseStack pPoseStack, float pRotationYaw, float pPartialTicks) {
        pPoseStack.mulPose(Axis.YP.rotationDegrees(180.0F - pRotationYaw));

        if (entity.deathTime > 0) {
            float f = ((float) entity.deathTime + pPartialTicks - 1.0F) / 20.0F * 1.6F;
            f = Mth.sqrt(f);

            if (f > 1.0F) {
                f = 1.0F;
            }
            pPoseStack.mulPose(Axis.YP.rotationDegrees(f * 700F));
        }
    }
}