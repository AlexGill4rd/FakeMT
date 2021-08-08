package fakemt.fakemt.ArmorstandStuff;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

public class ArmorstandPlace implements Listener {

    @EventHandler
    public void onArmorstandPlace(EntitySpawnEvent e){

        Entity ent = e.getEntity();
        if(ent instanceof ArmorStand) {
            ArmorStand armorStand = (ArmorStand) ent;
            armorStand.setArms(true);
        }
    }
}
