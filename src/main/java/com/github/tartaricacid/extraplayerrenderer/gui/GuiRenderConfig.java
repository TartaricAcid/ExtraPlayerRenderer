package com.github.tartaricacid.extraplayerrenderer.gui;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import com.github.tartaricacid.extraplayerrenderer.config.ConfigPOJO;
import com.github.tartaricacid.extraplayerrenderer.event.RenderScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class GuiRenderConfig extends GuiScreen {
    private static final float SCALE_MAX = 360f;
    private static final float SCALE_MIN = 8f;

    @Override
    public void render(int mouseX, int mouseY, float particleTick) {
        super.render(mouseX, mouseY, particleTick);
        int middleX = width / 2;
        int middleY = height / 2;
        drawGradientRect(middleX - 60, middleY - 45, middleX + 70, middleY + 45, 0xcc111111, 0xcc111111);
        String text = I18n.format("gui.extra_player_renderer.config.text");
        fontRenderer.drawSplitString(text, middleX - 50, middleY - 35, 140, 0xffffffff);
    }

    @Override
    public boolean mouseScrolled(double scroll) {
        if (scroll != 0) {
            changeScaleValue((float) scroll * 3f);
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
        boolean result = false;
        if (button == GLFW.GLFW_MOUSE_BUTTON_LEFT) {
            RenderScreen.setYawOffset((float) deltaX + RenderScreen.getYawOffset());
            result = true;
        }

        if (button == GLFW.GLFW_MOUSE_BUTTON_RIGHT) {
            RenderScreen.setPosX((float) deltaX + RenderScreen.getPosX());
            RenderScreen.setPosY((float) deltaY + RenderScreen.getPosY());
            result = true;
        }
        return result;
    }

    @Override
    public boolean charTyped(char typedChar, int keyCode) {
        if (Character.toLowerCase(typedChar) == 'r' && isAltKeyDown()) {
            ConfigPOJO pojo = ConfigPOJO.getInstance();
            RenderScreen.setPosX(pojo.getPosX());
            RenderScreen.setPosY(pojo.getPosY());
            RenderScreen.setScale(pojo.getScale());
            RenderScreen.setYawOffset(pojo.getYawOffset());
        }
        return super.charTyped(typedChar, keyCode);
    }

    /**
     * 更改 Scale 的数值
     *
     * @param amount 增加或者减少的数值
     */
    private void changeScaleValue(float amount) {
        float tmp = RenderScreen.getScale() + amount * RenderScreen.getScale() / 80.0f;
        RenderScreen.setScale(MathHelper.clamp(tmp, SCALE_MIN, SCALE_MAX));
    }

    @Override
    public void onGuiClosed() {
        ConfigFileManager.writeConfigFile(
                new ConfigPOJO(RenderScreen.getPosX(),
                        RenderScreen.getPosY(),
                        RenderScreen.getScale(),
                        RenderScreen.getYawOffset()));
    }
}
