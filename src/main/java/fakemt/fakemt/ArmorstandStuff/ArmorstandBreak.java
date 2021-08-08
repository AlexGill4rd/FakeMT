package fakemt.fakemt.ArmorstandStuff;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorstandBreak implements Listener {

    @EventHandler
    public void onArmorstandBreak(EntityDamageByEntityEvent e){

        Player player = (Player) e.getDamager();
        Entity damaged = e.getEntity();
        if (damaged instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) damaged;
            if (player.getGameMode() == GameMode.CREATIVE){
                for (ItemStack equipped : armorStand.getEquipment().getArmorContents()){
                    player.getInventory().addItem(equipped);
                }
                player.getInventory().addItem(armorStand.getEquipment().getItemInMainHand());
                player.getInventory().addItem(armorStand.getEquipment().getItemInOffHand());
            }
        }
    }
}
