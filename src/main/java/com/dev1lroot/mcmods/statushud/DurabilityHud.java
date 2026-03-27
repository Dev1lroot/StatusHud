package com.dev1lroot.mcmods.statushud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DurabilityHud
{
    public static void render(GuiGraphicsExtractor graphics, Minecraft minecraft, int screenWidth, int screenHeight)
    {
        // Эта панель очень херово смотриться если открыт чат, поэтому её нужно прятать
        if (minecraft.gui.getChat().isChatFocused()) return;

        // Собираем массив предметов для рендера
        List<ItemStack> slots = new ArrayList<>();

        ItemStack mainhand   = minecraft.player.getMainHandItem();
        ItemStack offhand    = HudUtils.getOffHandItem();
        ItemStack boots      = HudUtils.getBoots();
        ItemStack leggings   = HudUtils.getLeggings();
        ItemStack chestplate = HudUtils.getChestplate();
        ItemStack helmet     = HudUtils.getHelmet();

        if (Config.bEnableToolDurabilityIndicator.getAsBoolean() && !mainhand.isEmpty())    slots.add(mainhand);
        if (Config.bEnableShieldDurabilityIndicator.getAsBoolean() && !offhand.isEmpty())   slots.add(offhand);
        if (Config.bEnableArmorDurabilityIndicator.getAsBoolean() && !boots.isEmpty())      slots.add(boots);
        if (Config.bEnableArmorDurabilityIndicator.getAsBoolean() && !leggings.isEmpty())   slots.add(leggings);
        if (Config.bEnableArmorDurabilityIndicator.getAsBoolean() && !chestplate.isEmpty()) slots.add(chestplate);
        if (Config.bEnableArmorDurabilityIndicator.getAsBoolean() && !helmet.isEmpty())     slots.add(helmet);

        // Рендерим снизу вверх (так проще считать отступ)
        int iconX = 16;
        int bottomY = screenHeight - 30;

        for (int i = 0; i < slots.size(); i++)
        {
            int iconY = bottomY - i * 16;
            renderSlot(graphics, minecraft, slots.get(i), iconX, iconY);
        }
    }

    private static void renderSlot(GuiGraphicsExtractor graphics, Minecraft minecraft, ItemStack stack, int x, int y)
    {
        graphics.item(stack, x, y);
        graphics.itemDecorations(minecraft.font, stack, x, y);

        if (stack.isDamageableItem())
        {
            int current = stack.getMaxDamage() - stack.getDamageValue();
            int max     = stack.getMaxDamage();
            HudWriter writer = new HudWriter(graphics, minecraft.font, x + 20, y + 4, 10);
            writer.setColor(getDurabilityColor(current, max)).write(current + " / " + max);
        }
    }

    private static int getDurabilityColor(int current, int max)
    {
        float ratio = (float) current / max;
        if (ratio > 0.6f) return 0xFF55FF55;
        if (ratio > 0.3f) return 0xFFFFAA00;
        return 0xFFFF5555;
    }
}