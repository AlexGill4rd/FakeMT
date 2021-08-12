package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PetInventories {
    Functions functions = new Functions();
    public void openMainMenu(Player player){
        PetData petData = new PetData(player);
        Inventory inventory = Bukkit.createInventory(null, 27, "§7§l* §6§lPETS §7§l*");

        for (Pet pet : petData.getPets())
            inventory.addItem(functions.createItemstack(Material.DIAMOND_HOE, ChatColor.BLUE + pet.getEntityType().name(), functions.createArraylist("§7Nickname: §r" + pet.getName(), "§7Left-Click om het huisdier te spawnen", "§7Right-Click om pet setting te openen")));

        functions.fillInv(inventory, 15);
        player.openInventory(inventory);
    }

}
