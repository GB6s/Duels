package com.gb6.duels;

import com.gb6.duels.builders.inventory.GUI;
import com.gb6.duels.commands.DuelCommand;
import com.gb6.duels.listeners.DuelListeners;
import com.gb6.duels.listeners.InventoryClickListener;
import com.gb6.duels.listeners.InventoryCloseListener;
import com.gb6.duels.managers.CommandManager;
import com.gb6.duels.objects.Arena;
import com.gb6.duels.objects.Kit;
import com.gb6.duels.utils.GlowUtil;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.List;

import static com.gb6.duels.managers.FileManager.*;
import static com.gb6.duels.utils.Constants.*;

public final class Duels extends JavaPlugin {

    private static Duels instance;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        createFile("arenas");
        createFile("kits");
        load();

        getCommand("duels").setExecutor(new CommandManager());
        getCommand("duel").setExecutor(new DuelCommand());

        registerListeners();
        registerGlow();

        new CommandManager();

        if (KIT_LIST.stream().noneMatch(k -> k.getName().equals("default"))) {
            KIT_LIST.add(new Kit("default"));
        }
    }

    @Override
    public void onDisable() {
        save();
    }

    private void save() {
        writeJSON("arenas", ARENA_LIST);
        writeJSON("kits", KIT_LIST);
    }

    private void load() {
        ARENA_LIST.addAll(readJSON("arenas", new TypeToken<List<Arena>>() {
        }));

        KIT_LIST.addAll(readJSON("kits", new TypeToken<List<Kit>>() {
        }));
    }

    private void registerListeners() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryClickListener(), this);
        pm.registerEvents(new InventoryCloseListener(), this);
        pm.registerEvents(new DuelListeners(), this);
        pm.registerEvents(new GUI(), this);
    }

    private void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            GlowUtil glow = new GlowUtil(70);
            Enchantment.registerEnchantment(glow);
        } catch (IllegalArgumentException e) {
            return;
        }
    }


    public static Duels getInstance() {
        return instance;
    }
}
