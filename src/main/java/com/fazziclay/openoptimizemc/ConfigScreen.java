package com.fazziclay.openoptimizemc;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.VideoOptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private final Config config;


    private ButtonWidget renderLevelButton;
    private ButtonWidget renderEntitiesButton;
    private ButtonWidget renderBlockEntitiesButton;
    private ButtonWidget renderPlayersButton;
    private ButtonWidget playersOnlyHead;
    private ButtonWidget playersModelPose;
    private ButtonWidget advancedProfilerButton;

    public ConfigScreen(Screen parent) {
        super(Text.of("OpenOptimizeMC"));
        this.parent = parent;
        this.config = OpenOptimizeMc.getConfig();
        this.client = MinecraftClient.getInstance();
    }

    @Override
    protected void init() {
        renderLevelButton = new ButtonWidget(10, 20, 110, 20, Text.empty(), button -> {
            stateButton(renderLevelButton, "Render world", config.toggleRenderLevel());
        });
        stateButton(renderLevelButton, "Render world", config.isRenderLevel());
        addSelectableChild(renderLevelButton);
        addDrawable(renderLevelButton);

        renderEntitiesButton = new ButtonWidget(130, 20, 110, 20, Text.empty(), button -> {
            stateButton(renderEntitiesButton, "Render entities", config.toggleRenderEntities());
        });
        stateButton(renderEntitiesButton, "Render entities", config.isRenderEntities());
        addSelectableChild(renderEntitiesButton);
        addDrawable(renderEntitiesButton);

        renderBlockEntitiesButton = new ButtonWidget(10, 45, 150, 20, Text.empty(), button -> {
            stateButton(renderBlockEntitiesButton, "§cRender block-entities", config.toggleRenderBlockEntities());
        });
        stateButton(renderBlockEntitiesButton, "§cRender block-entities", config.isRenderBlockEntities());
        addSelectableChild(renderBlockEntitiesButton);
        addDrawable(renderBlockEntitiesButton);


        // PLAYERS
        renderPlayersButton = new ButtonWidget(10, 70, 100, 20, Text.empty(), button -> {
            stateButton(renderPlayersButton, "Render players", config.toggleRenderPlayers());
        });
        stateButton(renderPlayersButton, "Render players", config.isRenderPlayers());
        addSelectableChild(renderPlayersButton);
        addDrawable(renderPlayersButton);

        playersOnlyHead = new ButtonWidget(120, 70, 120, 20, Text.empty(), button -> {
            stateButton(playersOnlyHead, "Players only-head", config.togglePlayersOnlyHeads());
        });
        stateButton(playersOnlyHead, "Players only-head", config.isPlayersOnlyHeads());
        addSelectableChild(playersOnlyHead);
        addDrawable(playersOnlyHead);

        playersModelPose = new ButtonWidget(250, 70, 120, 20, Text.empty(), button -> {
            stateButton(playersModelPose, "Players ModelPose", config.togglePlayersModelPose());
        });
        stateButton(playersModelPose, "Players ModelPose", config.isPlayersModelPose());
        addSelectableChild(playersModelPose);
        addDrawable(playersModelPose);


        // Profiler
        advancedProfilerButton = new ButtonWidget(10, 95, 200, 20, Text.empty(), button -> {
            stateButton(advancedProfilerButton, "Advanced Profiler(Shift+F3)", config.toggleAdvancedProfilerForGameRendererEntities());
        });
        stateButton(advancedProfilerButton, "Advanced Profiler(Shift+F3)", config.isAdvancedProfilerForGameRendererEntities());
        addSelectableChild(advancedProfilerButton);
        addDrawable(advancedProfilerButton);


        ButtonWidget cancel = new ButtonWidget(width - 60, height - 30, 50, 20, Text.of("Close"), button -> this.close());
        addSelectableChild(cancel);
        addDrawable(cancel);
    }

    private void stateButton(ButtonWidget button, String s, boolean b) {
        button.setMessage(Text.of(s + ": " + (b ? "§aON" : "§cOFF")));
    }

    @Override
    public void tick() {
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        assert client != null;
        if (client.world == null) drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, Color.WHITE.getRGB());
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
