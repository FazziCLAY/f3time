package com.fazziclay.f3time.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import net.minecraft.client.MinecraftClient;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Config {
    @SerializedName("enabled")
    private boolean enabled = true;
    @SerializedName("pattern")
    private String pattern = "§a[F3Time] Time: $(time); Play time: $(play_time)";

    public void save() {
        File f = new File(MinecraftClient.getInstance().runDirectory, "config/f3time.json");
        try {
            FileWriter fw = new FileWriter(f);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            fw.write(gson.toJson(this, Config.class));
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Config load() {
        File f = new File(MinecraftClient.getInstance().runDirectory, "config/f3time.json");
        if (!f.exists()) return new Config();
        try {
            String s = Files.readString(f.toPath(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            return gson.fromJson(s, Config.class);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load F3Time config.", e);
        }
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
        save();
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
