package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class FastItemEdit implements CommandExecutor {

    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);
    Functions functions = new Functions();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("fastlore")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0){
                    if (functions.hasPerm(player, "fakemt.fastlore")){
                        setItemLore(player, player.getInventory().getItemInMainHand(), "Item");
                    }
                }else if (args.length == 1){
                    if (functions.hasPerm(player, "fakemt.fastlore")){
                        setItemLore(player, player.getInventory().getItemInMainHand(), args[0]);
                    }
                }
            }
        }else if (command.getName().equalsIgnoreCase("fastname")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0){
                    if (functions.hasPerm(player, "fakemt.fastname")){
                        setItemDisplayname(player, player.getInventory().getItemInMainHand(), functions.getItemName(player.getInventory().getItemInMainHand()));
                    }
                }
            }
        }
        return false;
    }
    private void setItemDisplayname(Player player, ItemStack itemStack, String displayname){
        player.getInventory().setItemInMainHand(functions.editItemMeta(itemStack, displayname, null));
    }
    private void setItemLore(Player player, ItemStack item, String category){
        int season = plugin.getConfig().getInt("Season");
        ArrayList<String> lore = functions.getArray("DefaultLore");
        lore.forEach(s -> lore.set(lore.indexOf(s), s.replace("<category>", StringUtils.capitalize(ChatColor.translateAlternateColorCodes('&', category.toLowerCase()))).replace("<season>", String.valueOf(season))));
        player.getInventory().setItemInMainHand(functions.editItemMeta(item, null, lore));
    }
}
