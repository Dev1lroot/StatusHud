package com.dev1lroot.mcmods.statushud.indicators;

import com.dev1lroot.mcmods.statushud.utils.HudPainter;
import com.dev1lroot.mcmods.statushud.utils.HudUtils;
import com.dev1lroot.mcmods.statushud.utils.HudWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class DamageIndicator
{
    public static void render(GuiGraphicsExtractor graphics, Minecraft minecraft, int screenWidth, int screenHeight)
    {
        int damage_indicator_offset_x = 10;
        int damage_indicator_offset_y = 10;
        int damage_indicator_width = 200;
        int damage_indicator_height = 60;
        int damage_indicator_x2 = damage_indicator_offset_x + damage_indicator_width;
        int damage_indicator_y2 = damage_indicator_offset_y + damage_indicator_height;

        // Получаем имя, максимальное и обычное здоровья
        String name = HudUtils.getTargetEntityName(minecraft);
        float currentHealth = HudUtils.getTargetEntityHealthValue(minecraft);
        float maxHealth = HudUtils.getTargetEntityMaxHealthValue(minecraft);
        float healthPercentage = currentHealth / maxHealth;

        // Создаем драйвер
        HudWriter writer = new HudWriter(graphics, minecraft.font, damage_indicator_offset_x + 60, damage_indicator_offset_y + 6, 10);
        HudPainter painter = new HudPainter(graphics);

        // Делаем фон
        painter.setColor(0xFF160818).fill(
                damage_indicator_offset_x,
                damage_indicator_offset_y,
                damage_indicator_x2,
                damage_indicator_y2);

        // Делаем рамку
        painter.setColor(0xFF2E065F).drawInsetFrame(
                damage_indicator_offset_x+1,
                damage_indicator_offset_x+1,
                damage_indicator_x2 - 1,
                damage_indicator_y2 - 1,
                1);

        // Рисуем фон под иконкой моба
        painter.setColor(0xFF000000).fill(
                5 + damage_indicator_offset_x,
                5 + damage_indicator_offset_y,
                55 + damage_indicator_offset_x,
                55 + damage_indicator_offset_y);

        // Рисуем иконку моба
        painter.drawEntity(
                5 + damage_indicator_offset_x,
                5 + damage_indicator_offset_y,
                55 + damage_indicator_offset_x,
                55 + damage_indicator_offset_y,
                HudUtils.getTargetEntity(minecraft),
                25.0F);

        // Пишем имя
        writer.setColor(0xFFFFFFFF).write(name).newLine().newLine();

        // Рисуем полоску здоровья (фон)
        painter.setColor(0xFF000000).fill(
                60 + damage_indicator_offset_x,
                22 + damage_indicator_offset_y,
                damage_indicator_width + damage_indicator_offset_x - 5,
                37 + damage_indicator_offset_y);

        // Рисуем полоску здоровья (саму)
        painter.setColor(0xFFAF0000).drawBar(
                60 + damage_indicator_offset_x,
                22 + damage_indicator_offset_y,
                damage_indicator_width + damage_indicator_offset_x - 5,
                37 + damage_indicator_offset_y,
                healthPercentage);

        // Рисуем текст здоровья поверх полоски
        writer.setColor(0xFFFFFFFF).write(currentHealth + " / " + maxHealth).newLine();
    }
}