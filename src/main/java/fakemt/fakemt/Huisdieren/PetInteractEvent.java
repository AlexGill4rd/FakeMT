package fakemt.fakemt.Huisdieren;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PetInteractEvent implements Listener {

    @EventHandler
    public void onPetHit(EntityDamageByEntityEvent e){

        if (e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if (!(e.getEntity() instanceof Player)){
                Entity damaged = e.getEntity();
                if (!damaged.getCustomName().equals("")) {
                    e.setCancelled(true);
                }

            }
        }


    }

}
