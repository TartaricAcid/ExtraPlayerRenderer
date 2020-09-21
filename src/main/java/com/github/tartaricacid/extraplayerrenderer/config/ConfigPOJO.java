package com.github.tartaricacid.extraplayerrenderer.config;

import com.google.gson.annotations.SerializedName;

public class ConfigPOJO {
    @SerializedName("pos_x")
    private float posX;
    @SerializedName("pos_Y")
    private float posY;
    @SerializedName("scale")
    private float scale;
    @SerializedName("yaw_offset")
    private float yawOffset;

    public ConfigPOJO(float posX, float posY, float scale, float yawOffset) {
        this.posX = posX;
        this.posY = posY;
        this.scale = scale;
        this.yawOffset = yawOffset;
    }

    public static ConfigPOJO getInstance() {
        return new ConfigPOJO(50, 320, 80, 5);
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getYawOffset() {
        return yawOffset;
    }

    public void setYawOffset(float yawOffset) {
        this.yawOffset = yawOffset;
    }
}
