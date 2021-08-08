package fakemt.fakemt.Automatisation;

import fakemt.fakemt.Functions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AutoLore implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("autolore")){

            if (sender instanceof Player){

                Player player = (Player) sender;
                Functions functions = new Functions();

                if (args.length == 0){
                    if (functions.hasPerm(player, "fakemt.autolore")){
                        ItemStack[] playerinventory = player.getInventory().getStorageContents();

                        for (int i = 0; i < playerinventory.length; i++){
                            ItemStack item = player.getInventory().getItem(i);
                            if (item == null)continue;
                            if (item.getType().isItem()){
                                if (functions.isDrug(item)){
                                    player.getInventory().setItem(i, functions.editItemMeta(item, null, functions.getArray("DrugLore")));
                                }else if (item.getType().isEdible()){
                                    player.getInventory().setItem(i, functions.editItemMeta(item, null, functions.getArray("FoodLore")));
                                }else if (functions.isArmor(item)){
                                    player.getInventory().setItem(i, functions.editItemMeta(item, null, functions.getArray("ArmorLore")));
                                }else if (functions.isCriminalItem(item)){
                                    player.getInventory().setItem(i, functions.editItemMeta(item, null, functions.getArray("IllegalLore")));
                                }
                            }
                        }
                        player.sendMessage("§6Je hebt elk item een gepaste lore gegeven!");
                    }
                }else if (args.length == 1){
                    if (args[0].equalsIgnoreCase("true")){
                        if (functions.hasPerm(player, "fakemt.autolore")){
                            ItemStack[] playerinventory = player.getInventory().getStorageContents();

                            for (int i = 0; i < playerinventory.length; i++){
                                ItemStack item = player.getInventory().getItem(i);
                                if (item == null)continue;
                                if (item.getType().isItem()){
                                    if (functions.isDrug(item)){
                                        player.getInventory().setItem(i, functions.editItemMeta(item, functions.getItemName(item), functions.getArray("DrugLore")));
                                    }else if (item.getType().isEdible()){
                                        player.getInventory().setItem(i, functions.editItemMeta(item, functions.getItemName(item), functions.getArray("FoodLore")));
                                    }else if (functions.isArmor(item)){
                                        player.getInventory().setItem(i, functions.editItemMeta(item, functions.getItemName(item), functions.getArray("ArmorLore")));
                                    }else if (functions.isCriminalItem(item)){
                                        player.getInventory().setItem(i, functions.editItemMeta(item, functions.getItemName(item), functions.getArray("IllegalLore")));
                                    }
                                }
                            }
                            player.sendMessage("§6Je hebt elk item een gepaste lore gegeven!");
                        }
                    }
                }
            }
        }
        return false;
    }
}
