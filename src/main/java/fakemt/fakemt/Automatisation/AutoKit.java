package fakemt.fakemt.Automatisation;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;

import static fakemt.fakemt.Head.Configs.customConfigFile3;

public class AutoKit implements CommandExecutor, Listener {

    Functions functions = new Functions();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("kit")){
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length == 1){
                    if (functions.hasPerm(player, "kit.get")){
                        if (isValidKit(getValidKitname(args[0]))){
                            if (!getKitLoot(getValidKitname(args[0])).isEmpty()){
                                for (ItemStack item : getKitLoot(getValidKitname(args[0]))){
                                    if (item == null)continue;
                                    player.getInventory().addItem(item);
                                }
                                player.sendMessage(functions.getMessage("KitReceived"));
                            }else
                                player.sendMessage(functions.getMessage("EmptyKit"));
                        }else
                            player.sendMessage(functions.getMessage("InvalidKit"));
                    }
                }else if (args.length == 2) {
                    if (args[0].equalsIgnoreCase("create")){
                        if (functions.hasPerm(player, "kit.create")){
                            String kitname = args[1];
                            ItemStack[] loot = player.getInventory().getContents();
                            if (!isValidKit(getValidKitname(kitname))){
                                Configs.getCustomConfig2().set("Kits." + kitname, loot);
                                functions.saveData();
                                player.sendMessage(functions.getMessage("KitCreated"));
                            }else{
                                player.sendMessage(functions.getMessage("DoubleKit"));
                            }
                        }
                    }else if (args[0].equalsIgnoreCase("remove")){
                        if (functions.hasPerm(player, "kit.remove")){
                            String kitname = args[1];
                            if (isValidKit(getValidKitname(kitname))){
                                Configs.getCustomConfig2().set("Kits." + getValidKitname(kitname), null);
                                if (getDefaultKitName() != null){
                                    if (getDefaultKitName().equalsIgnoreCase(getValidKitname(kitname))){
                                        Configs.getCustomConfig2().set("Kits.default", null);
                                    }
                                }
                                functions.saveData();
                                player.sendMessage(functions.getMessage("Kit Removed"));
                            }else player.sendMessage(functions.getMessage("InvalidKit"));
                        }
                    }
                }else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("create")){
                        if (args[2].equalsIgnoreCase("true")){
                            if (functions.hasPerm(player, "kit.create.default")){
                                String kitname = args[1];
                                ItemStack[] loot = player.getInventory().getContents();
                                if (!isValidKit(getValidKitname(kitname))){
                                    Configs.getCustomConfig2().set("Kits." + kitname, loot);
                                    Configs.getCustomConfig2().set("Kits.default", kitname);
                                    functions.saveData();
                                    player.sendMessage(functions.getMessage("DefaultKitCreated"));
                                }else
                                    player.sendMessage(functions.getMessage("DoubleKit"));
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!functions.hasJoinedBefore(player)){
            player.getInventory().setContents(getStarterKitLoot());
            Configs.getCustomConfig3().set("Players." + player.getUniqueId().toString() + ".Displayname", player.getName());
            Configs.getCustomConfig3().set("Players." + player.getUniqueId().toString() + ".UniqueID", player.getUniqueId().toString());
            Configs.getCustomConfig3().set("Players." + player.getUniqueId().toString() + ".IP", player.getAddress().getHostName());
            Configs.getCustomConfig3().set("Players." + player.getUniqueId().toString() + ".FirstPlayed", player.getFirstPlayed());
            saveData();
        }
    }
    private ItemStack[] getStarterKitLoot(){
        ItemStack[] loot = new ItemStack[0];
        if (!Configs.getCustomConfig2().contains("Kits.default")){
            return loot;
        }
        String kitname = Configs.getCustomConfig2().getString("Kits.default");
        return Configs.getCustomConfig2().getList("Kits." + kitname).toArray(new ItemStack[0]);
    }
    private String getDefaultKitName(){
        return Configs.getCustomConfig2().contains("Kits.default") ? Configs.getCustomConfig2().getString("Kits.default") : null;
    }
    private ArrayList<ItemStack> getKitLoot(String kit){
        ArrayList<ItemStack> items = new ArrayList<>();
        if (isValidKit(getValidKitname(kit)))
            items = (ArrayList<ItemStack>) Configs.getCustomConfig2().getList("Kits." + getValidKitname(kit));
        return items;
    }
    private String getValidKitname(String kitname){
        if (Configs.getCustomConfig2().contains("Kits." + kitname)){
            return kitname;
        }else if (Configs.getCustomConfig2().contains("Kits." + StringUtils.capitalize(kitname.toLowerCase()))){
            return StringUtils.capitalize(kitname.toLowerCase());
        }else if (Configs.getCustomConfig2().contains("Kits." + kitname.toLowerCase())){
            return kitname.toLowerCase();
        }else if (Configs.getCustomConfig2().contains("Kits." + kitname.toUpperCase())){
            return kitname.toUpperCase();
        }
        return kitname;
    }
    private void saveData(){
        try {
            Configs.getCustomConfig3().save(customConfigFile3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isValidKit(String kit){
        return Configs.getCustomConfig2().contains("Kits." + kit);
    }
}
