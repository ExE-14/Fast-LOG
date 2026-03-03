package com.notGecko.fast_log.client.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Config(name = "fast_log")
public class FlogConfig implements ConfigData {
    @ConfigEntry.Category("general")
    public boolean enabled = true;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip(count = 2)
    public String commandToSend = "trigger login set";

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip
    public int delayTicks = 0;

    @ConfigEntry.Category("general")
    @ConfigEntry.Gui.Tooltip
    public String hideFromChat = "Login successful.";

    @ConfigEntry.Category("conditions")
    @ConfigEntry.Gui.Tooltip
    public boolean checkBlindness = true;

    @ConfigEntry.Category("conditions")
    @ConfigEntry.Gui.Tooltip
    public List<String> allowedIps = new ArrayList<>(Arrays.asList("65.108.21.151:25914"));
}