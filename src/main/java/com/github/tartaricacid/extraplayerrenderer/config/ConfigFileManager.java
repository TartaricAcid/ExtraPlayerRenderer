package com.github.tartaricacid.extraplayerrenderer.config;

import com.google.gson.Gson;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public final class ConfigFileManager {
    private static final File CONFIG_FILE = Minecraft.getMinecraft().mcDataDir.toPath()
            .resolve("config").resolve("extra_player_renderer.json").toFile();
    private static final Gson GSON = new Gson();
    private static ConfigPOJO CONFIG_POJO;

    public static void readConfigFile() {
        if (!CONFIG_FILE.isFile()) {
            try {
                Files.createFile(CONFIG_FILE.toPath());
                CONFIG_POJO = ConfigPOJO.getInstance();
                FileUtils.write(CONFIG_FILE, GSON.toJson(CONFIG_POJO), StandardCharsets.UTF_8);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            CONFIG_POJO = GSON.fromJson(new FileReader(CONFIG_FILE), ConfigPOJO.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void writeConfigFile(ConfigPOJO configPOJO) {
        if (!CONFIG_FILE.isFile()) {
            try {
                Files.createFile(CONFIG_FILE.toPath());
                FileUtils.write(CONFIG_FILE, GSON.toJson(configPOJO), StandardCharsets.UTF_8);
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileUtils.write(CONFIG_FILE, GSON.toJson(configPOJO), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ConfigPOJO getConfigPojo() {
        return CONFIG_POJO;
    }
}
