package com.github.tartaricacid.extraplayerrenderer.input;

import com.github.tartaricacid.extraplayerrenderer.gui.GuiRenderConfig;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class OpenConfigKey {
    public static final KeyMapping OPEN_CONFIG_KEY = new KeyMapping("key.extra_player_renderer.open_config.desc",
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "key.category.extra_player_renderer");

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        if (OPEN_CONFIG_KEY.isDown()) {
            Minecraft.getInstance().setScreen(new GuiRenderConfig(Component.literal("config_gui")));
        }
    }
}
