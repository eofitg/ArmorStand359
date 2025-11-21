package net.eofitg.armorstand359.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.eofitg.armorstand359.ArmorStand359;
import net.eofitg.armorstand359.util.ArmorStandUtil;
import net.eofitg.armorstand359.util.MouseUtil;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.text.Text;

import java.util.concurrent.CompletableFuture;

public class ArmorStandCommand {
    private static final String[] SUB_COMMANDS = {"toggle", "set", "reset"};

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        dispatcher.register(ClientCommandManager.literal("armorstand")
                .executes(context -> {
                    showHelp(context.getSource());
                    return Command.SINGLE_SUCCESS;
                })
                .then(ClientCommandManager.argument("subcommand", StringArgumentType.string())
                        .suggests(new AutoTipSuggestionProvider())
                        .executes(context -> {
                            String subCommand = StringArgumentType.getString(context, "subcommand");
                            return executeSubCommand(context.getSource(), subCommand);
                        })
                )
                .then(ClientCommandManager.literal("toggle")
                        .executes(context -> toggle(context.getSource()))
                )
                .then(ClientCommandManager.literal("set")
                        .executes(context -> set(context.getSource()))
                )
                .then(ClientCommandManager.literal("reset")
                        .executes(context -> set(context.getSource()))
                )
        );
    }

    private static int executeSubCommand(FabricClientCommandSource source, String subCommand) {
        return switch (subCommand.toLowerCase()) {
            case "toggle" -> toggle(source);
            case "set" -> set(source);
            case "reset" -> reset(source);
            default -> {
                source.sendFeedback(Text.literal("§cUnknown subcommand: " + subCommand));
                showHelp(source);
                yield Command.SINGLE_SUCCESS;
            }
        };
    }

    private static void showHelp(FabricClientCommandSource source) {
        source.sendFeedback(Text.literal("/armorstand toggle|set|reset"));
    }

    private static int toggle(FabricClientCommandSource source) {
        ArmorStand359.enabled = !ArmorStand359.enabled;
        source.sendFeedback(Text.literal(ArmorStand359.enabled + ""));
        return Command.SINGLE_SUCCESS;
    }

    private static int set(FabricClientCommandSource source) {
        ArmorStandEntity armorStand = MouseUtil.getMouseOverAS();
        if (armorStand != null) {
            String id = ArmorStandUtil.getArmorStandId(armorStand);
            if (id != null) {
                ArmorStand359.target = id;
            }
        }
        return Command.SINGLE_SUCCESS;
    }

    private static int reset(FabricClientCommandSource source) {
        ArmorStand359.target = "";
        return Command.SINGLE_SUCCESS;
    }

    // Tab Completion
    public static class AutoTipSuggestionProvider implements SuggestionProvider<FabricClientCommandSource> {
        @Override
        public CompletableFuture<Suggestions> getSuggestions(CommandContext<FabricClientCommandSource> context, SuggestionsBuilder builder) {
            String remaining = builder.getRemaining().toLowerCase();

            for (String subCommand : SUB_COMMANDS) {
                if (subCommand.startsWith(remaining)) {
                    builder.suggest(subCommand);
                }
            }

            return builder.buildFuture();
        }
    }
}
