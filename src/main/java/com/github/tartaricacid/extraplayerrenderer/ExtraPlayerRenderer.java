package com.github.tartaricacid.extraplayerrenderer;

import com.github.tartaricacid.extraplayerrenderer.config.ConfigFileManager;
import net.minecraftforge.fml.common.Mod;

@Mod(ExtraPlayerRenderer.MOD_ID)
public class ExtraPlayerRenderer {
    public static final String MOD_ID = "extraplayerrenderer";

    public ExtraPlayerRenderer() {
        ConfigFileManager.readConfigFile();
    }
}
