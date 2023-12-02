package com.fazziclay.f3time.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;

public class ModMenu implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (ConfigScreenFactory<Screen>) parent -> AutoConfig.getConfigScreen(Config.class, parent).get();
    }
}
