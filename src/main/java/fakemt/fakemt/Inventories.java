package fakemt.fakemt;

import fakemt.fakemt.Kluizen.Kluis;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Inventories {
    private static final Functions functions = new Functions();
    public static Inventory kluisInventory(Player player, int pagina, Kluis kluis){
        Inventory inventory = Bukkit.createInventory(null, 54, "§6§lKluis §7van §8§l" + player.getName());

        //PAGE INFO
        inventory.setItem(8, functions.createItemstack(Material.PAPER, "§ePagina§f " + pagina, null));

        //PAGE SELECTOR
        inventory.setItem(17, functions.createItemstack(Material.STICK, "§6Scroll", functions.createArraylist("§7Left-Klik skip §f1", "§7Right-Klik ga §f1 §7pagina terug")));
        inventory.setItem(26, functions.createItemstack(Material.STICK, "§6Speed Scroll", functions.createArraylist("§7Left-Klik skip §f10", "§7Right-Klik ga §f10 §7pagina's terug")));
        inventory.setItem(35, functions.createItemstack(Material.STICK, "§6Speed Scroll", functions.createArraylist("§7Left-Klik skip §f10", "§7Right-Klik ga §f10 §7pagina's terug")));

        //VAULT INFO
        inventory.setItem(53, functions.createItemstack(Material.PAPER, "§eBeschikbare slots: §f" + kluis.getSlots(), functions.createArraylist("§eEigenaar: §f" + player.getName(), "§eKluisnaam:§f " + kluis.getKluisnaam())));

        for (int i = 0; i < 54; i+=9){
            inventory.setItem(i+7, functions.createGlass(null, 15, null));
            if (inventory.getItem(i+8) == null || inventory.getItem(i+8).getType() == Material.AIR){
                inventory.setItem(i+8, functions.createGlass(null, 15, null));
            }
        }

        fillInvFromSlot(inventory,  , 14);

        for (ItemStack item : kluis.getKluisLoot(String.valueOf(pagina))){
            inventory.addItem(item);
        }
        return inventory;
    }
    public static void fillInvFromSlot(Inventory inventory, int slot, int colorglass){
        if (slot < 0) slot = 0;
        for (int i = slot; i < 54; i++){
            if (inventory.getItem(i) != null)continue;
            inventory.setItem(i, functions.createGlass(" ", colorglass, null));
        }
    }
}
