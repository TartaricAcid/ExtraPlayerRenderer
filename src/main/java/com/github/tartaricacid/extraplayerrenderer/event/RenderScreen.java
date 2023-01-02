package com.github.tartaricacid.extraplayerrenderer.event;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT)
public class RenderScreen {
    @SubscribeEvent
    public static void onRenderScreen(RenderGameOverlayEvent.Pre event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) {
            return;
        }
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        GlStateManager.enableColorMaterial();
        GlStateManager.enableDepth();
        GlStateManager.pushMatrix();
        GlStateManager.translate(ConfigFileManager.getConfigPojo().getPosX(), ConfigFileManager.getConfigPojo().getPosY(), -500.0F);
        GlStateManager.scale(-ConfigFileManager.getConfigPojo().getScale(), ConfigFileManager.getConfigPojo().getScale(),
                ConfigFileManager.getConfigPojo().getScale());
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotate(player.rotationYaw + ConfigFileManager.getConfigPojo().getYawOffset(), 0.0F, 1.0F, 0.0F);

        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);

        RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
        rendermanager.setRenderShadow(false);
        rendermanager.doRenderEntity(player, 0.0D, 0.0D, 0.0D, 0, event.getPartialTicks(), true);
        rendermanager.setRenderShadow(true);

        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

    public static float getScale() {
        return ConfigFileManager.getConfigPojo().getScale();
    }

    public static void setScale(float scale) {
        ConfigFileManager.getConfigPojo().setScale(scale);
    }

    public static float getPosX() {
        return ConfigFileManager.getConfigPojo().getPosX();
    }

    public static void setPosX(float posX) {
        ConfigFileManager.getConfigPojo().setPosX(posX);
    }

    public static float getPosY() {
        return ConfigFileManager.getConfigPojo().getPosY();
    }

    public static void setPosY(float posY) {
        ConfigFileManager.getConfigPojo().setPosY(posY);
    }

    public static float getYawOffset() {
        return ConfigFileManager.getConfigPojo().getYawOffset();
    }

    public static void setYawOffset(float yawOffset) {
        ConfigFileManager.getConfigPojo().setYawOffset(yawOffset);
    }
}
