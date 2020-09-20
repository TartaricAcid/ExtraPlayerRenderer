package com.github.tartaricacid.extraplayerrenderer.gui;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import com.github.tartaricacid.extraplayerrenderer.config.ConfigPOJO;
import com.github.tartaricacid.extraplayerrenderer.event.RenderScreen;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import static net.minecraftforge.fml.client.gui.GuiUtils.drawGradientRect;

@OnlyIn(Dist.CLIENT)
public class GuiRenderConfig extends Screen {
    private static final float SCALE_MAX = 360f;
    private static final float SCALE_MIN = 8f;

    public GuiRenderConfig(ITextComponent titleIn) {
        super(titleIn);
    }

    @Override
    public void func_230430_a_(MatrixStack matrixStack, int mouseX, int mouseY, float particleTick) {
        super.func_230430_a_(matrixStack, mouseX, mouseY, particleTick);
        int middleX = field_230708_k_ / 2;
        int middleY = field_230709_l_ / 2;
        drawGradientRect(matrixStack.getLast().getMatrix(), 0, middleX - 60, middleY - 45, middleX + 70, middleY + 45, 0xcc111111, 0xcc111111);
        String text = I18n.format("gui.extra_player_renderer.config.text");
        // field_230712_o_.drawSplitString(text, middleX - 50, middleY - 35, 140, 0xffffffff);
    }

    @Override
    public boolean func_231043_a_(double mouseX, double mouseY, double scroll) {
        if (scroll != 0) {
            changeScaleValue((float) scroll * 3f);
            return true;
        }
        return false;
    }

    @Override
    public boolean func_231045_a_(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
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
    public boolean func_231042_a_(char typedChar, int keyCode) {
        if (Character.toLowerCase(typedChar) == 'r' && func_231174_t_()) {
            ConfigPOJO pojo = ConfigPOJO.getInstance();
            RenderScreen.setPosX(pojo.getPosX());
            RenderScreen.setPosY(pojo.getPosY());
            RenderScreen.setScale(pojo.getScale());
            RenderScreen.setYawOffset(pojo.getYawOffset());
        }
        return super.func_231042_a_(typedChar, keyCode);
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
    public void func_231175_as__() {
        ConfigFileManager.writeConfigFile(
                new ConfigPOJO(RenderScreen.getPosX(),
                        RenderScreen.getPosY(),
                        RenderScreen.getScale(),
                        RenderScreen.getYawOffset()));
        super.func_231175_as__();
    }
}
