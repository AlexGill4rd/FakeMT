package fakemt.fakemt.Huisdieren;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryEvents implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        ItemStack is = e.getCurrentItem();
        Inventory inventory = e.getInventory();

        if (is == null)return;
        if (e.getView().getTitle().equalsIgnoreCase("§7§l* §6§lPETS §7§l*")){
            e.setCancelled(true);
            if (e.getClickedInventory().getHolder() instanceof Player)return;
            if (is.getType() == Material.DIAMOND_HOE){
                if (e.getClick().isLeftClick()){
                    PetData petData = new PetData(player);
                    petData.despawnPets(true);

                    Pet toSpawn = new Pet(EntityType.valueOf(ChatColor.stripColor(is.getItemMeta().getDisplayName().toUpperCase())), player);
                    toSpawn.setActive(true);
                    petData.spawnPet(toSpawn);
                    player.closeInventory();
                }
            }

        }
    }

}
