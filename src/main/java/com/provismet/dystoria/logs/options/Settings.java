package com.provismet.dystoria.logs.options;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.provismet.dystoria.logs.DystoriaBattleLogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class Settings {
    private static final String CONFIG_FOLDER = "./config/";
    private static final String CONFIG_FILE = CONFIG_FOLDER + "dystoria-logger-server.json";

    public static boolean logPvP = true;
    public static boolean logPvN = false;
    public static boolean logPvW = false;

    public static boolean canLog (boolean isPvP, boolean isPvN, boolean isPvW) {
        return (Settings.logPvP && isPvP) || (Settings.logPvN && isPvN) || (Settings.logPvW && isPvW);
    }

    public static void save () {
        JsonObject json = new JsonObject();
        json.addProperty("log-pvp", Settings.logPvP);
        json.addProperty("log-pvn", Settings.logPvN);
        json.addProperty("log-pvw", Settings.logPvW);

        File folder = new File(CONFIG_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
            writer.write(gson.toJson(json));
        }
        catch (IOException e) {
            DystoriaBattleLogger.LOGGER.error("Failed to save config for Dystoria Logger due to the following exception: ", e);
        }
    }

    public static void load () {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            JsonElement configJson = JsonParser.parseReader(reader);
            if (configJson instanceof JsonObject configObject) {
                if (configObject.has("log-pvp")) {
                    Settings.logPvP = configObject.get("log-pvp").getAsBoolean();
                }
                if (configObject.has("log-pvn")) {
                    Settings.logPvN = configObject.get("log-pvn").getAsBoolean();
                }
                if (configObject.has("log-pvw")) {
                    Settings.logPvW = configObject.get("log-pvw").getAsBoolean();
                }
            }
        }
        catch (FileNotFoundException e) {
            Settings.save();
        }
        catch (IOException e) {
            DystoriaBattleLogger.LOGGER.error("Failed to load config for Dystoria Logger due to the following exception: ", e);
        }
    }
}
