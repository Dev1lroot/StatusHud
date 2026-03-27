package com.dev1lroot.mcmods.statushud;

import net.neoforged.neoforge.common.ModConfigSpec;

public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue bEnableDamageIndicator = BUILDER
            .comment("Toggle the damage indicator on the HUD.")
            .define("bEnableDamageIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableTimeIndicator = BUILDER
            .comment("Toggle the time display on the HUD.")
            .define("bEnableTimeIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableDateIndicator = BUILDER
            .comment("WORK IN PROGRESS: Toggle the date display on the HUD. May not function as expected.")
            .define("bEnableDateIndicator", false);

    public static final ModConfigSpec.BooleanValue bEnableToolDurabilityIndicator = BUILDER
            .comment("Toggle the display for the current tool's remaining durability.")
            .define("bEnableToolDurabilityIndicator", true);

    public static final ModConfigSpec.BooleanValue bEnableToolTargetPositionIndicator = BUILDER
            .comment("Toggle the display for the coordinates of the block you are looking at.")
            .define("bEnableToolTargetPositionIndicator", true);

    public static final ModConfigSpec.ConfigValue<String> sTimeIndicatorMode = BUILDER
            .comment("WORK IN PROGRESS: Select the source for time display.",
                    "Allowed values: 'real' (system clock), 'game' (in-game world time)",
                    "May not function as expected.")
            .define("sTimeIndicatorMode", "game");

    public static final ModConfigSpec.ConfigValue<String> sDateIndicatorMode = BUILDER
            .comment("WORK IN PROGRESS: Select the source for date display.",
                    "Allowed values: 'real' (current calendar date), 'game' (in-game days passed)",
                    "May not function as expected.")
            .define("sDateIndicatorMode", "game");

    static final ModConfigSpec SPEC = BUILDER.build();
}
