package fakemt.fakemt.Automatisation;

import fakemt.fakemt.Functions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AutoEnchant implements CommandExecutor {
    Functions functions = new Functions();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("autoenchant")) {
            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (args.length == 1) {
                    if (functions.hasPerm(player, "autoenchant.enchant")){
                        String enchantment = args[0].toUpperCase();
                        Enchantment enchant = Enchantment.getByName(enchantment);
                        ItemStack[] playerinventory = player.getInventory().getContents();
                        if (enchant == null){
                            player.sendMessage(functions.getMessage("InvalidEnchant"));
                            return true;
                        }
                        int random = 1;
                        String previous = "";
                        for (ItemStack item : playerinventory){
                            if (item == null)continue;
                            if (!item.getType().isItem())continue;
                            String itemname = "";
                            String[] split = new String[0];
                            if (item.hasItemMeta()){
                                if (item.getItemMeta().getDisplayName() != null)itemname = item.getItemMeta().getDisplayName();
                                split = itemname.split(" ");
                            }
                            if (split.length > 0){
                                if (split[0].contains("-")){
                                    if (!previous.toLowerCase().contains(split[1].toLowerCase())){
                                        Random r = new Random();
                                        random = r.nextInt(enchant.getMaxLevel()) + 1;
                                    }
                                }else if (!previous.toLowerCase().contains(split[0].toLowerCase())) {
                                    Random r = new Random();
                                    random = r.nextInt(enchant.getMaxLevel()) + 1;
                                }
                            }else{
                                Random r = new Random();
                                random = r.nextInt(enchant.getMaxLevel()) + 1;
                            }

                            item.addEnchantment(enchant, random);
                            if (item.hasItemMeta()){
                                if (item.getItemMeta().getDisplayName() != null)previous = item.getItemMeta().getDisplayName();
                            }
                        }
                        player.sendMessage(functions.getMessage("AutoenchantSuccesvol"));
                    }
                }
            }
        }
        return false;
    }
}