package com.jaredscarito.advancedrolebot.api;

import com.jaredscarito.advancedrolebot.AdvancedRoleBot;
import com.timvisee.yamlwrapper.YamlConfiguration;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Utils {
    private static Utils utils = new Utils();
    public static Utils getInstance() {
        return utils;
    }

    private List<Role> getAllRolesUnderRole(Guild guild, Role parentRole) {
        // Get all roles in the guild
        List<Role> roles = new ArrayList<>();
        for (Role role : guild.getRoles()) {
            // Check if the role is under the specified parent role
            if (role.getPosition() < parentRole.getPosition()) {
                // This role is under the specified parent role
                roles.add(role);
            }
        }
        return roles;
    }

    public HashSet<String> getAddableRoles(String roleId, Guild guild) {
        YamlConfiguration fig = AdvancedRoleBot.getInstance().getConfig();
        List<String> roleKeys = (List<String>) fig.getList("Config.Roles." + roleId + ".Add");
        HashSet<String> roleIds = new HashSet<>();
        for (String roleKey : roleKeys) {
            if (!roleKey.equalsIgnoreCase("*")) {
                roleIds.add(roleKey);
            } else {
                // It's a wildcard... They can add every role that is below the specified role...
                Role parentRole = guild.getRoleById(roleId);
                for (Role role : this.getAllRolesUnderRole(guild, parentRole)) {
                    roleIds.add(role.getId());
                }
            }
        }
        return roleIds;
    }
    public HashSet<String> getRemoveableRoles(String roleId, Guild guild) {
        YamlConfiguration fig = AdvancedRoleBot.getInstance().getConfig();
        List<String> roleKeys = (List<String>) fig.getList("Config.Roles." + roleId + ".Remove");
        HashSet<String> roleIds = new HashSet<>();
        for (String roleKey : roleKeys) {
            if (!roleKey.equalsIgnoreCase("*")) {
                roleIds.add(roleKey);
            } else {
                // It's a wildcard... They can remove every role that is below the specified role...
                Role parentRole = guild.getRoleById(roleId);
                for (Role role : this.getAllRolesUnderRole(guild, parentRole)) {
                    roleIds.add(role.getId());
                }
            }
        }
        return roleIds;
    }
    public boolean hasRoleConfig(String roleId) {
        YamlConfiguration fig = AdvancedRoleBot.getInstance().getConfig();
        return fig.isConfigurationSection("Config.Roles." + roleId);
    }

    public EmbedBuilder getErrorEmbed(String content) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle("Error encountered...");
        eb.setColor(Color.RED);
        eb.setDescription(content);
        eb.setFooter("AdvancedRoleBot | github.com/JaredScar");
        return eb;
    }
    public EmbedBuilder getInfoEmbed(String title, String content) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setColor(Color.CYAN);
        eb.setDescription(content);
        eb.setFooter("AdvancedRoleBot | github.com/JaredScar");
        return eb;
    }
    public EmbedBuilder getSuccessEmbed(String title, String content) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setTitle(title);
        eb.setColor(Color.GREEN);
        eb.setDescription(content);
        eb.setFooter("AdvancedRoleBot | github.com/JaredScar");
        return eb;
    }
}
