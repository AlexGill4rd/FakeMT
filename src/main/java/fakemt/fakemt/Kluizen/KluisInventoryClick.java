package fakemt.fakemt.Kluizen;

import fakemt.fakemt.Functions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static fakemt.fakemt.Inventories.kluisInventory;

public class KluisInventoryClick implements Listener {
    Functions functions = new Functions();
    @EventHandler
    public void onVaultInventoryClick(InventoryClickEvent e){

        String invnaam = e.getView().getTitle();
        Player player = (Player) e.getWhoClicked();
        PlayerVault playerVault = new PlayerVault(player);
        ItemStack is = e.getCurrentItem();

        if (is == null || is.getType() == Material.AIR)return;

        String itemName = functions.getItemDisplayname(is);

        if (invnaam.contains("Kluis")){
            Kluis kluis = playerVault.getKluisByItemstack(e.getInventory().getItem(53));
            if (!(e.getClickedInventory() instanceof PlayerInventory)){
                if (!playerVault.isVaultItemSlot(e.getSlot(), e.getInventory(), kluis)){
                    e.setCancelled(true);
                    if (is.getType() == Material.STICK) {
                        if (itemName.equals("§6Scroll")) {
                            if (e.getClick().isLeftClick()){
                                playerVault.savePageData(e.getInventory(), kluis);
                                player.openInventory(kluisInventory(player, playerVault.getCurrentPage(e.getInventory())+1, kluis));
                            }else if (e.getClick().isRightClick()){
                                if (playerVault.getCurrentPage(e.getInventory())-1 > 0){
                                    playerVault.savePageData(e.getInventory(), kluis);
                                    player.openInventory(kluisInventory(player, playerVault.getCurrentPage(e.getInventory())-1, kluis));
                                }
                            }
                        } else if (itemName.equals("§6Speed Scroll")) {

                        }
                    }
                }
            }

        }
    }
    @EventHandler
    public void onVaultClose(InventoryCloseEvent e){

        String invnaam = e.getView().getTitle();
        Player player = (Player) e.getPlayer();
        PlayerVault playerVault = new PlayerVault(player);

        if (invnaam.contains("Kluis")){
            Kluis kluis = playerVault.getKluisByItemstack(e.getInventory().getItem(53));
            playerVault.savePageData(e.getInventory(), kluis);
        }
    }
}
