package com.gb6.duels.utils;


import com.gb6.duels.Duels;
import com.gb6.duels.enums.Setting;
import com.gb6.duels.managers.CommandManager;
import com.gb6.duels.objects.ArenaObject;
import com.gb6.duels.objects.CommandObject;
import com.gb6.duels.objects.DuelObject;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public interface Constants {

    List<CommandObject> COMMAND_LIST = new ArrayList<>();
    LinkedList<ArenaObject> ARENA_LIST =  new LinkedList<>();

    Map<UUID, DuelObject> DUEL_MAP = new HashMap<>();

    Duels INSTANCE = Duels.getInstance();

    CommandManager COMMAND_MANAGER = new CommandManager();

    MessageUtilities MSG_UTIL = new MessageUtilities();

}
