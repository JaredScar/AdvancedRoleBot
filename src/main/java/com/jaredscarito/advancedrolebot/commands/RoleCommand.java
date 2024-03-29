package com.jaredscarito.advancedrolebot.commands;

import com.jaredscarito.advancedrolebot.AdvancedRoleBot;
import com.jaredscarito.advancedrolebot.api.Utils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.StringSelectMenu;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import java.awt.*;
import java.util.*;
import java.util.List;

public class RoleCommand extends ListenerAdapter {
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent evt) {
        if (evt.getMember() == null) return;
        if (evt.getCommandString().split(" ")[0].replace("/", "").equalsIgnoreCase(AdvancedRoleBot.getInstance().getConfig()
                .getString("Config.Commands.Role.Command"))) {
            if (evt.getSubcommandName() != null) {
                String addCmd = AdvancedRoleBot.getInstance().getConfig()
                        .getString("Config.Commands.Role.Sub-Commands.Add.Command");
                String removeCmd = AdvancedRoleBot.getInstance().getConfig()
                        .getString("Config.Commands.Role.Sub-Commands.Remove.Command");
                if (evt.getOption("selected_user") != null) {
                    User user;
                    user = Objects.requireNonNull(evt.getOption("selected_user")).getAsUser();
                    if (evt.getGuild() != null) {
                        List<Role> serverRoles = evt.getGuild().getRoles();
                        HashMap<String, String> serverRoleMapper = new HashMap<>();
                        for (Role r : serverRoles) {
                            serverRoleMapper.put(r.getId(), r.getName());
                        }
                        evt.getGuild().retrieveMemberById(user.getId()).queue((member) -> {
                            EmbedBuilder eb = new EmbedBuilder();
                            eb.setAuthor("AdvancedRoleBot");
                            eb.setFooter("AdvancedRoleBot - github.com/JaredScar");
                            StringSelectMenu.Builder stringSelBuilder = StringSelectMenu.create("arb");
                            List<StringSelectMenu> menus = new ArrayList<>();
                            boolean reply = true;
                            if (evt.getSubcommandName().equalsIgnoreCase(addCmd)) {
                                // ADD
                                eb.setTitle(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Add.Embed.Title"));
                                eb.setDescription(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Add.Embed.Description").replace("{MEMBER}", member.getAsMention()));
                                int rgb = Integer.parseInt(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Add.Embed.Color").replace("#", ""), 16);
                                eb.setColor(new Color(rgb));
                                if (!AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Add.Embed.Thumbnail").isEmpty())
                                    eb.setThumbnail(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Add.Embed.Thumbnail"));
                                stringSelBuilder = StringSelectMenu.create("0--arb|add=" + user.getId());
                                HashMap<String, String> roles = getApplicableRoleIds(evt.getMember());
                                TreeMap<String, String> sortedRoles = new TreeMap<>();
                                for (String roleKey : roles.keySet()) {
                                    HashSet<String> addableRoles = Utils.getInstance().getAddableRoles(roleKey, evt.getGuild());
                                    for (String roleId : addableRoles) {
                                        String roleName = serverRoleMapper.get(roleId);
                                        sortedRoles.put(roleName, roleId);
                                    }
                                }
                                for (Map.Entry<String, String> entry : sortedRoles.entrySet()) {
                                    String roleName = entry.getKey();
                                    String roleId = entry.getValue();

                                    stringSelBuilder.addOption(roleName, roleId);

                                    if (stringSelBuilder.getOptions().size() == 25) {
                                        stringSelBuilder.setPlaceholder(stringSelBuilder.getOptions().get(0).getLabel()
                                                + " => " + stringSelBuilder.getOptions()
                                                .get(stringSelBuilder.getOptions().size() - 1).getLabel());
                                        menus.add(stringSelBuilder.build());
                                        stringSelBuilder = StringSelectMenu.create(menus.size() + "--arb|add=" + user.getId());
                                    }
                                }
                                if (!stringSelBuilder.getOptions().isEmpty()) {
                                    stringSelBuilder.setPlaceholder(stringSelBuilder.getOptions().get(0).getLabel()
                                            + " => " + stringSelBuilder.getOptions()
                                            .get(stringSelBuilder.getOptions().size() - 1).getLabel());
                                    menus.add(stringSelBuilder.build());
                                }
                            } else if (evt.getSubcommandName().equalsIgnoreCase(removeCmd)) {
                                // REMOVE
                                eb.setTitle(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Remove.Embed.Title"));
                                eb.setDescription(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Remove.Embed.Description").replace("{MEMBER}", member.getAsMention()));
                                int rgb = Integer.parseInt(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Remove.Embed.Color").replace("#", ""), 16);
                                eb.setColor(new Color(rgb));
                                if (!AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Remove.Embed.Thumbnail").isEmpty())
                                    eb.setThumbnail(AdvancedRoleBot.getInstance().getConfig().getString("Config.Commands.Role.Sub-Commands.Remove.Embed.Thumbnail"));
                                stringSelBuilder = StringSelectMenu.create("0--arb|remove=" + user.getId());
                                HashMap<String, String> roles = getApplicableRoleIds(evt.getMember());
                                TreeMap<String, String> sortedRoles = new TreeMap<>();
                                for (String roleKey : roles.keySet()) {
                                    HashSet<String> removeableRoles = Utils.getInstance().getRemoveableRoles(roleKey, evt.getGuild());
                                    for (String roleId : removeableRoles) {
                                        String roleName = serverRoleMapper.get(roleId);
                                        sortedRoles.put(roleName, roleId);
                                    }
                                }
                                for (Map.Entry<String, String> entry : sortedRoles.entrySet()) {
                                    String roleName = entry.getKey();
                                    String roleId = entry.getValue();

                                    stringSelBuilder.addOption(roleName, roleId);

                                    if (stringSelBuilder.getOptions().size() == 25) {
                                        stringSelBuilder.setPlaceholder(stringSelBuilder.getOptions().get(0).getLabel()
                                                + " => " + stringSelBuilder.getOptions()
                                                .get(stringSelBuilder.getOptions().size() - 1).getLabel());
                                        menus.add(stringSelBuilder.build());
                                        stringSelBuilder = StringSelectMenu.create(menus.size() + "--arb|remove=" + user.getId());
                                    }
                                }
                                if (!stringSelBuilder.getOptions().isEmpty()) {
                                    stringSelBuilder.setPlaceholder(stringSelBuilder.getOptions().get(0).getLabel()
                                            + " => " + stringSelBuilder.getOptions()
                                            .get(stringSelBuilder.getOptions().size() - 1).getLabel());
                                    menus.add(stringSelBuilder.build());
                                }
                            } else {
                                // No valid command ran...
                                evt.replyEmbeds(Utils.getInstance().getErrorEmbed("No valid subcommand was ran.").build()).queue();
                                reply = false;
                            }
                            if (reply) {
                                ReplyCallbackAction act = evt.replyEmbeds(eb.build());
                                if (!menus.isEmpty()) {
                                    for (StringSelectMenu menu : menus) {
                                        act.addActionRow(menu);
                                    }
                                    act.setEphemeral(true).queue();
                                } else {
                                    evt.replyEmbeds(Utils.getInstance().getErrorEmbed("There are no roles " +
                                            "available for you to manage...").build()).queue();
                                }
                            }
                        }, (err) -> {
                            // Error, cannot get that user...
                            evt.replyEmbeds(Utils.getInstance().getErrorEmbed("Cannot get member instance of " +
                                    "specified user. Maybe they left?").build()).queue();
                        });
                    }
                } else {
                    // No valid user selected...
                    evt.replyEmbeds(Utils.getInstance().getErrorEmbed("A valid user was not selected.").build()).queue();
                }
            } else {
                // No valid subcommand...
                evt.replyEmbeds(Utils.getInstance().getErrorEmbed("No valid subcommand was ran.").build()).queue();
            }
        }
    }
    private HashMap<String, String> getApplicableRoleIds(Member mem) {
        HashMap<String, String> map = new HashMap<>();
        for (Role r : mem.getRoles()) {
            if (Utils.getInstance().hasRoleConfig(r.getId()))
                map.put(r.getId(), r.getName());
        }
        return map;
    }
}
