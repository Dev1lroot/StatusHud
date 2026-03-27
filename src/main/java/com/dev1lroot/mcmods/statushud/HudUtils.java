package com.dev1lroot.mcmods.statushud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

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
     * Возвращает название предмета в активной руке.
     * Если рука пуста, возвращает пустую строку.
     */
    public static String getMainHandItemName() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return "";
        }

        ItemStack stack = player.getMainHandItem();

        if (stack.isEmpty()) {
            return "";
        }

        return stack.getHoverName().getString();
    }

    /**
     * Возвращает оставшуюся прочность предмета в активной руке.
     * Если предмет не ломается или рука пуста, возвращает -1.
     */
    public static int getMainHandItemDurability() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return -1;
        }

        ItemStack stack = player.getMainHandItem();

        // Проверяем, пуст ли стек и может ли предмет вообще тратить прочность
        if (stack.isEmpty() || !stack.isDamageableItem()) {
            return -1;
        }

        // Вычисляем текущую прочность: Максимум - Текущий износ
        return stack.getMaxDamage() - stack.getDamageValue();
    }

    /**
     * Возвращает полную прочность предмета в активной руке.
     * Если предмет не ломается или рука пуста, возвращает -1.
     */
    public static int getMainHandItemMaxDurability() {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) {
            return -1;
        }

        ItemStack stack = player.getMainHandItem();

        // Проверяем, пуст ли стек и может ли предмет вообще тратить прочность
        if (stack.isEmpty() || !stack.isDamageableItem()) {
            return -1;
        }

        return stack.getMaxDamage();
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
    public static String getFormattedTime(Minecraft mc)
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
     * Проверяет смотрим ли мы на сущность
     */
    public static Boolean areWeTargetingAnEntity(Minecraft mc)
    {
        return (mc.hitResult instanceof EntityHitResult entityHit);
    }

    /**
     * Получает имя сущности
     */
    public static String getTargetEntityName(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return "Unknown";
        Entity entity = entityHit.getEntity();
        return entity.getName().getString();
    }

    /**
     * Получает здоровье сущности
     */
    public static float getTargetEntityHealthValue(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return 0;
        Entity entity = entityHit.getEntity();
        if (!(entity instanceof LivingEntity living)) return 0;
        return living.getHealth();
    }

    /**
     * Получает максимальное здоровье сущности
     */
    public static float getTargetEntityMaxHealthValue(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return 0;
        Entity entity = entityHit.getEntity();
        if (!(entity instanceof LivingEntity living)) return 0;
        return living.getMaxHealth();
    }

    /**
     * Получает сущность на которую мы смотрим (именно living)
     */
    public static LivingEntity getTargetEntity(Minecraft mc)
    {
        if (!(mc.hitResult instanceof EntityHitResult entityHit)) return null;
        Entity entity = entityHit.getEntity();
        if (!(entity instanceof LivingEntity living)) return null;
        return living;
    }

    /**
     * Получаем броню и щит
     */
    // Броня через getItemBySlot
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
            return "";
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
     * Получает игровые дни
     */
    public static String getGameDays(Minecraft mc)
    {
        //TODO: FIX
        if (mc.level == null)
        {
            return "0";
        }

        // Добавляем 6000 тиков, чтобы "сдвинуть" начало отсчета с 06:00 на 00:00.
        // Теперь деление на 24000 даст смену дня ровно в полночь.
        long totalTicksWithOffset = mc.level.getGameTime() + 6000L;

        return "" + (totalTicksWithOffset / 24000L);
    }
}