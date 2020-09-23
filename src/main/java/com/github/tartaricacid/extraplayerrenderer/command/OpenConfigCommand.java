package com.github.tartaricacid.extraplayerrenderer.command;

import com.github.tartaricacid.extraplayerrenderer.gui.GuiRenderConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;

public class OpenConfigCommand extends CommandBase {
    private static final String NAME = "epr";
    private static final String USAGE = "/epr";

    @Override
    public String getCommandName() {
        return NAME;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return USAGE;
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 0;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        new Thread(() -> {
            try {
                Minecraft.getMinecraft().func_152344_a(() -> Minecraft.getMinecraft().displayGuiScreen(new GuiRenderConfig()));
            } catch (Throwable e) {
            }
        }, "Open-Config-Gui-Thread").start();
    }
}
