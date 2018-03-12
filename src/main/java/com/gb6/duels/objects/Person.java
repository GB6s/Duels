package com.gb6.duels.objects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Person {
    @Getter private Player player;
    @Getter private Dueler dueler;
    @Getter private ArrayList<Integer> slots;
    @Getter private Integer confirmSlot;
    @Getter @Setter private ItemStack[] stakes;

    Person(Dueler dueler, Player player, ArrayList<Integer> slots, Integer confirmSlot) {
        this.dueler = dueler;
        this.player = player;
        this.slots = slots;
        this.confirmSlot = confirmSlot;
    }
}