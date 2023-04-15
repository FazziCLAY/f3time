package com.fazziclay.openoptimizemc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class OpenOptimizeMc implements ClientModInitializer {
    public static final String ID = "openoptimizemc";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);
    public static final Random RANDOM = new Random();


    private static volatile OpenOptimizeMc instance;
    private static Config config;


    public static OpenOptimizeMc getInstance() {
        return instance;
    }

    public static Config getConfig() {
        return config;
    }

    private boolean togglePlayers = false;
    private boolean toggleOnlyHeads = false;
    private boolean configOpen = false;

    @Override
    public void onInitializeClient() {
        instance = this;

        config = Config.load();

        KeyBinding binding_togglePlayers = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.openoptimizemc.togglePlayers", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_I, "key.category.first.test"));
        KeyBinding binding_toggleOnlyHeads = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.openoptimizemc.toggleOnlyHeads", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_U, "key.category.first.test"));
        KeyBinding binding_config = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.openoptimizemc.config", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_L, "key.category.first.test"));


        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (binding_togglePlayers.isPressed()) {
                togglePlayers = true;
            } else if (togglePlayers) {
                runTogglePlayers(client);
                togglePlayers = false;
            }

            if (binding_toggleOnlyHeads.isPressed()) {
                toggleOnlyHeads = true;
            } else if (toggleOnlyHeads) {
                runToggleOnlyHeads(client);
                toggleOnlyHeads = false;
            }

            if (binding_config.isPressed()) {
                if (!configOpen) {
                    client.setScreen(new ConfigScreen(null));
                }
                configOpen = true;
            } else if (configOpen) {
                configOpen = false;
            }
        });
    }

    private void runToggleOnlyHeads(MinecraftClient client) {
        LOGGER.info("pressed");
        boolean c = !getConfig().isPlayersOnlyHeads();
        getConfig().setPlayersOnlyHeads(c);
        if (c) {
            client.player.sendMessage(Text.of("§a[client] Players only-head enabled"));
        } else {
            client.player.sendMessage(Text.of("§c[client] Players only-head disabled"));
        }
    }

    private void runTogglePlayers(MinecraftClient client) {
        LOGGER.info("pressed");
        boolean c = !getConfig().isRenderPlayers();
        if (c) {
            client.player.sendMessage(Text.of("§a[client] Players enabled"));
        } else {
            client.player.sendMessage(Text.of("§c[client] Players disabled"));
        }
        getConfig().setRenderPlayers(c);
    }
}
