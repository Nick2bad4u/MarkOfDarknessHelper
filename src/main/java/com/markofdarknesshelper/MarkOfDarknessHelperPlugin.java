package com.markofdarknesshelper;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.ChatMessageType;
import net.runelite.client.Notifier;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;

@Slf4j
@PluginDescriptor(
        name = "Mark of Darkness Helper"
)
public class MarkOfDarknessHelperPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private Notifier notifier;

    @Inject
    private MarkOfDarknessHelperOverlay overlay;

    @Inject
    private MarkOfDarknessHelperConfig config;

    @Inject
    private OverlayManager overlayManager;

    private static final String MARK_OF_DARKNESS_MESSAGE = "You have placed a Mark of Darkness upon yourself.</col>";

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onChatMessage(ChatMessage event)
    {
        if (event.getType() != ChatMessageType.GAMEMESSAGE)
        {
            return;
        }

        if (event.getMessage().endsWith(MARK_OF_DARKNESS_MESSAGE))
        {
            int magicLevel = client.getBoostedSkillLevel(Skill.MAGIC);
            boolean hasPurgingStaff = isWieldingPurgingStaff();
            int dynamicTimeout = (int) Math.round(0.6 * magicLevel * (hasPurgingStaff ? 5 : 1));
            overlay.setMarkStartTime(System.currentTimeMillis());
            overlay.setTimeout(dynamicTimeout);
        }
    }

    private boolean isWieldingPurgingStaff()
    {
        final int PURGING_STAFF_ID = 29594; // Replace with actual ID if known
        ItemContainer equipment = client.getItemContainer(InventoryID.EQUIPMENT);
        if (equipment != null)
        {
            for (Item item : equipment.getItems())
            {
                if (item != null && item.getId() == PURGING_STAFF_ID)
                {
                    return true;
                }
            }
        }
        return false;
    }

    @Provides
    MarkOfDarknessHelperConfig provideConfig(ConfigManager configManager)
    {
        return configManager.getConfig(MarkOfDarknessHelperConfig.class);
    }
}
