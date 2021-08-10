package fakemt.fakemt.ArmorstandStuff;

import org.bukkit.GameMode;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class ArmorstandBreak implements Listener {

    @EventHandler
    public void onArmorstandBreak(EntityDamageByEntityEvent e){

        Player player = (Player) e.getDamager();
        Entity damaged = e.getEntity();
        if (damaged instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) damaged;
            if (player.getGameMode() == GameMode.CREATIVE){
                for (ItemStack item : getArmorstandLoot(armorStand))player.getInventory().addItem(item);
            }
        }
    }
    private ArrayList<ItemStack> getArmorstandLoot(ArmorStand armorStand){
        ArrayList<ItemStack> armorstandloot = new ArrayList<>();
        armorstandloot.add(armorStand.getEquipment().getItemInMainHand());
        armorstandloot.add(armorStand.getEquipment().getItemInOffHand());
        armorstandloot.addAll(Arrays.asList(armorStand.getEquipment().getArmorContents()));
        return armorstandloot;
    }
}
