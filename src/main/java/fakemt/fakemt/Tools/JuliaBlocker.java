package fakemt.fakemt.Tools;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.*;

import static fakemt.fakemt.FakeMT.blocked;

public class JuliaBlocker implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("block")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (player.getUniqueId().toString().equals("f13d0a6b-f3b1-4ff3-93f3-4eeab6dc1d70")){
                    if (args.length == 1){
                        Player target = Bukkit.getPlayer(args[0]);
                        if (target == null){
                            player.sendMessage("§cDeze speler is niet online of bestaat niet!");
                            return true;
                        }
                        if (blocked.contains(target.getUniqueId())){
                            blocked.remove(target.getUniqueId());
                            player.sendMessage("§eJe hebt §f" + target.getName() + " §egeunblocked!");
                        }else{
                            blocked.add(target.getUniqueId());
                            player.sendMessage("§eJe hebt §f" + target.getName() + " §egeblocked!");
                        }
                    }
                }
            }
        }
        return false;
    }
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){
        Player player = e.getPlayer();
        if (blocked.contains(player.getUniqueId())) e.setCancelled(true);
    }
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if (blocked.contains(player.getUniqueId())) e.setCancelled(true);
    }
    @EventHandler
    public void playerItemDropEvent(PlayerDropItemEvent e){
        Player player = e.getPlayer();
        if (blocked.contains(player.getUniqueId())) e.setCancelled(true);
    }
    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e){
        Player player = e.getPlayer();
        if (blocked.contains(player.getUniqueId())) e.setCancelled(true);
    }
    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e){
        Player player = e.getPlayer();
        if (blocked.contains(player.getUniqueId())) e.setCancelled(true);
    }
}
