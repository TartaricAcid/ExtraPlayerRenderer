package com.github.tartaricacid.extraplayerrenderer;

import com.github.tartaricacid.extraplayerrenderer.command.OpenConfigCommand;
import com.github.tartaricacid.extraplayerrenderer.event.RenderScreen;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import static com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager.readConfigFile;

@Mod(modid = ExtraPlayerRenderer.MOD_ID, name = ExtraPlayerRenderer.MOD_NAME,
        version = ExtraPlayerRenderer.VERSION, clientSideOnly = true)
public class ExtraPlayerRenderer {
    public static final String MOD_ID = "extraplayerrenderer";
    public static final String MOD_NAME = "Extra Player Renderer";
    public static final String VERSION = "@VERSION@";

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        ClientCommandHandler.instance.registerCommand(new OpenConfigCommand());
        readConfigFile();
        new RenderScreen();
    }
}
