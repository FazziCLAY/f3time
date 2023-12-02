package com.fazziclay.f3time.config;

import me.shedaniel.autoconfig.ConfigData;

@me.shedaniel.autoconfig.annotation.Config(name = "f3time")
public class Config implements ConfigData {
    private boolean enabled = true;
    private String pattern = "&a[F3Time] Time: $(time); Play time: $(play_time)";
    private int position = -1;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
