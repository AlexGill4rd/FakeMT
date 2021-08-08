package fakemt.fakemt.Tools;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class BottleStacker implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getHolder() instanceof Player){
            ItemStack cursor = player.getItemOnCursor();
            ItemStack cursorClone = player.getItemOnCursor().clone();
            cursorClone.setAmount(1);

            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR){
                e.setCancelled(true);
                for (ItemStack item : player.getInventory()){
                    if (item == null)continue;
                    ItemStack clone = item.clone();
                    clone.setAmount(1);
                    if (clone.equals(cursorClone)){
                        cursor.setAmount(cursor.getAmount()+item.getAmount());
                        item.setAmount(0);
                    }
                }
                return;
            }
            if (e.getCurrentItem() == null && cursorClone.getType() == Material.POTION){
                e.getInventory().setItem(e.getSlot(), cursor);
                player.setItemOnCursor(null);
                return;
            }
            if (e.getCurrentItem() == null)return;
            ItemStack clickedClone = e.getCurrentItem().clone();
            clickedClone.setAmount(1);

            if (cursorClone.equals(clickedClone) && cursorClone.getType() == Material.POTION){
                e.setCancelled(true);
                e.getCurrentItem().setAmount(e.getCurrentItem().getAmount()+cursor.getAmount());
                player.setItemOnCursor(null);
            }

        }

    }

}
