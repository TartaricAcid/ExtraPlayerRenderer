package com.github.tartaricacid.extraplayerrenderer.event;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.LivingEntity;
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
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;

        if (player == null) {
            return;
        }

        PoseStack poseStack = RenderSystem.getModelViewStack();
        poseStack.pushPose();
        poseStack.translate(posX, posY, -500);
        poseStack.scale(1, 1, -1);
        RenderSystem.applyModelViewMatrix();
        PoseStack stack = new PoseStack();
        stack.scale(scale, scale, scale);
        Quaternion zRot = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion yRot = Vector3f.YP.rotationDegrees(player.yBodyRot + yawOffset - 180);
        zRot.mul(yRot);
        stack.mulPose(zRot);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher renderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        yRot.conj();
        renderDispatcher.overrideCameraOrientation(yRot);
        renderDispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource buffer = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> renderDispatcher.render(player, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, stack, buffer, 15728880));
        buffer.endBatch();
        renderDispatcher.setRenderShadow(true);
        poseStack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
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
