package fakemt.fakemt.Huisdieren;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinPetSpawn implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        PetData petData = new PetData(player);
        if (petData.hasLoadedPet())
            petData.spawnPet(petData.getActivePet());
    }
}
