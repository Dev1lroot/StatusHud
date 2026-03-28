package com.dev1lroot.mcmods.statushud;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue bEnableDamageIndicator = BUILDER
            .comment("Toggle the damage indicator on the HUD.")
            .define("bEnableDamageIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableDurabilityIndicator = BUILDER
            .comment("Toggle the display for all of the durability indicators all together")
            .define("bEnableDurabilityIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableArmorDurabilityIndicator = BUILDER
            .comment("Toggle the display for the currently equipped armor's remaining durability.")
            .define("bEnableArmorDurabilityIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableShieldDurabilityIndicator = BUILDER
            .comment("Toggle the display for the currently equipped shield's remaining durability.")
            .define("bEnableShieldDurabilityIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableToolDurabilityIndicator = BUILDER
            .comment("Toggle the display for the currently equipped tool's remaining durability.")
            .define("bEnableToolDurabilityIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableStatusIndicator = BUILDER
            .comment("Toggle the display for the upper line of information")
            .define("bEnableStatusIndicator", true);

    public static final ModConfigSpec.ConfigValue<Float> fDamageIndicatorRaycastDistance = BUILDER
            .comment("Describes the distance the damage indicator is able to retrieve entity's information, default: 30, vanilla: 3")
            .define("fDamageIndicatorRaycastDistance", 30.0F);

    public static final ModConfigSpec.ConfigValue<String> sStatusIndicatorFormat = BUILDER
            .comment("Allows you to set custom display format for the upper line of information",
                    "- currently supported features:",
                    "    \\n - new line",
                    "    &AARRGGBB; - color code",
                    "- currently supported variables:",
                    "    $sGameTime24 - in-game day time in 24 hours format",
                    "    $sGameTime12 - in-game day time in 12 hours format",
                    "    $sPlayerName - player's name",
                    "    $sPlayTime - real time played in the world in 1h 2m 3s format",
                    "    $sTargetsChunkPosition - position of the block you are looking at in chunk coordinates")
            .define("sStatusIndicatorFormat", "$sGameTime24 | &FF00FF00;$sPlayerName&FFFFFFFF; | $sPlayTime | &FFFFFF00;$sTargetsChunkPosition");

    public static final ModConfigSpec.ConfigValue<String> sDurabilityIndicatorFormat = BUILDER
            .comment("Allows you to set custom display format for armor, shield and tool durability",
                    "- currently supported variables:",
                    "    $iCurrentDurability - current durability of the item",
                    "    $iMaxDurability - maximum durability of the item",
                    "    $fCurrentDurabilityPercent - current durability in percents")
            .define("sDurabilityIndicatorFormat", "$iCurrentDurability/$iMaxDurability ($fCurrentDurabilityPercent%)");

    static final ModConfigSpec SPEC = BUILDER.build();
}
