package com.jaredscarito.advancedrolebot.listeners;

import com.jaredscarito.advancedrolebot.api.Utils;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.selections.SelectOption;

import java.util.ArrayList;

public class DropdownListener extends ListenerAdapter {
    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent evt) {
        String selectId = evt.getComponent().getId();
        if (selectId != null) {
            selectId = selectId.split("=")[0];
            if (evt.getComponent().getId().split("=").length > 1) {
                String userId = evt.getComponent().getId().split("=")[1];
                if (evt.getGuild() != null) {
                    String finalSelectId = selectId.split("--")[1];
                    SelectOption selOpt = null;
                    for (SelectOption opt : evt.getSelectedOptions()) {
                        selOpt = opt;
                    }
                    if (selOpt != null) {
                        Role role = evt.getGuild().getRoleById(selOpt.getValue());
                        if (role != null) {
                            evt.getGuild().retrieveMemberById(userId).queue((member) -> {
                                switch (finalSelectId.toLowerCase()) {
                                    case "arb|add" -> {
                                        if (!member.getRoles().contains(role)) {
                                            evt.getGuild().addRoleToMember(member, role).queue();
                                            evt.getMessage().editMessageComponents(new ArrayList<>()).queue();
                                            evt.editMessageEmbeds(Utils.getInstance()
                                                    .getSuccessEmbed("Role added", "The role " + role.getAsMention() + " was added to member " + member.getAsMention()).build()).queue();
                                        } else {
                                            // The member already has the role...
                                            evt.getMessage().delete().queue();
                                            evt.replyEmbeds(Utils.getInstance().getErrorEmbed("The member already has this role...").build()).queue();
                                        }
                                    }
                                    case "arb|remove" -> {
                                        if (member.getRoles().contains(role)) {
                                            evt.getGuild().removeRoleFromMember(member, role).queue();
                                            evt.getMessage().editMessageComponents(new ArrayList<>()).queue();
                                            evt.editMessageEmbeds(Utils.getInstance()
                                                            .getSuccessEmbed("Role removed", "The role " + role.getAsMention() + " was removed from member " + member.getAsMention()).build()).queue();
                                        } else {
                                            // The member does not have this role...
                                            evt.getMessage().delete().queue();
                                            evt.replyEmbeds(Utils.getInstance().getErrorEmbed("The member does not have this role...").build()).queue();
                                        }
                                    }
                                }
                            }, (err) -> {
                                // No valid user, maybe they left??
                                evt.getMessage().delete().queue();
                                evt.replyEmbeds(Utils.getInstance().getErrorEmbed("Cannot get member instance of " +
                                        "specified user. Maybe they left?").build()).queue();
                            });
                        } else {
                            // Not a valid role...
                            evt.getMessage().delete().queue();
                            evt.replyEmbeds(Utils.getInstance().getErrorEmbed("The specified role was not valid.").build()).queue();
                        }
                    } else {
                        // No valid option selected...
                        evt.getMessage().delete().queue();
                        evt.replyEmbeds(Utils.getInstance().getErrorEmbed("No valid option was selected.").build()).queue();
                    }
                } else {
                    // Guild is null...
                    evt.getMessage().delete().queue();
                    evt.replyEmbeds(Utils.getInstance().getErrorEmbed("Guild is null?...").build()).queue();
                }
            } else {
                // No user specified...
                evt.getMessage().delete().queue();
                evt.replyEmbeds(Utils.getInstance().getErrorEmbed("A valid user was not selected.").build()).queue();
            }
        } else {
            // Not a valid selectId... It's null
        }
    }
}
