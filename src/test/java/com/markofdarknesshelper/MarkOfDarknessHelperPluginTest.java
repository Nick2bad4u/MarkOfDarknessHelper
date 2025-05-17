package com.markofdarknesshelper;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class MarkOfDarknessHelperPluginTest
{
    public static void main(String[] args) throws Exception
    {
        ExternalPluginManager.loadBuiltin(MarkOfDarknessHelperPlugin.class);
        RuneLite.main(args);
    }
}