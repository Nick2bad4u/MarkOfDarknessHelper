package com.markofdarknesshelper;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("markofdarknesshelper")
public interface MarkOfDarknessHelperConfig extends Config
{
    @ConfigItem(
            keyName = "shouldNotify",
            name = "Notify when Mark of Darkness expires",
            description = "Sends a notification once Mark of Darkness needs to be recast."
    )
    default boolean shouldNotify() { return true; }

    @ConfigItem(
            keyName = "shouldFlash",
            name = "Flash the Reminder Box",
            description = "Makes the reminder box flash."
    )
    default boolean shouldFlash() { return false; }

    @ConfigItem(
            keyName = "onlyArceuus",
            name = "Only on Arceuus Spellbook",
            description = "Only display the reminder box when on the Arceuus spellbook"
    )
    default boolean onlyArceuus() { return true; }
}