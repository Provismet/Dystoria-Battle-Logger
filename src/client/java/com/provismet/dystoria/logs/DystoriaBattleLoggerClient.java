package com.provismet.dystoria.logs;

import com.provismet.dystoria.logs.file.LogWriter;
import com.provismet.dystoria.logs.networking.BattleLogPacketS2C;
import com.provismet.dystoria.logs.options.ClientSettings;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class DystoriaBattleLoggerClient implements ClientModInitializer {
    @Override
    public void onInitializeClient () {
        ClientPlayNetworking.registerGlobalReceiver(BattleLogPacketS2C.ID, (battleLogPacketS2C, context) -> {
            if (ClientSettings.keepLogs) LogWriter.saveString(battleLogPacketS2C.json());
        });
        ClientSettings.load();
    }
}
