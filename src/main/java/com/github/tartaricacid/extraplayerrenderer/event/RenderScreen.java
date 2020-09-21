package com.github.tartaricacid.extraplayerrenderer.event;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
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
    public static void onRenderScreen(RenderGameOverlayEvent event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity player = mc.player;

        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translatef(posX, posY, -500.0F);
        GlStateManager.scalef(-scale, scale, scale);
        GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotatef(player.rotationYaw + yawOffset, 0.0F, 1.0F, 0.0F);

        GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef(-135.0F, 0.0F, 1.0F, 0.0F);

        EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(player, 0.0D, 0.0D, 0.0D, 0, event.getPartialTicks(), true);
        rendermanager.setRenderShadow(true);

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
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
