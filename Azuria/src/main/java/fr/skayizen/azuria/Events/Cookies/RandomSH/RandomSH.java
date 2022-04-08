package fr.skayizen.azuria.Events.Cookies.RandomSH;

import fr.minuskube.inv.ClickableItem;
import fr.minuskube.inv.SmartInventory;
import fr.minuskube.inv.content.InventoryContents;
import fr.minuskube.inv.content.InventoryProvider;
import fr.skayizen.azuria.Utils.Classes.ItemBuilder;
import fr.skayizen.azuria.Utils.Items.Items;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class RandomSH implements InventoryProvider {


    public static final SmartInventory INVENTORY = SmartInventory.builder()
            .id("CookieRdmSH")
            .title("§6§lCookie Random Shiny !")
            .provider(new RandomSH())
            .size(5, 9)
            .closeable(true)
            .build();



    @Override
    public void init(Player player, InventoryContents inventoryContents) {

        inventoryContents.set(0, 4, ClickableItem.empty(Items.YELLOW_PLACE_HOLDER.getItem()));

        for (int i = 0; i < 4; i++) {
            inventoryContents.set(0, i, ClickableItem.empty(Items.GREEN_PLACE_HOLDER.getItem()));
            inventoryContents.set(1 + i, 0, ClickableItem.empty(Items.GREEN_PLACE_HOLDER.getItem()));
            inventoryContents.set(4, i, ClickableItem.empty(Items.GREEN_PLACE_HOLDER.getItem()));
        }

        for (int i = 0; i < 4; i++) {

            inventoryContents.set(0, 5+i, ClickableItem.empty(Items.RED_PLACE_HOLDER.getItem()));
            inventoryContents.set(1+i, 8, ClickableItem.empty(Items.RED_PLACE_HOLDER.getItem()));
            inventoryContents.set(4, 5+i, ClickableItem.empty(Items.RED_PLACE_HOLDER.getItem()));
        }


        inventoryContents.set(4, 4, ClickableItem.empty(Items.YELLOW_PLACE_HOLDER.getItem()));

        player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);



        ItemStack greenGGemme = new ItemStack(5363);
        ItemStack yes = new ItemBuilder(greenGGemme)
                .withDisplayNameColoured("§aConfirmer")
                .withLoreColoured(Arrays.asList("§f", "§fUne fois clicker dessus, tu", "§frecevras un Pokemon shiny aléatoire. "))
                .build();
        inventoryContents.set(2, 3, ClickableItem.of(yes, e -> {

            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "pokegive " + player.getName() + " random s");
            player.sendMessage("§6Cookie §f» Vous avez utiliser votre Cookie Random Shiny !");
            RandomSH.INVENTORY.close(player);

            player.getItemInHand().setAmount(player.getItemInHand().getAmount() -1);

        }));

        ItemStack redGemme = new ItemStack(5023);
        ItemStack no = new ItemBuilder(redGemme)
                .withDisplayNameColoured("§cAnnuler")
                .withLoreColoured(Arrays.asList("§f", "§fUne fois clicker dessus, tu", "§fgarderas ton cookie et annuler le procéssus. "))
                .build();
        inventoryContents.set(2, 5, ClickableItem.of(no, e -> {
            player.sendMessage("§6Cookie §f» Vous avez annuler le procéssus de votre Cookie Random Shiny !");
            RandomSH.INVENTORY.close(player);

        }));

    }

    @Override
    public void update(Player player, InventoryContents inventoryContents) {



    }
}
