package com.gb6.duels.objects;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gb6.duels.utils.Constants.MSG_UTIL;

public class MessageObject {
    private List<String> message;

    public MessageObject(String message) {
        this.message = Arrays.asList(message);
    }

    public MessageObject(List<String> message) {
        this.message = message;
    }

    public MessageObject color() {
        message = message.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        return this;
    }

    public MessageObject color(Player player) {
        this.message = color().getMessage().stream().map(s -> s.replace("%player%", player.getName())).collect(Collectors.toList());
        return this;
    }

    public MessageObject format(String toReplace, String replacement) {
        this.message = color().getMessage().stream().map(s -> s.replace(toReplace, replacement)).collect(Collectors.toList());
        return this;
    }

    public void send(Entity entity) {
        message.forEach(s -> entity.sendMessage(MSG_UTIL.prefix(s)));
    }

    public void send(CommandSender sender) {
        message.forEach(s -> sender.sendMessage(MSG_UTIL.prefix(s)));
    }

    public List<String> getMessage() {
        return message;
    }

}
