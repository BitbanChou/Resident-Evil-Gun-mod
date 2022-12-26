package com.joy187.re8gun.item.model;

import com.joy187.re8gun.Main;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix3f;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mrcrayfish.guns.Reference;
import com.mrcrayfish.guns.client.GunRenderType;
import com.mrcrayfish.guns.client.handler.AimingHandler;
import com.mrcrayfish.guns.client.handler.GunRenderingHandler;
import com.mrcrayfish.guns.client.render.gun.IOverrideModel;
import com.mrcrayfish.guns.client.util.RenderUtil;
import com.mrcrayfish.guns.item.attachment.IAttachment;
import com.mrcrayfish.guns.util.OptifineHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.config.ModConfig;

import java.io.IOException;
import java.io.InputStream;
public class LongRange8xScopeModel implements IOverrideModel
{
    private static final ResourceLocation RED_DOT_RETICLE = new ResourceLocation(Reference.MOD_ID, "textures/effect/red_dot_reticle.png");
    //private static final ResourceLocation RED_DOT_RETICLE_GLOW = new ResourceLocation(Reference.MOD_ID, "textures/effect/red_dot_reticle_glow.png");
    //private static final ResourceLocation VIGNETTE = new ResourceLocation(Main.MOD_ID, "textures/item/standard_8x_scope_reticle.png");
    @Override
    public void render(float partialTicks, ItemTransforms.TransformType transformType, ItemStack stack, ItemStack parent, LivingEntity entity, PoseStack PoseStack, MultiBufferSource renderTypeBuffer, int light, int overlay) {
        PoseStack.pushPose();
        if (OptifineHelper.isShadersEnabled() && transformType.firstPerson() && entity.equals(Minecraft.getInstance().player)) {
            double transition = 1.0D - Math.pow(1.0D - AimingHandler.get().getNormalisedAdsProgress(), 2.0D);
            double zScale = 0.05D + 0.95D * (1.0D - transition);
            PoseStack.translate(0,0,transition*0.18);
            PoseStack.scale(1.0F, 1.0F, (float)zScale);
        }

        PoseStack.translate(0, -0.15, -0.38);
        PoseStack.translate(0, 0, 0.0015);
        RenderUtil.renderModel(stack, parent, PoseStack, renderTypeBuffer, light, overlay);

        PoseStack.translate(0, 0.15, 0.42);

        PoseStack.popPose();
        PoseStack.translate(0, 0, 0.04);
        if(transformType.firstPerson() && entity.equals(Minecraft.getInstance().player))
        {

//            if(entity.getMainHandItem() .getPrimaryHand() == Hand.LEFT)
//            {
//                PoseStack.scale(-1, 1, 1);
//            }

            float scopeSize = 0.785F;
            float scopePrevSize = 1.10F;
            float size = scopeSize / 16.0F;
            float reticleSize = scopePrevSize / 16.0F;

//            float crop = 0.4375F;
            float crop = 0.4425F;
            Minecraft mc = Minecraft.getInstance();
            Window window = mc.getWindow(); //.getMainWindow();


            float texU = ((window.getWidth() - window.getHeight() + window.getHeight() * crop * 2.0F) / 2.0F) / window.getWidth();

            PoseStack.pushPose();
            {
                Matrix4f matrix = PoseStack.last().pose();
                Matrix3f normal = PoseStack.last().normal();

                //PoseStack.translate(-size / 2, 0.0595 , 0.2);
                PoseStack.translate(-size / 2, 0.05225  , 3.37 * 0.0625); //4.70
                //PoseStack.translate(-size / 2, 0.08725  , Config.COMMON.gameplay.scopeDoubleRender.get() ? 4.70 * 0.0625 : 2.37 * 0.0625); //4.70

                // PoseStack.translate(-size / 2, 0.08725  , Config.COMMON.gameplay.scopeDoubleRender.get() ? 4.70 * 0.0625 : 2.37 * 0.0625); //4.70

                float color = (float) AimingHandler.get().getNormalisedAdsProgress() * 0.8F + 0.2F;

                VertexConsumer builder;

                if(!OptifineHelper.isShadersEnabled())
                {
                    builder = renderTypeBuffer.getBuffer(GunRenderType.getScreen());
                    builder.vertex(matrix, 0, size, 0).color(color, color, color, 1.0F).uv(texU, 1.0F - crop).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                    builder.vertex(matrix, 0, 0, 0).color(color, color, color, 1.0F).uv(texU, crop).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                    builder.vertex(matrix, size, 0, 0).color(color, color, color, 1.0F).uv(1.0F - texU, crop).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                    builder.vertex(matrix, size, size, 0).color(color, color, color, 1.0F).uv(1.0F - texU, 1.0F - crop).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
                }

                PoseStack.translate(0, 0, 0.0001);

                double invertProgress = (1.0 - AimingHandler.get().getNormalisedAdsProgress());
                PoseStack.translate(-0.04 * invertProgress, 0.01 * invertProgress, 0);

                double scale = 8.0;
                PoseStack.translate(size / 2, size / 2, 0);
                PoseStack.translate(-(size / scale) / 2, -(size / scale) / 2, 0);
                PoseStack.translate(0, 0, 0.0001);

                int reticleGlowColor = RenderUtil.getItemStackColor(stack, parent, 1);

                float red = ((reticleGlowColor >> 16) & 0xFF) / 255F;
                float green = ((reticleGlowColor >> 8) & 0xFF) / 255F;
                float blue = ((reticleGlowColor >> 0) & 0xFF) / 255F;
                float alpha = (float) AimingHandler.get().getNormalisedAdsProgress();

                alpha = (float) (1F * AimingHandler.get().getNormalisedAdsProgress());

                PoseStack.scale(10.0f,10.0f,10.0f);
                PoseStack.translate(-0.00455715, -0.00456, 0.0);
                builder = renderTypeBuffer.getBuffer(RenderType.entityTranslucent(RED_DOT_RETICLE));
                // Walking bobbing
                boolean aimed = false;
                /* The new controlled bobbing */
                if(AimingHandler.get().isAiming())
                    aimed = true;

                double invertZoomProgress = aimed ? 0.0575 : 0.468;//double invertZoomProgress = aimed ? 0.135 : 0.94;//aimed ? 1.0 - AimingHandler.get().getNormalisedAdsProgress() : ;
                float deltaDistanceWalked = entity.walkDist - entity.walkDistO;
                float distanceWalked = -(entity.walkDist + deltaDistanceWalked * partialTicks);
                float bobbing = Mth.lerp(partialTicks, entity.yHeadRot, entity.yHeadRotO);
                PoseStack.translate(-0.095*Math.asin(((double) (Math.sin(distanceWalked * (float) Math.PI)) * bobbing * 0.5F) * invertZoomProgress), 0.085*(Math.asin((double) (Math.abs(-Math.cos(distanceWalked *(float) Math.PI) * bobbing))) * invertZoomProgress * 1.140),0);//(Math.asin((double) (Math.abs(-Math.cos(distanceWalked*GunRenderingHandler.get().walkingCrouch * (float) Math.PI) * bobbing))) * invertZoomProgress * 1.140), 0.0D);// * 1.140, 0.0D);
                PoseStack.mulPose(Vector3f.ZN.rotationDegrees((float)(Math.sin(distanceWalked * (float) Math.PI) * bobbing * 3.0F) * (float) invertZoomProgress));
                PoseStack.mulPose(Vector3f.XN.rotationDegrees((float)(Math.abs(Math.cos(distanceWalked * (float) Math.PI - 0.2F) * bobbing) * 5.0F) * (float) invertZoomProgress));

                //PoseStack.translate(0, 0, (GunRenderingHandler.get().kick * -GunRenderingHandler.get().kickReduction)*0.75);
                PoseStack.translate(0, 0, -0.35);
                PoseStack.mulPose(Vector3f.YN.rotationDegrees(0.041f));
                PoseStack.mulPose(Vector3f.ZN.rotationDegrees(0.041f)); // seems to be interesting to increase the force of
                //PoseStack.translate(Vector3f.ZP.rotationDegrees(recoilSway * 2.5f * recoilReduction)); // seems to be interesting to increase the force of
                PoseStack.mulPose(Vector3f.XP.rotationDegrees( 0.0205F));
                PoseStack.translate(0, 0, 0.35);

//                builder = renderTypeBuffer.getBuffer(RenderType.entityTranslucent(VIGNETTE));
//                builder.vertex(matrix, 0, (float) (reticleSize / scale), 0).color(red, green, blue, alpha).uv(0.0F, 0.9375F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                builder.vertex(matrix, 0, 0, 0).color(red, green, blue, alpha).uv(0.0F, 0.0F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                builder.vertex(matrix, (float) (reticleSize / scale), 0, 0).color(red, green, blue, alpha).uv(0.9375F, 0.0F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                builder.vertex(matrix, (float) (reticleSize / scale), (float) (reticleSize / scale), 0).color(red, green, blue, alpha).uv(0.9375F, 0.9375F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();


            }
            PoseStack.popPose();
        }//float scopeSize = 1.095F;PoseStack.translate(-size / 2, 0.0645 , 3.45 * 0.0625);
    }

//    @Override
//    public void render(float partialTicks, ItemTransforms.TransformType transformType, ItemStack itemStack, ItemStack parent, LivingEntity livingEntity, PoseStack poseStack, MultiBufferSource multiBufferSource, int light, int overlay) {
//        poseStack.pushPose();
//        if (OptifineHelper.isShadersEnabled()) {
//            double transition = 1.0 - Math.pow(1.0 - AimingHandler.get().getNormalisedAdsProgress(), 2);
//            double zScale = 0.05 + 0.95 * (1.0 - transition);
//            poseStack.scale(1.0F, 1.0F, (float) zScale);
//        }
//
//        poseStack.translate(0, -0.15, -0.38);
//        poseStack.translate(0, 0, 0.0015);
//        poseStack.translate(0, 0.15, 0.42);
//
//        RenderUtil.renderModel(itemStack, parent, poseStack, multiBufferSource, light, overlay);
//        poseStack.popPose();
//        poseStack.translate(0, 0, 0.04);
//        if (transformType.firstPerson() && livingEntity.equals(Minecraft.getInstance().player)) {
//            poseStack.pushPose();
//            {
//                Matrix4f matrix = poseStack.last().pose();
//                Matrix3f normal = poseStack.last().normal();
//
//                float size = 1.4F / 16.0F;
//                poseStack.translate(-size / 2, 0.06313, -0.15875);
//                //poseStack.translate(-size / 2, 0.08725  , 4.70 * 0.0625 );
//
//                VertexConsumer builder;
//
//                if (!OptifineHelper.isShadersEnabled()) {
//                    builder = multiBufferSource.getBuffer(RenderType.entityTranslucent(VIGNETTE));
//                    builder.vertex(matrix, 0, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(1.0F, 1.0F).overlayCoords(overlay).uv2(light).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                    builder.vertex(matrix, size, 0, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(0.0F, 1.0F).overlayCoords(overlay).uv2(light).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                    builder.vertex(matrix, size, size, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(0.0F, 0.0F).overlayCoords(overlay).uv2(light).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                    builder.vertex(matrix, 0, size, 0).color(1.0F, 1.0F, 1.0F, 1.0F).uv(1.0F, 0.0F).overlayCoords(overlay).uv2(light).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                }
//
//                double invertProgress = (1.0 - AimingHandler.get().getNormalisedAdsProgress());
//                poseStack.translate(-0.04 * invertProgress, 0.01 * invertProgress, 0);
//
//                double scale = 6.0;
//                poseStack.translate(size / 2, size / 2, 0);
//                poseStack.translate(-(size / scale) / 2, -(size / scale) / 2, 0);
//                poseStack.translate(0, 0, 0.0001);
//
//                int reticleGlowColor = RenderUtil.getItemStackColor(itemStack, parent, 0);
//                CompoundTag tag = itemStack.getTag();
//                if (tag != null && tag.contains("ReticleColor", Tag.TAG_INT)) {
//                    reticleGlowColor = tag.getInt("ReticleColor");
//                }
//
//                float red = ((reticleGlowColor >> 16) & 0xFF) / 255F;
//                float green = ((reticleGlowColor >> 8) & 0xFF) / 255F;
//                float blue = ((reticleGlowColor >> 0) & 0xFF) / 255F;
//                float alpha = (float) AimingHandler.get().getNormalisedAdsProgress();
//
//                poseStack.translate(-0.00455715, -0.00456, 0.0);
//                if (!OptifineHelper.isShadersEnabled()) {
//                    builder = multiBufferSource.getBuffer(RenderType.entityTranslucent(RED_DOT_RETICLE_GLOW));
//                    builder.vertex(matrix, 0, (float) (size / scale), 0).color(red, green, blue, alpha).uv(0.0F, 0.9375F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                    builder.vertex(matrix, 0, 0, 0).color(red, green, blue, alpha).uv(0.0F, 0.0F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                    builder.vertex(matrix, (float) (size / scale), 0, 0).color(red, green, blue, alpha).uv(0.9375F, 0.0F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                    builder.vertex(matrix, (float) (size / scale), (float) (size / scale), 0).color(red, green, blue, alpha).uv(0.9375F, 0.9375F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                }
//
//                alpha = (float) (0.75F * AimingHandler.get().getNormalisedAdsProgress());
//
//                poseStack.translate(0, 0, 0.35);
//                builder = multiBufferSource.getBuffer(RenderType.entityTranslucent(RED_DOT_RETICLE));
//                builder.vertex(matrix, 0, (float) (size / scale), 0).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.9375F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                builder.vertex(matrix, 0, 0, 0).color(1.0F, 1.0F, 1.0F, alpha).uv(0.0F, 0.0F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                builder.vertex(matrix, (float) (size / scale), 0, 0).color(1.0F, 1.0F, 1.0F, alpha).uv(0.9375F, 0.0F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//                builder.vertex(matrix, (float) (size / scale), (float) (size / scale), 0).color(1.0F, 1.0F, 1.0F, alpha).uv(0.9375F, 0.9375F).overlayCoords(overlay).uv2(15728880).normal(normal, 0.0F, 1.0F, 0.0F).endVertex();
//            }
//            poseStack.popPose();
//        }
//    }
}