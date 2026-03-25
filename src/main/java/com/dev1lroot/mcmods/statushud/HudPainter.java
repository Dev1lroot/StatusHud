package com.dev1lroot.mcmods.statushud;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class HudPainter
{
    private final GuiGraphicsExtractor graphics;
    private int currentColor;

    public HudPainter(GuiGraphicsExtractor graphics)
    {
        this.graphics = graphics;
        this.currentColor = ARGB.opaque(0xFFFFFF);
    }

    public HudPainter setColor(int color)
    {
        this.currentColor = color;
        return this;
    }

    // Твой новый метод отрисовки моба для 26.1
    public HudPainter drawEntity(int x1, int y1, int x2, int y2, LivingEntity entity, float scale)
    {
        if (entity == null)
        {
            return this;
        }

        // 1. Извлекаем состояние рендера (как в твоем декомпиляте)
        EntityRenderDispatcher dispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> renderer = dispatcher.getRenderer(entity);
        EntityRenderState state = renderer.createRenderState(entity, 1.0F);

        // Убираем тени, чтобы в HUD выглядело чисто
        state.shadowPieces.clear();

        // 2. Настраиваем вращение (чтобы моб стоял прямо и смотрел на нас)
        Quaternionf rotation = (new Quaternionf()).rotateZ((float)Math.PI);

        // Если это живое существо, подправим углы
        if (state instanceof LivingEntityRenderState livingState)
        {
            livingState.bodyRot = 180.0F;
            livingState.yRot = 0.0F;
            livingState.xRot = 0.0F;
        }

        // 3. Позиционирование
        // В 26.1 graphics.entity требует рамки (x0, y0, x1, y1)

        Vector3f translation = new Vector3f(0.0F, state.boundingBoxHeight / 2.0F, 0.0F);

        // Самый важный вызов в 26.1
        graphics.entity(state, scale, translation, rotation, null, x1, y1, x2, y2);

        return this;
    }

    public HudPainter drawInsetFrame(int x1, int y1, int x2, int y2, int size)
    {
        graphics.fill(x1,y1,x2,y1+size, this.currentColor);
        graphics.fill(x1,y2-size,x2,y2, this.currentColor);
        graphics.fill(x1,y1,x1+size,y2, this.currentColor);
        graphics.fill(x2-size,y1,x2,y2, this.currentColor);
        return this;
    }

    public HudPainter fill(int x1, int y1, int x2, int y2)
    {
        graphics.fill(x1,y1,x2,y2, this.currentColor);
        return this;
    }

    public HudPainter drawBar(int x1, int y1, int x2, int y2, float percentage)
    {
        int barWidth = x2 - x1;
        int fillable = Math.round(barWidth * percentage);
        this.fill(x1,y1,x1 + fillable,y2);
        return this;
    }
}