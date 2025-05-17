package net.runelite.client.plugins.markofdarknesshelper;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;

public class MarkOfDarknessHelperOverlay extends OverlayPanel
{
    private static final Color FOCUS = new Color(128, 0, 255, 150);

    private final MarkOfDarknessHelperConfig config;
    private final Client client;
    private long markStartTime = 0;
    private int timeoutSeconds = 60;

    @Inject
    public MarkOfDarknessHelperOverlay(MarkOfDarknessHelperConfig config, Client client)
    {
        this.config = config;
        this.client = client;
    }

    public void setMarkStartTime(long startTime)
    {
        this.markStartTime = startTime;
    }

    public void setTimeout(int seconds)
    {
        this.timeoutSeconds = seconds;
    }

    private boolean shouldShowReminder()
    {
        if (markStartTime == 0)
        {
            return true;
        }
        long elapsed = (System.currentTimeMillis() - markStartTime) / 1000;
        return elapsed > timeoutSeconds;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        if (config.onlyArceuus() && client.getVarbitValue(Varbits.SPELLBOOK) != 3)
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

        if (config.shouldFlash())
        {
            if (client.getGameCycle() % 40 >= 20)
            {
                panelComponent.setBackgroundColor(getPreferredColor());
            }
            else
            {
                panelComponent.setBackgroundColor(FOCUS);
            }
        }
        else
        {
            panelComponent.setBackgroundColor(FOCUS);
        }

        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        return panelComponent.render(graphics);
    }
}