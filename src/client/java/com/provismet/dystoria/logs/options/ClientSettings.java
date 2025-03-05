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

public final class ClientSettings {
    private static final String CONFIG_FOLDER = "./config/";
    private static final String CONFIG_FILE = CONFIG_FOLDER + "dystoria-logger.json";

    public static boolean keepLogs = false;

    public static void save () {
        File folder = new File(CONFIG_FOLDER);
        if (!folder.exists()) {
            folder.mkdirs();
        }

        JsonObject json = new JsonObject();
        json.addProperty("save-logs", ClientSettings.keepLogs);

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
                if (configObject.has("save-logs")) {
                    ClientSettings.keepLogs = configObject.get("save-logs").getAsBoolean();
                }
            }
        }
        catch (FileNotFoundException e) {
            ClientSettings.save();
        }
        catch (IOException e) {
            DystoriaBattleLogger.LOGGER.error("Failed to load config for Dystoria Logger due to the following exception: ", e);
        }
    }
}
