package com.provismet.dystoria.logs.format;

import com.cobblemon.mod.common.api.battles.model.PokemonBattle;
import com.cobblemon.mod.common.api.battles.model.actor.BattleActor;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.helditem.HeldItemProvider;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.battles.BattleSide;
import com.cobblemon.mod.common.battles.pokemon.BattlePokemon;
import com.cobblemon.mod.common.pokemon.PokemonStats;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * <p>Utility class that formats battle-related objects into JSON.</p>
 *
 * <p>For convenience and reuse, each step is split into separate methods.</p>
 */
public final class PokemonBattleFormatter {
    public static JsonObject serialiseBattle (PokemonBattle battle) {
        JsonObject battleJson = new JsonObject();
        battleJson.addProperty("id", battle.getBattleId().toString());
        battleJson.add("sides", PokemonBattleFormatter.serialiseSides(battle.getSides()));

        JsonArray chatLog = new JsonArray();
        battle.getChatLog().forEach(line -> chatLog.add(line.getString()));
        battleJson.add("messages", chatLog);

        return battleJson;
    }

    public static JsonArray serialiseSides (Iterable<BattleSide> sides) {
        JsonArray sidesJson =  new JsonArray();
        for (BattleSide side : sides) {
            sidesJson.add(PokemonBattleFormatter.serialiseSide(side));
        }

        return sidesJson;
    }

    public static JsonObject serialiseSide (BattleSide side) {
        JsonObject sideJson = new JsonObject();
        for (BattleActor actor : side.getActors()) {
            sideJson.add(actor.getName().getString(), PokemonBattleFormatter.serialiseTeam(actor.getPokemonList()));
        }

        return sideJson;
    }

    public static JsonArray serialiseTeam (List<BattlePokemon> team) {
        JsonArray teamArray = new JsonArray();
        for (BattlePokemon pokemon : team) {
            teamArray.add(PokemonBattleFormatter.serialisePokemon(pokemon));
        }
        return teamArray;
    }

    public static JsonObject serialisePokemon (BattlePokemon pokemon) {
        PokemonWrapper wrappedPokemon = new PokemonWrapper(pokemon);
        JsonObject json = new JsonObject();

        json.addProperty("species", wrappedPokemon.getSpecies());
        json.addProperty("item", wrappedPokemon.getHeldItem());
        json.addProperty("ability", wrappedPokemon.getAbility());
        json.addProperty("nature", wrappedPokemon.getNature());
        json.addProperty("gender", wrappedPokemon.getGender());

        JsonArray movesJson = new JsonArray();
        for (String move : wrappedPokemon.getMoveSet()) {
            movesJson.add(move);
        }
        json.add("moves", movesJson);

        JsonObject ivArray = PokemonBattleFormatter.formatStats(wrappedPokemon.getIVs());
        JsonObject evArray = PokemonBattleFormatter.formatStats(wrappedPokemon.getEVs());

        json.add("iv", ivArray);
        json.add("ev", evArray);

        return json;
    }

    public static JsonObject formatStats (PokemonStats stats) {
        JsonObject statObject = new JsonObject();
        statObject.addProperty("health", stats.getOrDefault(Stats.HP));
        statObject.addProperty("attack", stats.getOrDefault(Stats.ATTACK));
        statObject.addProperty("defence", stats.getOrDefault(Stats.DEFENCE));
        statObject.addProperty("special-attack", stats.getOrDefault(Stats.SPECIAL_ATTACK));
        statObject.addProperty("special-defence", stats.getOrDefault(Stats.SPECIAL_DEFENCE));
        statObject.addProperty("speed", stats.getOrDefault(Stats.SPEED));

        return statObject;
    }

    public static final class PokemonWrapper {
        private final BattlePokemon base;

        private PokemonWrapper (BattlePokemon base) {
            this.base = base;
        }

        public String getSpecies () {
            return this.base.getEffectedPokemon().showdownId();
        }

        public String getHeldItem () {
            return HeldItemProvider.INSTANCE.provideShowdownId(this.base);
        }

        public String getAbility () {
            return this.base.getEffectedPokemon().getAbility().getName();
        }

        public String getNature () {
            return this.base.getNature().getName().getPath();
        }

        public String getGender () {
            return this.base.getEffectedPokemon().getGender().asString();
        }

        public List<String> getMoveSet () {
            return this.base.getMoveSet().getMoves().stream().map(Move::getName).toList();
        }

        public PokemonStats getIVs () {
            return this.base.getIvs();
        }

        public PokemonStats getEVs () {
            return this.base.getEffectedPokemon().getEvs();
        }
    }
}
