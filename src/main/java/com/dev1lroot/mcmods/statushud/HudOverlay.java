package com.dev1lroot.mcmods.statushud;

import com.dev1lroot.mcmods.statushud.indicators.DamageIndicator;
import com.dev1lroot.mcmods.statushud.indicators.StatusIndicator;
import com.dev1lroot.mcmods.statushud.indicators.DurabilityIndicator;
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

        StatusIndicator.render(graphics, minecraft, screenWidth, screenHeight);
        DamageIndicator.render(graphics, minecraft, screenWidth, screenHeight);
        DurabilityIndicator.render(graphics, minecraft, screenWidth, screenHeight);
    }
}