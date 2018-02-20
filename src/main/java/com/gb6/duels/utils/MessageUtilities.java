package com.gb6.duels.utils;

import com.gb6.duels.Duels;
import com.gb6.duels.objects.MessageObject;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageUtilities {
    private static FileConfiguration configuration;
    private static String prefix;

    public MessageUtilities() {
        configuration = Duels.getInstance().getConfig();
        prefix = ChatColor.translateAlternateColorCodes('&', configuration.getString("prefix"));
    }

    public MessageObject getMessage(String string) {
        return new MessageObject(configuration.getString("messages." + string));
    }

    public MessageObject getMessageList(String string) {
        return new MessageObject(configuration.getStringList("messages." + string));
    }

    public String prefix(String s) {
        return prefix + " " + s;
    }

    public String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
