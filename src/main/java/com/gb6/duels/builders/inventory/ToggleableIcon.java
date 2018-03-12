package com.gb6.duels.builders.inventory;

import com.gb6.duels.events.GUIClickEvent;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class ToggleableIcon extends Icon {

    @Getter private boolean toggled;

    public ToggleableIcon(ItemStack itemStack) {
        super(itemStack);
    }

    public void toggle(GUIClickEvent event, Consumer<GUIClickEvent> action) {
        action.accept(event);
        toggled ^= true;
    }


}
