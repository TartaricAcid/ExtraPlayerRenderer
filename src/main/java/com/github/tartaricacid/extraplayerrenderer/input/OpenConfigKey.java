package com.github.tartaricacid.extraplayerrenderer.input;

import com.github.tartaricacid.extraplayerrenderer.gui.GuiRenderConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class OpenConfigKey {
    public static final KeyBinding OPEN_CONFIG_KEY = new KeyBinding("key.extra_player_renderer.open_config.desc",
            KeyConflictContext.IN_GAME,
            KeyModifier.ALT,
            InputMappings.Type.KEYSYM,
            GLFW.GLFW_KEY_P,
            "key.category.extra_player_renderer");

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        if (OPEN_CONFIG_KEY.isPressed()) {
            Minecraft.getInstance().displayGuiScreen(new GuiRenderConfig());
        }
    }
}
