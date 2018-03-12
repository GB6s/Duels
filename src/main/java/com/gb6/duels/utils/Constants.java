package com.gb6.duels.utils;


import com.gb6.duels.Duels;
import com.gb6.duels.builders.inventory.GUI;
import com.gb6.duels.managers.ConfigurationManager;
import com.gb6.duels.objects.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;

public interface Constants {

    List<Command> COMMAND_LIST = new ArrayList<>();
    ArrayList<Arena> ARENA_LIST = new ArrayList<>();
    ArrayList<Kit> KIT_LIST = new ArrayList<>();
    Map<Player, GUI> GUI_MAP = new HashMap<>();

    Map<UUID, Dueler> DUEL_MAP = new HashMap<>();
    Map<UUID, PlayerData> PLAYER_DATA = new HashMap<>();

    Duels INSTANCE = Duels.getInstance();

    TitleActionBar TAB = new TitleActionBar();
    ConfigurationManager CFM = new ConfigurationManager();
    GuiUtilities GUI_UTIL = new GuiUtilities();

    List<Integer> KIT_SLOTS = Arrays.asList(36, 37, 38, 39, 40, 41, 42, 43, 44, 49, 50, 51, 52);
    String PREFIX = ChatColor.translateAlternateColorCodes('&', INSTANCE.getConfig().getString("prefix")) + " ";


}
