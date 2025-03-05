package com.provismet.dystoria.logs.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.provismet.dystoria.logs.DystoriaBattleLogger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public final class LogWriter {
    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("uuuu-MM-dd+HHmm");

    public static void saveString (String json) {
        try {
            JsonElement jsonElement = JsonParser.parseString(json);
            if (jsonElement.isJsonObject()) {
                LogWriter.saveJson((JsonObject)jsonElement);
            }
            else {
                LogWriter.saveJsonElement(jsonElement, "Unknown ID");
            }
        }
        catch (Exception e) {
            DystoriaBattleLogger.LOGGER.error("Received invalid JSON from server: {}", json);
        }
    }

    public static void saveJson (JsonObject json) {
        String battleId = "Unknown ID";
        if (json.has("id")) {
            JsonElement idElement = json.get("id");
            if (idElement.isJsonPrimitive()) battleId = idElement.getAsString();
        }

        LogWriter.saveJsonElement(json, battleId);
    }

    public static void saveJsonElement (JsonElement json, String id) {
        File battleLogsDir = new File("./battle_logs/");
        if (!battleLogsDir.exists()) {
            battleLogsDir.mkdirs();
        }

        String filename = "./battle_logs/" + LocalDateTime.now(ZoneOffset.UTC).format(TIME_FORMAT) + "+(" + id + ").json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(gson.toJson(json));
        }
        catch (IOException e) {
            DystoriaBattleLogger.LOGGER.error("Failed to save battle log, data is as follows:\nName: {}\nData: {}\nError: {}", filename, json, e);
        }
    }
}
