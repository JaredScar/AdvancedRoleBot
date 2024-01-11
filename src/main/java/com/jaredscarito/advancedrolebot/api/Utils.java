package com.jaredscarito.advancedrolebot.api;

import com.jaredscarito.advancedrolebot.AdvancedRoleBot;
import com.timvisee.yamlwrapper.YamlConfiguration;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {
    private static Utils utils = new Utils();
    public static Utils getInstance() {
        return utils;
    }

    public List<String> getAddableRoles(String roleId) {
        YamlConfiguration fig = AdvancedRoleBot.getInstance().getConfig();
        List<String> roleKeys = fig.getConfigurationSection("Config.Roles." + roleId).getKeys();
        List<String> roleIds = new ArrayList<>();
        for (String roleKey : roleKeys) {
            List<String> perms = (List<String>) fig.getList("Config.Roles." + roleId + "." + roleKey + ".Permissions");
            if (perms.contains("ADD"))
                roleIds.add(roleKey);
        }
        return roleIds;
    }
    public List<String> getRemoveableRoles(String roleId) {
        YamlConfiguration fig = AdvancedRoleBot.getInstance().getConfig();
        List<String> roleKeys = fig.getConfigurationSection("Config.Roles." + roleId).getKeys();
        List<String> roleIds = new ArrayList<>();
        for (String roleKey : roleKeys) {
            List<String> perms = (List<String>) fig.getList("Config.Roles." + roleId + "." + roleKey + ".Permissions");
            if (perms.contains("REMOVE"))
                roleIds.add(roleKey);
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
