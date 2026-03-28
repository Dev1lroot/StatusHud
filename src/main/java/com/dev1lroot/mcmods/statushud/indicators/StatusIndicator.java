package com.dev1lroot.mcmods.statushud.indicators;

import com.dev1lroot.mcmods.statushud.Config;
import com.dev1lroot.mcmods.statushud.utils.HudStringFormatter;
import com.dev1lroot.mcmods.statushud.utils.HudUtils;
import com.dev1lroot.mcmods.statushud.utils.HudWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class StatusIndicator
{
    public static void render(GuiGraphicsExtractor graphics, Minecraft minecraft, int screenWidth, int screenHeight)
    {
        if(!Config.bEnableStatusIndicator.getAsBoolean()) return;

        int status_indicator_offset_x = 10;
        int status_indicator_offset_y = 10;

        // Если мы смотрим не на моба то просто будем отображать дату и время в игре
        HudWriter writer = new HudWriter(graphics, minecraft.font, status_indicator_offset_x + 6, status_indicator_offset_y + 6, 10);
        writer.setColor(0xFFFFFFFF);

        // Получаем никнейм игрока
        String playername = (Minecraft.getInstance().player != null)
                ? Minecraft.getInstance().player.getName().getString()
                : "Undefined";

        // Получаем и заполняем нашу статусную строку данными по паттерну из конфига
        String statusString = new HudStringFormatter(Config.sStatusIndicatorFormat.get())
                .setVal("sGameTime24", HudUtils.getFormattedTime24(minecraft))
                .setVal("sGameTime12", HudUtils.getFormattedTime12(minecraft))
                .setVal("sTargetsChunkPosition", HudUtils.getTargetChunkPos(minecraft))
                .setVal("sPlayerName",playername)
                .setVal("sPlayTime",HudUtils.getPlayTime(minecraft))
                .output();

        writer.setColor(0xFFFFFFFF).write(statusString);
    }
}