package com.provismet.dystoria.logs;

import com.provismet.dystoria.logs.networking.BattleLogPacketS2C;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DystoriaBattleLogger implements ModInitializer {
	public static final String MODID = "dystoria-logger";
	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static Identifier identifier (String path) {
		return Identifier.of(MODID, path);
	}

	@Override
	public void onInitialize () {
		PayloadTypeRegistry.playS2C().register(BattleLogPacketS2C.ID, BattleLogPacketS2C.CODEC);
	}
}