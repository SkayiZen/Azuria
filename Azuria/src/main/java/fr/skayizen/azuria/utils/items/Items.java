package fr.skayizen.azuria.utils.items;

import fr.skayizen.azuria.utils.classes.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Items {

    GREEN_PLACE_HOLDER(new ItemBuilder(Material.STAINED_GLASS_PANE)
            .withDisplayName(" ")
            .withDurability((short) 5)
            .build()),

    RED_PLACE_HOLDER(new ItemBuilder(Material.STAINED_GLASS_PANE)
            .withDisplayName(" ")
            .withDurability((short) 14)
            .build()),

    YELLOW_PLACE_HOLDER(new ItemBuilder(Material.STAINED_GLASS_PANE)
            .withDisplayName(" ")
            .withDurability((short) 4)
            .build());


    private final ItemStack item;

    Items(ItemStack item){
        this.item = item;
    }

    public ItemStack getItem() {
        return item;
    }

}
