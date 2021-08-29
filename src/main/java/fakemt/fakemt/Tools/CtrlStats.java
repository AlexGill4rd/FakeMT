package fakemt.fakemt.Tools;

import fakemt.fakemt.Functions;
import nl.minetopiasdb.api.playerdata.PlayerManager;
import nl.minetopiasdb.api.playerdata.objects.SDBPlayer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;

import java.util.concurrent.TimeUnit;

import static fakemt.fakemt.FakeMT.servername;

public class CtrlStats implements Listener {
    Functions functions = new Functions();
    @EventHandler
    public void onPlayerInteract(PlayerInteractAtEntityEvent e){

        Player player = e.getPlayer();

        if (e.getRightClicked() instanceof Player){
            if (functions.hasPerm(player, servername + ".stats")){
                if (player.isSneaking()){
                    Player target = (Player) e.getRightClicked();
                    openControlMenu(player, target);
                }
            }
        }
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if (e.getView().getTitle().equals("§7§l-= §6§lPlayer Info §7§l=-")){
            e.setCancelled(true);
        }
    }
    private void openControlMenu(Player player, Player target){

        Inventory inventory = Bukkit.createInventory(null, 54, "§7§l-= §6§lPlayer Info §7§l=-");

        //ARMOR
        inventory.setItem(10, target.getInventory().getHelmet());
        inventory.setItem(19, target.getInventory().getChestplate());
        inventory.setItem(28, target.getInventory().getLeggings());
        inventory.setItem(37, target.getInventory().getBoots());

        //HANDS
        inventory.setItem(21, target.getInventory().getItemInMainHand());
        inventory.setItem(30, target.getInventory().getItemInOffHand());

        //STATISTIEKEN
        SDBPlayer sdbPlayer = PlayerManager.getOnlinePlayer(target.getUniqueId());

        float luckyshards = (float) (Math.round(sdbPlayer.getLuckyShards() * 10000))/10000;

        inventory.setItem(23, functions.createHead(target, "§7§l- §6§lOwner: §f" + ChatColor.stripColor(target.getDisplayName()) + " §7§l-", null));
        inventory.setItem(24, functions.createHead(target, "§7§l- §6§lTime: §f" + calculateTime(target.getStatistic(Statistic.PLAY_ONE_TICK)/20) + " §7§l-", null));
        inventory.setItem(25, functions.createHead(target, "§7§l- §6§lLuckyshards: §f" + luckyshards + " §7§l-", null));
        inventory.setItem(32, functions.createHead(target, "§7§l- §6§lFitheid: §f" + sdbPlayer.getFitness().getTotalFitness() + " §7§l-", null));
        inventory.setItem(33, functions.createHead(target, "§7§l- §6§lLevel: §f" + sdbPlayer.getLevel() + " §7§l-", null));
        inventory.setItem(34, functions.createHead(target, "§7§l- §6§lPrefix: §f" + sdbPlayer.getPrefix() + " §7§l-", null));

        functions.fillInv(inventory, 15);

        player.openInventory(inventory);
    }
    private String calculateTime(long seconds) {
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds) * 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) * 60);

        return day + " dagen " + hours + " uur " + minute + " minuten " + second + " seconden";

    }
}
