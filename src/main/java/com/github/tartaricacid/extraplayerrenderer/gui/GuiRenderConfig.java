package com.github.tartaricacid.extraplayerrenderer.gui;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import com.github.tartaricacid.extraplayerrenderer.config.ConfigPOJO;
import com.github.tartaricacid.extraplayerrenderer.event.RenderScreen;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class GuiRenderConfig extends GuiScreen {
    private static final float SCALE_MAX = 360f;
    private static final float SCALE_MIN = 8f;

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int middleX = width / 2;
        int middleY = height / 2;
        drawGradientRect(middleX - 60, middleY - 45, middleX + 70, middleY + 45, 0xcc111111, 0xcc111111);
        String text = I18n.format("gui.extra_player_renderer.config.text");
        fontRenderer.drawSplitString(text, middleX - 50, middleY - 35, 140, 0xffffffff);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        float mouseDX = Mouse.getDX() * width / (float) mc.displayWidth * 3.0f;
        float mouseDY = -Mouse.getDY() * height / (float) mc.displayHeight * 3.0f;

        // 鼠标滚轮改变大小
        if (Mouse.getEventDWheel() != 0) {
            changeScaleValue(Mouse.getEventDWheel() * 0.07f);
        }
        // 鼠标左键拖动旋转
        if (Mouse.isButtonDown(0)) {
            RenderScreen.setYawOffset(mouseDX + RenderScreen.getYawOffset());
        }
        // 鼠标右键移动位置
        if (Mouse.isButtonDown(1)) {
            RenderScreen.setPosX(mouseDX + RenderScreen.getPosX());
            RenderScreen.setPosY(mouseDY + RenderScreen.getPosY());
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        if (keyCode == Keyboard.KEY_R && isAltKeyDown()) {
            ConfigPOJO pojo = ConfigPOJO.getInstance();
            RenderScreen.setPosX(pojo.getPosX());
            RenderScreen.setPosY(pojo.getPosY());
            RenderScreen.setScale(pojo.getScale());
            RenderScreen.setYawOffset(pojo.getYawOffset());
        }
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
