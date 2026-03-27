package com.dev1lroot.mcmods.statushud;

import com.dev1lroot.mcmods.statushud.indicators.DamageIndicator;
import com.dev1lroot.mcmods.statushud.indicators.DurabilityIndicator;
import com.dev1lroot.mcmods.statushud.utils.HudUtils;
import com.dev1lroot.mcmods.statushud.utils.HudWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
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

        if (minecraft.options.hideGui) return;

        LocalPlayer player = minecraft.player;
        if (player == null) return;

        boolean isDebugOpen = Minecraft.getInstance().gui.getDebugOverlay().showDebugScreen();

        if (isDebugOpen) return; // выключаем все если включено меню F3

        if(Config.bEnableDamageIndicator.getAsBoolean() && HudUtils.areWeTargetingAnEntity(minecraft))
        {
            DamageIndicator.render(graphics, minecraft, screenWidth, screenHeight);
        }
        else if(Config.bEnableTimeIndicator.getAsBoolean() || Config.bEnableDateIndicator.getAsBoolean())
        {
            int clock_offset_x = 10;
            int clock_offset_y = 10;

            // Если мы смотрим не на моба то просто будем отображать дату и время в игре
            HudWriter writer = new HudWriter(graphics, minecraft.font, clock_offset_x + 6, clock_offset_y + 6, 10);
            writer.setColor(0xFFFFFFFF);

            // Отображаем только включенные режимы
            if(Config.bEnableTimeIndicator.getAsBoolean()) writer.write(HudUtils.getFormattedTime(minecraft) + " "); // CRUTCH
            if(Config.bEnableDateIndicator.getAsBoolean()) writer.write( "Day: " + HudUtils.getGameDays(minecraft) + " ");
            // Позиция цели отдельно, под всем остальным
            if (Config.bEnableTargetChunkPositionIndicator.getAsBoolean())
            {
                String pos = HudUtils.getTargetChunkPos(minecraft);
                if (!pos.isEmpty()) writer.setColor(0xFFFFFF00).write("[ " + pos + " ]");
            }
        }
        if(Config.bEnableDurabilityIndicator.getAsBoolean()) // Панель инструмента
        {
            DurabilityIndicator.render(graphics, minecraft, screenWidth, screenHeight);
        }
    }
}