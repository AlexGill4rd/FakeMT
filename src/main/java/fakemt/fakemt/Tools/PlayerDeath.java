package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeath implements Listener {
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);
    Functions functions = new Functions();
    @EventHandler
    public void onPlayerDead(PlayerDeathEvent e){

        Player player = e.getEntity();
        Player target = e.getEntity().getKiller();

        String wapennaam = functions.getItemDisplayname(target.getInventory().getItemInMainHand());
        String deathmessage = functions.color(plugin.getConfig().getString("DeathMessage").replace("<player>", player.getName()).replace("<killer>", target.getName()).replace("<wapen>", wapennaam));

        e.setDeathMessage(deathmessage);
    }

}
