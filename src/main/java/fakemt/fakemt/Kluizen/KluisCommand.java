package fakemt.fakemt.Kluizen;

import fakemt.fakemt.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class KluisCommand implements CommandExecutor {
    Functions functions = new Functions();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("kluis")){

            if (sender instanceof Player){

                Player player = (Player) sender;
                PlayerVault playerVault = new PlayerVault(player);
                if (args.length == 0){
                    if (functions.hasPerm(player, "kluis.help")) sendCommandHelp(player);
                }else if (args.length == 1){
                    if (functions.hasPerm(player, "kluis.help")) sendCommandHelp(player);
                }
                if (args.length >= 2){
                    if (args[0].equalsIgnoreCase("give")){
                        if (functions.hasPerm(player, "vault.give")){
                            StringBuilder vaultNameBuilder = new StringBuilder();
                            for (int i = 1; i < args.length; i++)vaultNameBuilder.append(args[i]).append(" ");
                            String vaultname = "§6" + vaultNameBuilder.toString().trim();
                            ItemStack vault = functions.createItemstack(playerVault.getVaultMaterial(), vaultname, functions.createArraylist("§7Plaats dit neer om een kluis te maken!"));
                            player.getInventory().addItem(vault);
                            player.sendMessage(functions.getMessage("Vault Given"));
                        }
                    }
                }

            }

        }

        return false;
    }
    private void sendCommandHelp(Player player){
        player.sendMessage("§6§l§m---");
        player.sendMessage("§7§l    * §6/Kluis §ehelp");
        player.sendMessage("§7§l    * §6/Kluis §egive §7<§cvaultname§7>");
    }
}
