package com.fazziclay.f3time;

import com.fazziclay.f3time.config.Config;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

public class F3Time implements ClientModInitializer {
    public static final String ID = "f3time";
    public static final Logger LOGGER = LoggerFactory.getLogger(ID);


    private static volatile F3Time instance;
    private static Config config;
    private static SimpleDateFormat timeFormat;
    private static long launchTime;


    public static F3Time getInstance() {
        return instance;
    }

    public static Config getConfig() {
        return config;
    }

    public static String genText() {
        return config.getPattern()
            .replace("&", "§")
                .replace("$(time)", timeFormat.format(GregorianCalendar.getInstance().getTime()))
                .replace("$(play_time)", timeDifference(calcPlayTime()));
    }

    public static String timeDifference(long l) {
        int h = (int) (l / (3600));
        int m = (int) ((l - (h * 3600)) / 60);
        int s = (int) (l - (h * 3600) - m * 60);

        return String.format("%02d:%02d:%02d", h, m, s);
    }

    public static long calcPlayTime() {
        return (System.currentTimeMillis() / 1000) - launchTime;
    }

    @Override
    public void onInitializeClient() {
        instance = this;

        var o = AutoConfig.register(Config.class, GsonConfigSerializer::new);
        config = o.getConfig();

        timeFormat = new SimpleDateFormat("HH:mm:ss");

        launchTime = System.currentTimeMillis() / 1000;

        LOGGER.info("F3Time initialized!");
    }
}
