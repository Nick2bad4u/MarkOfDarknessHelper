package com.markofdarknesshelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;


public class MarkOfDarknessHelperOverlay extends OverlayPanel
{
    private final MarkOfDarknessHelperConfig config;
    private final Client client;
    private boolean showReminder = false;
    private long reminderStartTime = 0;
    private boolean isMarkActive = false;

    @Inject
    public MarkOfDarknessHelperOverlay(MarkOfDarknessHelperConfig config, Client client)
    {
        this.config = config;
        this.client = client;
    }

    public void showReminderBox()
    {
        this.showReminder = true;
        this.reminderStartTime = System.currentTimeMillis();
        this.isMarkActive = false;
    }

    public void hideReminderBox()
    {
        this.showReminder = false;
        this.reminderStartTime = 0;
        this.isMarkActive = true;
    }

    /**
     * Called when the local player dies. If Mark of Darkness was active,
     * the buff is lost on death so we immediately show the recast reminder.
     */
    public void resetOnDeath()
    {
        if (isMarkActive)
        {
            showReminderBox();
        }
    }

    private boolean shouldShowReminder()
    {
        if (!showReminder)
        {
            return false;
        }
        if (reminderStartTime > 0 && (System.currentTimeMillis() - reminderStartTime) / 1000 > config.overlayTimeoutSeconds())
        {
            showReminder = false;
            reminderStartTime = 0;
            return false;
        }
        return true;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.onlyArceuus() && client.getVarbitValue(VarbitID.SPELLBOOK) != 3)
        {
            return null;
        }

        if (!shouldShowReminder())
        {
            return null;
        }

        panelComponent.getChildren().clear();
        panelComponent.getChildren().add(LineComponent.builder()
                .left("Cast Mark of Darkness!")
                .build());

        Color userFlashColor = config.flashColor();
        Color userBoxColor = config.boxColor();

        if (config.shouldFlash())
        {
            if (client.getGameCycle() % 40 >= 20)
            {
                panelComponent.setBackgroundColor(userBoxColor);
            }
            else
            {
                panelComponent.setBackgroundColor(userFlashColor);
            }
        }
        else
        {
            panelComponent.setBackgroundColor(userBoxColor);
        }

        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        return panelComponent.render(graphics);
    }
}
