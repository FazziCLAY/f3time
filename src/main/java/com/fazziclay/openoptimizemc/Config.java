package com.fazziclay.openoptimizemc;

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
    @SerializedName("renderPlayers")
    private boolean renderPlayers = true;
    @SerializedName("playersOnlyHeads")
    private boolean playersOnlyHeads = false;
    @SerializedName("playersModelPose")
    private boolean playersModelPose = true;
    @SerializedName("renderEntities")
    private boolean renderEntities = true;

    @SerializedName("renderLevel")
    private boolean renderLevel = true;

    @SerializedName("advancedProfilerForGameRendererEntities")
    private boolean advancedProfilerForGameRendererEntities = true;
    @SerializedName("renderBlockEntities")
    private boolean renderBLockEntities;

    public void save() {
        File f = new File(MinecraftClient.getInstance().runDirectory, "config/openoptimizemc.json");
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
        File f = new File(MinecraftClient.getInstance().runDirectory, "config/openoptimizemc.json");
        if (!f.exists()) return new Config();
        try {
            String s = Files.readString(f.toPath(), StandardCharsets.UTF_8);
            Gson gson = new Gson();
            return gson.fromJson(s, Config.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //TODO: in dev...
    public boolean isUpdateChunks() {
        return true;
    }



    // IS RENDER PLAYERS (personal for minecraft:player)
    public boolean isRenderPlayers() {
        return renderPlayers;
    }
    public void setRenderPlayers(boolean renderPlayers) {
        this.renderPlayers = renderPlayers;
        save();
    }
    public boolean toggleRenderPlayers() { return (renderPlayers = !renderPlayers); }



    // PLAYERS RENDER ONLY HEADS
    public boolean isPlayersOnlyHeads() {
        return playersOnlyHeads;
    }
    public void setPlayersOnlyHeads(boolean b) {
        playersOnlyHeads = b;
        save();
    }
    public boolean togglePlayersOnlyHeads() { return (playersOnlyHeads = !playersOnlyHeads); }



    // PLAYERS MODEL POSE
    public boolean isPlayersModelPose() {return playersModelPose;}
    public void setPlayersModelPose(boolean playersModelPose) {
        this.playersModelPose = playersModelPose;
        save();
    }
    public boolean togglePlayersModelPose() { return (playersModelPose = !playersModelPose); }



    // RENDER ENTITIES
    public boolean isRenderEntities() {return renderEntities;}
    public void setRenderEntities(boolean renderEntities) {
        this.renderEntities = renderEntities;
        save();
    }
    public boolean toggleRenderEntities() { return (renderEntities = !renderEntities); }



    // IS RENDER LEVEL (WORLD, MAP)
    public boolean isRenderLevel() {return renderLevel;}
    public void setRenderLevel(boolean b) {
        this.renderLevel = b;
        save();
    }
    public boolean toggleRenderLevel() {return (renderLevel = !renderLevel);}



    // ENTITIES ADVANCED PROFILER
    public boolean isAdvancedProfilerForGameRendererEntities() {return advancedProfilerForGameRendererEntities;}
    public void setAdvancedProfilerForGameRendererEntities(boolean b) {
        this.advancedProfilerForGameRendererEntities = b;
        save();
    }
    public boolean toggleAdvancedProfilerForGameRendererEntities() {return (advancedProfilerForGameRendererEntities = !advancedProfilerForGameRendererEntities);}



    // RENDER BLOCK ENTITIES
    public boolean isRenderBlockEntities() {return renderBLockEntities;}
    public void setRenderBlockEntities(boolean b) {
        this.renderBLockEntities = b;
        save();
    }
    public boolean toggleRenderBlockEntities() {
        return (renderBLockEntities = !renderBLockEntities);
    }
}
