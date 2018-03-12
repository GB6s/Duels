package com.gb6.duels.builders.inventory;

import com.gb6.duels.builders.item.ItemBuilder;
import com.gb6.duels.events.GUIClickEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class Icon {

    @Getter @Setter private ItemStack itemStack;
    @Setter @Accessors(chain = true) private Consumer<GUIClickEvent> callBack;

    public Icon() {
        itemStack = new ItemStack(Material.AIR);
    }

    public Icon(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void onClick(GUIClickEvent event) {
        if (this.callBack != null) {
            this.callBack.accept(event);
        }
    }

    public Icon setFiller() {
        itemStack = new ItemBuilder(Material.STAINED_GLASS_PANE, 1, 7);
        return this;
    }

    public boolean isFiller() {
        return itemStack.getType() == Material.STAINED_GLASS_PANE && itemStack.getData() != null && itemStack.getData().getData() == 7;
    }
}
