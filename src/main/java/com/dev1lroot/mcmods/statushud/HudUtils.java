package com.dev1lroot.mcmods.statushud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;

public class HudUtils
{
    public static String getPlayerPos(LocalPlayer player)
    {
        return String.format("%d / %d / %d",
                (int) Math.floor(player.getX()),
                (int) Math.floor(player.getY()),
                (int) Math.floor(player.getZ()));
    }

    public static String getPlayerChunkPos(LocalPlayer player)
    {
        return String.format("%d / %d / %d",
                (int) Math.floor(player.getX()) & 15,
                (int) Math.floor(player.getY()) & 15,
                (int) Math.floor(player.getZ()) & 15);
    }

    public static String getFormattedTime(Minecraft mc)
    {
        // FAILSAFE
        if (mc.level == null) return "00:00";

        // Игровой цикл: 24000 тиков = 24 часа. 1000 тиков = 1 час.
        // Смещение +6000 тиков, так как время 0 в MC — это 06:00.
        long time = (mc.level.getGameTime() + 6000L) % 24000L;
        long hours = time / 1000L;

        // Минуты: остаток от часа (1000 тиков), переведенный в 60-минутный формат.
        // (ticks % 1000) * 60 / 1000
        long minutes = (time % 1000L) * 60L / 1000L;

        return String.format("%02d:%02d", hours, minutes);
    }

    public static Boolean areWeTargetingAnEntity(Minecraft mc)
    {
        return (mc.hitResult instanceof EntityHitResult entityHit);
    }

    public static String getTargetEntityName(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return "Unknown";
        Entity entity = entityHit.getEntity();
        return entity.getName().getString();
    }

    public static float getTargetEntityHealthValue(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return 0;
        Entity entity = entityHit.getEntity();
        if (!(entity instanceof LivingEntity living)) return 0;
        return living.getHealth();
    }
    public static LivingEntity getTargetEntity(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return null;
        Entity entity = entityHit.getEntity();
        if (!(entity instanceof LivingEntity living)) return null;
        return living;
    }
    public static float getTargetEntityMaxHealthValue(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return 0;
        Entity entity = entityHit.getEntity();
        if (!(entity instanceof LivingEntity living)) return 0;
        return living.getMaxHealth();
    }

    public static String getTargetEntityHealth(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return "";

        Entity entity = entityHit.getEntity();

        if (!(entity instanceof LivingEntity living)) return entity.getName().getString();

        String name = living.getName().getString();
        float health = living.getHealth();
        float maxHealth = living.getMaxHealth();

        // Формат: "15.0 / 20.0"
        return String.format("%.1f / %.1f ❤", health, maxHealth);
    }

    public static String getTargetPos(Minecraft mc)
    {
        if (!(mc.hitResult instanceof BlockHitResult blockHit))
        {
            return "Target: ---";
        }

        BlockPos pos = blockHit.getBlockPos();
        return String.format("%d / %d / %d", pos.getX(), pos.getY(), pos.getZ());
    }

    public static String getTargetChunkPos(Minecraft mc)
    {
        if (!(mc.hitResult instanceof BlockHitResult blockHit))
        {
            return "";
        }

        BlockPos pos = blockHit.getBlockPos();
        return String.format("%d / %d / %d", pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
    }

    public static String getFacing(LocalPlayer player)
    {
        float yaw = player.getYRot() % 360;
        if (yaw < 0)
        {
            yaw += 360;
        }

        if (yaw < 22.5 || yaw >= 337.5) return "Facing: S";
        if (yaw < 67.5) return "Facing: SW";
        if (yaw < 112.5) return "Facing: W";
        if (yaw < 157.5) return "Facing: NW";
        if (yaw < 202.5) return "Facing: N";
        if (yaw < 247.5) return "Facing: NE";
        if (yaw < 292.5) return "Facing: E";

        return "Facing: SE";
    }

    public static String getGameDays(Minecraft mc)
    {
        if (mc.level == null)
        {
            return "Day: 0";
        }

        return "Day: " + (mc.level.getGameTime() / 24000L);
    }
}