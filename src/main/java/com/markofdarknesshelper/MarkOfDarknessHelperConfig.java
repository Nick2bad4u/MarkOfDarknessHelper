package com.markofdarknesshelper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Alpha;

import java.awt.Color;

@ConfigGroup("com/markofdarknesshelper")
public interface MarkOfDarknessHelperConfig extends Config
{
    @ConfigItem(
            keyName = "onlyArceuus",
            name = "Only on Arceuus Spellbook",
            description = "Only display the reminder box when on the Arceuus spellbook.",
            position = 1
    )
    default boolean onlyArceuus() { return true; }

    @ConfigItem(
            keyName = "shouldNotify",
            name = "Notify when Mark of Darkness expires",
            description = "Sends a notification once Mark of Darkness needs to be recast.",
            position = 2
    )
    default boolean shouldNotify() { return true; }

    @ConfigItem(
            keyName = "earlyNotify",
            name = "Early Notification",
            description = "Start the reminder when 'Your Mark of Darkness is about to run out.' appears in chat.",
            position = 3
    )
    default boolean earlyNotify() { return false; }

    @ConfigItem(
            keyName = "overlayTimeoutSeconds",
            name = "Reminder Duration (seconds)",
            description = "How many seconds the reminder will flash after Mark of Darkness expires.",
            position = 4
    )
    default int overlayTimeoutSeconds() { return 180; }

    @Alpha
    @ConfigItem(
            keyName = "boxColor",
            name = "Box Color",
            description = "Color of the non-flashing reminder box.",
            position = 5
    )
    default Color boxColor() { return new Color(0, 0, 0, 100); }

    @ConfigItem(
            keyName = "shouldFlash",
            name = "Flash the Reminder Box",
            description = "Makes the reminder box flash.",
            position = 6
    )
    default boolean shouldFlash() { return true; }

    @Alpha
    @ConfigItem(
            keyName = "flashColor",
            name = "Flashing Box Color",
            description = "Color of the flashing reminder box.",
            position = 7
    )
    default Color flashColor() { return new Color(128, 0, 255, 150); }
}