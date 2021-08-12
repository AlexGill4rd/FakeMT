package fakemt.fakemt.Huisdieren;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e){
        Player player = e.getPlayer();
        PetData petData = new PetData(player);
        if (petData.hasLoadedPet())
            petData.despawnPets(false);
    }
}
