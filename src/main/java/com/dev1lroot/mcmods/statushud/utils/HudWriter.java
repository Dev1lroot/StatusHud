package com.dev1lroot.mcmods.statushud.utils;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;

public class HudWriter
{
    private final GuiGraphicsExtractor graphics;
    private final Font font;
    private final int startX;
    private final int lineHeight;

    private int x;
    private int y;
    private int currentColor;

    public HudWriter(GuiGraphicsExtractor graphics, Font font, int x, int y, int lineHeight)
    {
        this.graphics = graphics;
        this.font = font;
        this.startX = x;
        this.x = x;
        this.y = y;
        this.lineHeight = lineHeight;
        this.currentColor = ARGB.opaque(0xFFFFFF);
    }

    public HudWriter setColor(int color)
    {
        this.currentColor = color;
        return this;
    }

    public HudWriter write(String text)
    {
        if (text == null || text.isEmpty())
        {
            return this;
        }

        // Если в строке есть \n, разбиваем и рекурсивно пишем части
        if (text.contains("\n"))
        {
            String[] lines = text.split("\n", -1);
            for (int i = 0; i < lines.length; i++)
            {
                write(lines[i]);
                if (i < lines.length - 1)
                {
                    newLine();
                }
            }
            return this;
        }

        graphics.text(font, text, x, y, currentColor);

        // Сдвигаем X на ширину отрисованного текста для следующей записи в ту же строку
        this.x += font.width(text);

        return this;
    }

    public HudWriter newLine()
    {
        this.x = startX;
        this.y += lineHeight;
        return this;
    }
}