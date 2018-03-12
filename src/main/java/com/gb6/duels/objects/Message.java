package com.gb6.duels.objects;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.gb6.duels.utils.Constants.*;

public class Message {
    private List<String> message;

    public Message(String message) {
        this.message = Arrays.asList(INSTANCE.getConfig().getString("messages." + message));
        this.color();
    }

    public Message(String message, Boolean list) {
        this.message = Arrays.asList(INSTANCE.getConfig().getString("messages." + message));
        this.color();
    }

    private Message color() {
        message = message.stream().map(s -> ChatColor.translateAlternateColorCodes('&', s)).collect(Collectors.toList());
        return this;
    }

    public Message color(Player player) {
        this.message = color().getMessage().stream().map(s -> s.replace("%player%", player.getName())).collect(Collectors.toList());
        return this;
    }

    public Message format(String toReplace, String replacement) {
        this.message = color().getMessage().stream().map(s -> s.replace("%" + toReplace + "%", replacement)).collect(Collectors.toList());
        return this;
    }

    public void send(Entity entity) {
        message.forEach(s -> entity.sendMessage(PREFIX + s));
    }

    public void send(CommandSender sender) {
        message.forEach(s -> sender.sendMessage(PREFIX + s));
    }

    private List<String> getMessage() {
        return message;
    }

}
