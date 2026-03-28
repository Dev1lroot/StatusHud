package com.dev1lroot.mcmods.statushud.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class HudUtils
{
    /**
     * Возвращает координаты игрока в мире в виде строки
     */
    public static String getPlayerPos(LocalPlayer player)
    {
        return String.format("%d / %d / %d",
                (int) Math.floor(player.getX()),
                (int) Math.floor(player.getY()),
                (int) Math.floor(player.getZ()));
    }

    /**
     * Возвращает координаты игрока в чанке в виде строки
     */
    public static String getPlayerChunkPos(LocalPlayer player)
    {
        return String.format("%d / %d / %d",
                (int) Math.floor(player.getX()) & 15,
                (int) Math.floor(player.getY()) & 15,
                (int) Math.floor(player.getZ()) & 15);
    }

    /**
     * Возвращает текущее майнкрафтовское время в 24-х часовом формате
     */
    public static String getFormattedTime24(Minecraft mc)
    {
        // FAILSAFE
        if (mc.level == null) return "00:00";

        // Игровой цикл: 24000 тиков = 24 часа. 1000 тиков = 1 час.
        // Смещение +6000 тиков, так как время 0 в MC — это 06:00.
        long time = (mc.level.getOverworldClockTime() + 6000L) % 24000L;
        long hours = time / 1000L;

        // Минуты: остаток от часа (1000 тиков), переведенный в 60-минутный формат.
        // (ticks % 1000) * 60 / 1000
        long minutes = (time % 1000L) * 60L / 1000L;

        return String.format("%02d:%02d", hours, minutes);
    }

    /**
     * Возвращает текущее майнкрафтовское время в 12-ти часовом формате
     */
    public static String getFormattedTime12(Minecraft mc)
    {
        String suffix = "AM";

        // FAILSAFE
        if (mc.level == null) return "00:00 " + suffix;

        // Игровой цикл: 24000 тиков = 24 часа. 1000 тиков = 1 час.
        // Смещение +6000 тиков, так как время 0 в MC — это 06:00.
        long time = (mc.level.getOverworldClockTime() + 6000L) % 24000L;
        long hours = time / 1000L;

        // Минуты: остаток от часа (1000 тиков), переведенный в 60-минутный формат.
        // (ticks % 1000) * 60 / 1000
        long minutes = (time % 1000L) * 60L / 1000L;

        // Я вообще не понимаю смысла от таких часов, но их чаще всего используют в странах целевой аудитории,
        // поэтому придется добавить этот костыль,
        // надеюсь никогда не придется добавлять мили и фаренгейт в игру
        if(hours > 12)
        {
            hours = hours - 12;
            suffix = "PM";
        }
        if(hours == 0)
        {
            hours = 12;
            suffix = "PM";
        }

        return String.format("%02d:%02d", hours, minutes) + " " + suffix;
    }

    public static Entity raycastTargetEntity(Minecraft mc, double range)
    {
        if (mc.player == null || mc.level == null) return null;

        Entity camera = mc.getCameraEntity();
        if (camera == null) return null;

        Vec3 eyePos = camera.getEyePosition(1.0F);
        Vec3 lookVec = camera.getViewVector(1.0F);
        Vec3 reachVec = eyePos.add(lookVec.scale(range));

        AABB searchBox = camera.getBoundingBox()
                .expandTowards(lookVec.scale(range))
                .inflate(1.0D);

        EntityHitResult result = ProjectileUtil.getEntityHitResult(
                mc.level,
                camera,
                eyePos,
                reachVec,
                searchBox,
                entity -> !entity.isSpectator() && entity.isPickable() && entity != camera,
                0.3F
        );

        return result != null ? result.getEntity() : null;
    }

    /**
     * Получаем броню и щит
     */
    public static ItemStack getHelmet() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return ItemStack.EMPTY;
        return mc.player.getItemBySlot(EquipmentSlot.HEAD);
    }

    public static ItemStack getChestplate() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return ItemStack.EMPTY;
        return mc.player.getItemBySlot(EquipmentSlot.CHEST);
    }

    public static ItemStack getLeggings() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return ItemStack.EMPTY;
        return mc.player.getItemBySlot(EquipmentSlot.LEGS);
    }

    public static ItemStack getBoots() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return ItemStack.EMPTY;
        return mc.player.getItemBySlot(EquipmentSlot.FEET);
    }

    public static ItemStack getOffHandItem() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return ItemStack.EMPTY;
        return mc.player.getItemBySlot(EquipmentSlot.OFFHAND);
    }
    public static ItemStack getMainHandItem() {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return ItemStack.EMPTY;
        return mc.player.getItemBySlot(EquipmentSlot.MAINHAND);
    }

    /**
     * Получает мировые координаты точки куда смотрит игрок (когда-нибудь вставим куда-то)
     */
    public static String getTargetPos(Minecraft mc)
    {
        if (!(mc.hitResult instanceof BlockHitResult blockHit))
        {
            return "Target: ---";
        }

        BlockPos pos = blockHit.getBlockPos();
        return String.format("%d / %d / %d", pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Получает координаты точки в чанке куда смотрит игрок
     */
    public static String getTargetChunkPos(Minecraft mc)
    {
        if (!(mc.hitResult instanceof BlockHitResult blockHit))
        {
            return "~ / ~ / ~";
        }

        BlockPos pos = blockHit.getBlockPos();
        return String.format("%d / %d / %d", pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
    }

    /**
     * Получает компасное наименование стороны куда смотрит игрок
     */
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

    /**
     * Returns total real-time play time in format: Xh Ym Zs.
     *
     * - Hides hours if 0
     * - Hides minutes if 0
     * - Always shows seconds
     *
     * Uses total world time (ticks). 20 ticks = 1 second.
     * Returns "0s" if world is not loaded.
     */
    public static String getPlayTime(Minecraft mc)
    {
        if (mc.level == null)
        {
            return "0s";
        }

        long ticks = mc.level.getGameTime();
        long totalSeconds = ticks / 20;

        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long seconds = totalSeconds % 60;

        StringBuilder result = new StringBuilder();

        if (hours > 0)
        {
            result.append(hours).append("h ");
        }

        if (minutes > 0)
        {
            result.append(minutes).append("m ");
        }

        // секунды показываем всегда
        result.append(seconds).append("s");

        return result.toString();
    }
}