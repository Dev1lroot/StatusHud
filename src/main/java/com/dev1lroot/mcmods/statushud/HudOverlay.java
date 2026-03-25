package com.dev1lroot.mcmods.statushud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.minecraft.client.player.LocalPlayer;

@EventBusSubscriber(modid = StatusHud.MODID, value = Dist.CLIENT)
public class HudOverlay
{
    @SubscribeEvent
    public static void onRegisterLayers(RegisterGuiLayersEvent event)
    {
        event.registerAbove(
                VanillaGuiLayers.PLAYER_HEALTH,
                Identifier.parse(StatusHud.MODID + ":main_hud"),
                HudOverlay::render
        );
    }

    private static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker)
    {
        Minecraft minecraft = Minecraft.getInstance();

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();

        if (minecraft.options.hideGui)
        {
            return;
        }

        LocalPlayer player = minecraft.player;
        if (player == null)
        {
            return;
        }

        boolean isDebugOpen = Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen();

        if (isDebugOpen) {
            return; // выключаем все если включено меню F3
        }

        if(HudUtils.areWeTargetingAnEntity(minecraft))
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
        else
        {
            //TODO: FIX
            //int clock_offset_x = 10;
            //int clock_offset_y = 10;

            // Если мы смотрим не на моба то просто будем отображать дату и время в игре
            //HudWriter writer = new HudWriter(graphics, minecraft.font, clock_offset_x + 6, clock_offset_y + 6, 10);
            //writer.setColor(0xFFFFFFFF).write(HudUtils.getFormattedTime(minecraft) + " | Day: " + HudUtils.getGameDays(minecraft));
        }
        if(HudUtils.getMainHandItemDurability() != -1) // Панель инструмента
        {
            int tool_menu_offset_x = 10;
            int tool_menu_offset_y = screenHeight - 30;

            // Если мы смотрим не на моба то просто будем отображать дату и время в игре
            HudWriter writer = new HudWriter(graphics, minecraft.font, tool_menu_offset_x + 6, tool_menu_offset_y + 6, 10);
            writer
                    .setColor(0xFFFFFFFF)
                    .write(HudUtils.getMainHandItemName())
                    .newLine()
                    .write(String.valueOf(HudUtils.getMainHandItemDurability()))
                    .write(" | ")
                    .setColor(0xFFFFFF00)
                    .write("[ " + HudUtils.getTargetChunkPos(minecraft) + " ]");
        }

        // Используем цепочку вызовов
//        writer.color(0xFF00FF00).write("PLAYER").newLine();
//        writer.color(0xFF777777).write(" - WORLD POSITION:").color(0xFFFFFFFF).write(HudUtils.getPlayerPos(player)).newLine();
//        writer.color(0xFF777777).write(" - CHUNK POSITION:").color(0xFFFFFFFF).write(HudUtils.getPlayerChunkPos(player)).newLine();
//        writer.newLine();
//        writer.color(0xFF00FF00).write("TARGET").newLine();
//        writer.color(0xFF777777).write(" - WORLD POSITION:").color(0xFFFFFFFF).write(HudUtils.getTargetPos(minecraft)).newLine();
//        writer.color(0xFF777777).write(" - CHUNK POSITION:").color(0xFFFFFFFF).write(HudUtils.getTargetChunkPos(minecraft)).newLine();
//        writer.color(0xFF777777).write(" - INFO:").color(0xFFFFFFFF).write(HudUtils.getTargetEntityHealth(minecraft)).newLine();
//        writer.newLine();
//        writer.color(0xFF00FF00).write("WORLD").newLine();
//        writer.color(0xFF777777).write(" - TIME:").color(0xFFFFFFFF).write(HudUtils.getFormattedTime(minecraft)).newLine();
//        writer.color(0xFF777777).write(" - DATE:").color(0xFFFFFFFF).write(HudUtils.getGameDays(minecraft)).newLine();

//        // Пример использования \n внутри строки
//        writer.write(HudUtils.getTargetPos(minecraft) + "\n" + HudUtils.getTargetChunkPos(minecraft)).newLine();
//
//        writer.write(HudUtils.getFacing(player)).newLine();
//        writer.write(HudUtils.getFormattedTime(minecraft)).newLine();
//        writer.write(HudUtils.getGameDays(minecraft)).newLine();
//
//        // Пример смены цвета для здоровья
//        writer.color(ARGB.opaque(0xFF5555))
//                .write(HudUtils.getTargetEntityHealth(minecraft))
//                .newLine();
    }
}