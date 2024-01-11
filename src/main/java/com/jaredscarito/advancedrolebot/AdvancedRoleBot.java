package com.jaredscarito.advancedrolebot;

import com.jaredscarito.advancedrolebot.commands.RoleCommand;
import com.jaredscarito.advancedrolebot.listeners.DropdownListener;
import com.timvisee.yamlwrapper.YamlConfiguration;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import java.io.File;

public class AdvancedRoleBot {
    private static AdvancedRoleBot main = new AdvancedRoleBot();
    public static AdvancedRoleBot getInstance() {
        return main;
    }
    public YamlConfiguration getConfig() {
        return YamlConfiguration.loadFromFile(new File("config.yml"));
    }
    public static void main(String[] args) throws InterruptedException {
        String token = main.getConfig().getString("Config.Token");
        JDA jdaInstance = JDABuilder.createDefault(token).enableIntents(
                GatewayIntent.MESSAGE_CONTENT,
                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_MEMBERS
        )
        .setMemberCachePolicy(MemberCachePolicy.ALL)
        .setChunkingFilter(ChunkingFilter.ALL)
        .addEventListeners(new RoleCommand(), new DropdownListener())
        .build();
        jdaInstance.awaitReady();
        for (Guild guild : jdaInstance.getGuilds()) {
            guild.updateCommands().addCommands(
                    Commands.slash(getInstance().getConfig().getString("Config.Commands.Role.Command"), "The base role manipulation command")
                        .addSubcommands(
                            new SubcommandData(getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Add.Command"), "Add a role to a user")
                                .addOption(OptionType.USER, "selected_user", "The user to apply changes to"),
                            new SubcommandData(getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Remove.Command"), "Remove a role from a user")
                                .addOption(OptionType.USER, "selected_user", "The user to apply changes to")
                        )
            ).queue();
        }
    }
}
