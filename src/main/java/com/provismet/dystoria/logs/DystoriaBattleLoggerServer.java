package com.provismet.dystoria.logs;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.provismet.dystoria.logs.options.Settings;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class DystoriaBattleLoggerServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer () {
        Settings.load();

        ServerLifecycleEvents.AFTER_SAVE.register((minecraftServer, flush, force) -> Settings.save());

        CommandRegistrationCallback.EVENT.register((commandDispatcher, commandRegistryAccess, registrationEnvironment) -> {
            commandDispatcher.register(
                CommandManager.literal("battlelogger")
                    .requires(source -> source.hasPermissionLevel(2))
                    .then(CommandManager.literal("PvP")
                        .executes(context -> {
                                context.getSource().sendFeedback(() -> Text.translatableWithFallback(
                                    "command.dystoria.logger.check.pvp",
                                    "Player vs Player logging: %1$s",
                                    String.valueOf(Settings.logPvP)
                                ), false);
                                return 1;
                            }
                        )
                        .then(CommandManager.argument("value", BoolArgumentType.bool())
                            .executes(context -> {
                                Settings.logPvP = BoolArgumentType.getBool(context, "value");
                                context.getSource()
                                    .sendFeedback(() -> Text.translatableWithFallback(
                                        "command.dystoria.logger.set.pvp",
                                        "Set Player vs Player logging to %1$s",
                                        String.valueOf(Settings.logPvP)
                                    ), false);
                                return 1;
                            })
                        )
                    )
                    .then(CommandManager.literal("PvN")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.translatableWithFallback(
                                "command.dystoria.logger.check.pvn",
                                "Player vs NPC logging: %1$s",
                                String.valueOf(Settings.logPvN)
                            ), false);
                            return 1;
                        })
                        .then(CommandManager.argument("value", BoolArgumentType.bool())
                            .executes(context -> {
                                Settings.logPvN = BoolArgumentType.getBool(context, "value");
                                context.getSource()
                                    .sendFeedback(() -> Text.translatableWithFallback(
                                        "command.dystoria.logger.set.pvn",
                                        "Set Player vs NPC logging to %1$s",
                                        String.valueOf(Settings.logPvN)
                                    ), false);
                                return 1;
                            }))
                    )
                    .then(CommandManager.literal("PvW")
                        .executes(context -> {
                            context.getSource().sendFeedback(() -> Text.translatableWithFallback(
                                "command.dystoria.logger.check.pvw",
                                "Player vs Wild Pokemon logging: %1$s",
                                String.valueOf(Settings.logPvW)
                            ), false);
                            return 1;
                        })
                        .then(CommandManager.argument("value", BoolArgumentType.bool())
                            .executes(context -> {
                                Settings.logPvW = BoolArgumentType.getBool(context, "value");
                                context.getSource()
                                    .sendFeedback(() -> Text.translatableWithFallback(
                                        "command.dystoria.logger.set.pvw",
                                        "Set Player vs Wild Pokemon logging to %1$s",
                                        String.valueOf(Settings.logPvW)
                                    ), false);
                                return 1;
                            })
                        )
                    )
                );
        });
    }
}
