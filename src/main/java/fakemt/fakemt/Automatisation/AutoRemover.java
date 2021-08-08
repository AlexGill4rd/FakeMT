package fakemt.fakemt.Automatisation;

import fakemt.fakemt.Functions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class AutoRemover implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player player = e.getPlayer();
        Functions functions = new Functions();
        if (functions.getAnnoyingPeople().contains(player.getUniqueId().toString())){
            player.kickPlayer("§6Om deze server te joinen heb je een hoger IQ nodig!");
        }

    }

}
