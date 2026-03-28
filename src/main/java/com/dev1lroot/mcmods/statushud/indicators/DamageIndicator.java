package com.dev1lroot.mcmods.statushud.indicators;

import com.dev1lroot.mcmods.statushud.Config;
import com.dev1lroot.mcmods.statushud.utils.HudPainter;
import com.dev1lroot.mcmods.statushud.utils.HudUtils;
import com.dev1lroot.mcmods.statushud.utils.HudWriter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;

public class DamageIndicator
{
    private static float getEntityScale(LivingEntity entity)
    {
        // Я надеюсь это не костыль, в противном случае скиньте как правильно сделать чтобы моб всегда влезал целиком в иконку
        EntityType<?> type = entity.getType();
        if(type.equals(EntityType.CREEPER)) return 25.0F;
        if(type.equals(EntityType.ZOMBIE) && entity.isBaby()) return 40.0F;
        if(type.equals(EntityType.ZOMBIE_VILLAGER) && entity.isBaby()) return 40.0F;
        if(type.equals(EntityType.TROPICAL_FISH)) return 50.0F;
        if(type.equals(EntityType.SALMON)) return 30.0F;
        if(type.equals(EntityType.SILVERFISH)) return 50.0F;
        if(type.equals(EntityType.ENDERMAN)) return 16.0F;

        if(entity.isBaby()) return 30.0F;
        return 20.0F;
    }
    public static void render(GuiGraphicsExtractor graphics, Minecraft minecraft, int screenWidth, int screenHeight)
    {
        // Если выключен индикатор то скипаем весь рендер
        if(!Config.bEnableDamageIndicator.getAsBoolean()) return;

        // Получаем сущность своим рейкастером, потому что майнкрафтовский выдает сущность только в радиусе поражения игроком
        Entity raycastedEntity = HudUtils.raycastTargetEntity(minecraft,Config.fDamageIndicatorRaycastDistance.get());

        // Если сущность не найдена то скипаем рендер
        if(raycastedEntity == null) return;

        // Если сущность не живая то скипаем рендер, мы у нее тупо здоровье не сможем получить
        if (!(raycastedEntity instanceof LivingEntity living)) return;

        // Это надо вынести в конфиг и вообще сделать возможность двигать и масштабировать в игре
        int damage_indicator_offset_x = 10;
        int damage_indicator_offset_y = 10;
        int damage_indicator_width = 200;
        int damage_indicator_height = 60;
        int damage_indicator_x2 = damage_indicator_offset_x + damage_indicator_width;
        int damage_indicator_y2 = damage_indicator_offset_y + damage_indicator_height;

        // Получаем имя, максимальное и обычное здоровья
        String name = raycastedEntity.getName().getString();
        float currentHealth = living.getHealth();
        float maxHealth = living.getMaxHealth();
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

        // Получаем правильный размер для конкретного моба
        float scale = getEntityScale(living);

        // Рисуем иконку моба
        painter.drawEntity(
                5 + damage_indicator_offset_x,
                5 + damage_indicator_offset_y,
                55 + damage_indicator_offset_x,
                55 + damage_indicator_offset_y,
                living,
                scale);

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