package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

public class FastItemEdit implements CommandExecutor {
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);
    Functions functions = new Functions();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("itemedit")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 0){
                    if (functions.hasPerm(player, "itemdit.help")){
                        player.sendMessage("§7§l* §6/Itemedit §elore §8[§7<§ccategory§7>§8]                    §7-> Pas de lore van het item aan");
                        player.sendMessage("§7§l* §6/Itemedit §ename §8[§7<§cdisplayname§7>§8]                §7-> Verander de displayname van een item");
                        player.sendMessage("§7§l* §6/Itemedit §edurability §8[§7<§cdurability§7>/<§crandom§7>/<§cpercentage§7>§8]  §7-> Pas de durability van een item aan");
                    }
                }else if (args.length == 1){
                    if (args[0].equalsIgnoreCase("lore")){
                        if (functions.hasPerm(player, "itemedit.lore")) {
                            if (isValidItem(player.getInventory().getItemInMainHand())){
                                setItemLore(player, player.getInventory().getItemInMainHand(), "Item");
                                player.sendMessage(functions.getMessage("EditLore"));
                            }else player.sendMessage(functions.getMessage("InvalidItem"));
                        }
                    }else if (args[0].equalsIgnoreCase("name")){
                        if (functions.hasPerm(player, "edititem.name")){
                            if (isValidItem(player.getInventory().getItemInMainHand())){
                                setItemDisplayname(player, player.getInventory().getItemInMainHand(), functions.getItemName(player.getInventory().getItemInMainHand()));
                                player.sendMessage(functions.getMessage("EditDisplayname"));
                            }else player.sendMessage(functions.getMessage("InvalidItem"));
                        }
                    }
                }else if (args.length == 2){
                    if (args[0].equalsIgnoreCase("lore")){
                        if (functions.hasPerm(player, "itemedit.lore")) {
                            if (isValidItem(player.getInventory().getItemInMainHand())){
                                setItemLore(player, player.getInventory().getItemInMainHand(), args[1]);
                                player.sendMessage(functions.getMessage("EditLore"));
                            }else player.sendMessage(functions.getMessage("InvalidItem"));
                        }
                    }else if (args[0].equalsIgnoreCase("durability")){
                        if (functions.hasPerm(player, "itemedit.durability")){
                            if (isValidItem(player.getInventory().getItemInMainHand())){
                                if (functions.isInt(args[1])) {
                                    setItemDurability(player, Integer.parseInt(args[1]));
                                    player.sendMessage(functions.getMessage("EditDurability"));
                                }else if (args[1].contains("%")){
                                    if (functions.isInt(args[1].replace("%", ""))){
                                        float procent = ((float) player.getInventory().getItemInMainHand().getType().getMaxDurability()/100) *Float.parseFloat(args[1].replace("%", ""));
                                        setItemDurability(player, (int) procent);
                                        player.sendMessage(functions.getMessage("EditDurability"));
                                    }else player.sendMessage(functions.getMessage("InvalidNumber"));
                                }else if (args[1].equalsIgnoreCase("true")){
                                    ItemStack playermainhand = player.getInventory().getItemInMainHand();
                                    Random r = new Random();
                                    int random = r.nextInt(playermainhand.getType().getMaxDurability());
                                    setItemDurability(player, random);
                                    player.sendMessage(functions.getMessage("EditDurability"));
                                }
                                else player.sendMessage(functions.getMessage("InvalidNumber"));
                            }else player.sendMessage(functions.getMessage("InvalidItem"));
                        }
                    }
                }
                if (args.length > 1){
                    if (args[0].equalsIgnoreCase("name")){
                        if (functions.hasPerm(player, "itemedit.name")){
                            if (isValidItem(player.getInventory().getItemInMainHand())){
                                StringBuilder displayname = new StringBuilder();
                                for (int i = 1; i < args.length; i++) displayname.append(args[i]).append(" ");
                                setItemDisplayname(player, player.getInventory().getItemInMainHand(), displayname.toString());
                                player.sendMessage(functions.getMessage("EditDisplayname"));
                            }else player.sendMessage(functions.getMessage("InvalidItem"));
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    private boolean isValidItem(ItemStack itemStack){return itemStack.getType() != Material.AIR; }
    private void setItemDurability(Player player, int durability){
        ItemStack mainhand = player.getInventory().getItemInMainHand();
        if (mainhand.getType().getMaxDurability() - durability < 0)mainhand.setDurability((short) 0);
        else mainhand.setDurability((short) (mainhand.getType().getMaxDurability() - durability));
    }
    private void setItemDisplayname(Player player, ItemStack itemStack, String displayname){ player.getInventory().setItemInMainHand(functions.editItemMeta(itemStack, displayname, null)); }
    private void setItemLore(Player player, ItemStack item, String category){
        int season = plugin.getConfig().getInt("Season");
        ArrayList<String> lore = functions.getArray("DefaultLore");
        lore.forEach(s -> lore.set(lore.indexOf(s), s.replace("<category>", StringUtils.capitalize(ChatColor.translateAlternateColorCodes('&', category.toLowerCase()))).replace("<season>", String.valueOf(season))));
        player.getInventory().setItemInMainHand(functions.editItemMeta(item, null, lore));
    }
}
