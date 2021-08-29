package fakemt.fakemt.Tools;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.UUID;

public class BottleStacker implements Listener {
    ArrayList<UUID> cooldown = new ArrayList<>();
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if (e.getInventory().getHolder() instanceof Player){
            if (cooldown.contains(player.getUniqueId()))return;
            cooldown.add(player.getUniqueId());

            ItemStack cursor = player.getItemOnCursor();
            ItemStack cursorClone = player.getItemOnCursor().clone();
            cursorClone.setAmount(1);

            if (e.getAction() == InventoryAction.COLLECT_TO_CURSOR){
                e.setCancelled(true);
                boolean stack = false;
                for (ItemStack item : player.getInventory()){
                    if (item == null)continue;
                    if (stack)continue;
                    ItemStack clone = item.clone();
                    clone.setAmount(1);
                    if (clone.equals(cursorClone)){
                        if (cursor.getAmount()+item.getAmount() > 64) {
                            item.setAmount(item.getAmount()-(64-cursor.getAmount()));
                            cursor.setAmount(64);
                            stack = true;
                        }else {
                            cursor.setAmount(cursor.getAmount() + item.getAmount());
                            item.setAmount(0);
                        }
                    }
                }
                cooldown.remove(player.getUniqueId());
                return;
            }
            if (e.getCurrentItem() == null && cursorClone.getType() == Material.POTION){
                if (e.getSlot() >= 0){
                    e.getInventory().setItem(e.getSlot(), cursor);
                    player.setItemOnCursor(null);
                    cooldown.remove(player.getUniqueId());
                    return;
                }
            }
            if (e.getCurrentItem() == null){
                cooldown.remove(player.getUniqueId());
                return;
            }
            ItemStack clickedClone = e.getCurrentItem().clone();
            clickedClone.setAmount(1);

            if (cursorClone.equals(clickedClone) && cursorClone.getType() == Material.POTION){
                e.setCancelled(true);
                if (e.getCurrentItem().getAmount() != 64){
                    if (e.getCurrentItem().getAmount()+cursor.getAmount() > 64){
                        cursor.setAmount(cursor.getAmount()-(64-e.getCurrentItem().getAmount()));
                        e.getCurrentItem().setAmount(64);
                    }else{
                        player.setItemOnCursor(null);
                        e.getCurrentItem().setAmount(e.getCurrentItem().getAmount() + cursor.getAmount());
                    }
                }
            }
            cooldown.remove(player.getUniqueId());

        }

    }
}
