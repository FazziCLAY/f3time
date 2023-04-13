package com.fazziclay.f3time;

import net.minecraft.client.font.MultilineText;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerWarningScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.function.Consumer;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private TextFieldWidget pattern;
    private ButtonWidget cancel;
    private ButtonWidget addParagraph;
    private ButtonWidget reset;
    private ButtonWidget resetInstantly;
    private MultilineText preview;

    public ConfigScreen(Screen parent) {
        super(Text.of("F3Name"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        preview = MultilineText.create(textRenderer, Text.of(""));

        cancel = new ButtonWidget(width - 60, height - 30, 50, 20, Text.of("Close"), button -> this.close());
        addSelectableChild(cancel);

        resetInstantly = new ButtonWidget(width - 65, height - 30 - 30, 60, 20, Text.of("§c[ RESET! ]"), button -> {
            pattern.setText(new Config().getPattern());
            resetInstantly.visible = false;
        });
        resetInstantly.visible = false;
        addSelectableChild(resetInstantly);

        addParagraph = new ButtonWidget(10, 30, 100, 20, Text.of("Add §a*Paragraph*"), button -> {
            int cursor = pattern.getCursor();
            String t = pattern.getText();
            t = t.substring(0, cursor) + "§" + t.substring(cursor);
            pattern.setText(t);
            pattern.setCursor(cursor+1);
        });
        addSelectableChild(addParagraph);

        reset = new ButtonWidget(120, 30, 100, 20, Text.of("Reset to default"), button -> {
            resetInstantly.visible = !resetInstantly.visible;
        });
        addSelectableChild(reset);


        pattern = new TextFieldWidget(textRenderer, 10, 60, width-20, 20, null, Text.of("Enter pattern"));
        pattern.setMaxLength(99999);
        pattern.setText(F3Time.getConfig().getPattern());
        addSelectableChild(pattern);


        pattern.setChangedListener(s -> {
            F3Time.getConfig().setPattern(s);
        });
    }

    @Override
    public void tick() {
        pattern.tick();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 8, Color.WHITE.getRGB());

        this.addParagraph.render(matrices, mouseX, mouseY, delta);
        this.reset.render(matrices, mouseX, mouseY, delta);
        this.resetInstantly.render(matrices, mouseX, mouseY, delta);
        this.pattern.render(matrices, mouseX, mouseY, delta);
        this.cancel.render(matrices, mouseX, mouseY, delta);

        preview = MultilineText.create(textRenderer, Text.of(F3Time.genText()));
        preview.drawWithShadow(matrices, 10, 100, 2, Color.WHITE.getRGB());

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        this.client.setScreen(parent);
    }
}
