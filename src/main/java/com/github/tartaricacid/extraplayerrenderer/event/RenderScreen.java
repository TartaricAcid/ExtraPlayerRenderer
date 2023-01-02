package com.github.tartaricacid.extraplayerrenderer.event;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Quaternion;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class RenderScreen {
    private static float posX = ConfigFileManager.getConfigPojo().getPosX();
    private static float posY = ConfigFileManager.getConfigPojo().getPosY();
    private static float scale = ConfigFileManager.getConfigPojo().getScale();
    private static float yawOffset = ConfigFileManager.getConfigPojo().getYawOffset();

    @SubscribeEvent
    public static void onRenderScreen(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;

        if (player == null) {
            return;
        }

        RenderSystem.pushMatrix();
        RenderSystem.translatef(posX, posY, -500);
        RenderSystem.scalef(1, 1, -1);
        MatrixStack matrixstack = new MatrixStack();
        matrixstack.scale(scale, scale, scale);
        Quaternion zRot = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion yRot = Vector3f.YP.rotationDegrees(player.yBodyRot + yawOffset - 180);
        zRot.mul(yRot);
        matrixstack.mulPose(zRot);
        EntityRendererManager rendererManager = Minecraft.getInstance().getEntityRenderDispatcher();
        yRot.conj();
        rendererManager.overrideCameraOrientation(yRot);
        rendererManager.setRenderShadow(false);
        IRenderTypeBuffer.Impl buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> rendererManager.render(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, matrixstack, buffer, 15728880));
        buffer.endBatch();
        rendererManager.setRenderShadow(true);
        RenderSystem.popMatrix();
    }

    public static float getScale() {
        return scale;
    }

    public static void setScale(float scale) {
        RenderScreen.scale = scale;
    }

    public static float getPosX() {
        return posX;
    }

    public static void setPosX(float posX) {
        RenderScreen.posX = posX;
    }

    public static float getPosY() {
        return posY;
    }

    public static void setPosY(float posY) {
        RenderScreen.posY = posY;
    }

    public static float getYawOffset() {
        return yawOffset;
    }

    public static void setYawOffset(float yawOffset) {
        RenderScreen.yawOffset = yawOffset;
    }
}
