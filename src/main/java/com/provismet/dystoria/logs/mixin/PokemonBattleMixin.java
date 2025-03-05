package com.provismet.dystoria.logs.mixin;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.google.gson.JsonObject;
import com.provismet.dystoria.logs.file.LogWriter;
import com.provismet.dystoria.logs.format.PokemonBattleFormatter;
import com.provismet.dystoria.logs.networking.BattleLogPacketS2C;
import com.provismet.dystoria.logs.options.Settings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = PokemonBattle.class, remap = false)
public abstract class PokemonBattleMixin {
    @Shadow @Final public abstract List<ServerPlayerEntity> getPlayers ();

    @Shadow public abstract boolean isPvP ();

    @Shadow public abstract boolean isPvN ();

    @Shadow public abstract boolean isPvW ();

    @Inject(method = "end", at = @At("HEAD"))
    private void saveLogs (CallbackInfo info) {
        if (Settings.canLog(this.isPvP(), this.isPvN(), this.isPvW())) {
            JsonObject json = PokemonBattleFormatter.serialiseBattle((PokemonBattle)(Object)this);
            this.getPlayers().forEach(player -> ServerPlayNetworking.send(player, BattleLogPacketS2C.fromJson(json)));
            LogWriter.saveJson(json);
        }
    }
}
