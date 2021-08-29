package fakemt.fakemt.Tools;

import fakemt.fakemt.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static fakemt.fakemt.FakeMT.servername;


public class InformationTab implements CommandExecutor, Listener {

    Functions functions = new Functions();
    String discordLink = "discord.gg/FakeMT";
    String wetboekLink = "mijnwetboek.nl";
    String webshopLink = "AlexNetwork.nl";
    String voteLink = "TopG/servers/AlexNetwork";

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("links")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (args.length == 0){
                    if (functions.hasPerm(player, servername + ".links")){
                        openInfoTab(player);
                    }
                }
            }
        }
        return false;
    }
    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if (e.getView().getTitle().equals("§7§l* §9§lInformatie §7§l*")){
            ItemStack is = e.getCurrentItem();
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            if (e.getClickedInventory() instanceof PlayerInventory)return;

            String itemname = is.getItemMeta().getDisplayName().toLowerCase();

            if (is.getType() == Material.SKULL_ITEM) {
                if (itemname.contains("discord")){
                    player.sendMessage("§6§l§m------------ §8§l[ §3§lDiscord §8§l] §6§l§m------------");
                    player.sendMessage("§f§l          * §7Discord: §6" + discordLink);
                }else if (itemname.contains("wetboek")){
                    player.sendMessage("§6§l§m------------ §8§l[ §3§lWetboek §8§l] §6§l§m------------");
                    player.sendMessage("§f§l          * §7Wetboek: §6" + wetboekLink);
                }else if (itemname.contains("webshop")){
                    player.sendMessage("§6§l§m------------ §8§l[ §3§lWebshop §8§l] §6§l§m------------");
                    player.sendMessage("§f§l          * §7Webshop: §6" + webshopLink);
                }else if (itemname.contains("vote")){
                    player.sendMessage("§6§l§m------------ §8§l[ §3§lVoting §8§l] §6§l§m------------");
                    player.sendMessage("§f§l          * §7Voting: §6" + voteLink);
                }
                player.closeInventory();
            }
        }

    }
    private void openInfoTab(Player player){
        Inventory inventory = Bukkit.createInventory(null, 9, "§7§l* §9§lInformatie §7§l*");
        inventory.setItem(1, functions.createHead(Bukkit.getOfflinePlayer("Metroidling"), "§7§l- §3§lDISCORD §7§l-", functions.createArraylist("§7Klik op het item om informatie", "§7over onze discord te ontvangen!")));
        inventory.setItem(3, functions.createHead(Bukkit.getOfflinePlayer("Minecat9000"), "§7§l- §6§lWETBOEK §7§l-", functions.createArraylist("§7Klik op het item om informatie", "§7over ons wetboek te ontvangen!")));
        inventory.setItem(5, functions.createHead(Bukkit.getOfflinePlayer("ElMarcosFTW"), "§7§l- §2§lWEBSHOP §7§l-", functions.createArraylist("§7Klik op het item om informatie", "§7over onze webshop  te ontvangen!")));
        inventory.setItem(7, functions.createHead(Bukkit.getOfflinePlayer("Push_red_button"), "§7§l- §b§lVOTE §7§l-", functions.createArraylist("§7Klik op het item om informatie", "§7over onze vote systeem te ontvangen!")));
        functions.fillInv(inventory, 15);
        player.openInventory(inventory);
    }
}
