package com.gb6.duels.objects;

import com.gb6.duels.enums.Requirement;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandObject {
    @Getter private String syntax;
    @Getter private String permission;
    @Getter private Requirement requirement;

    public CommandObject(String syntax, String permission, Requirement requirement) {
        this.syntax = syntax;
        this.permission = permission;
        this.requirement = requirement;
    }

    public void execute(CommandSender sender, List<String> args) {

    }
}