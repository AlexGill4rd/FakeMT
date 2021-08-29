package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static fakemt.fakemt.FakeMT.servername;

public class Alert implements CommandExecutor {

    Functions functions = new Functions();
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("alert")){
            if (sender instanceof Player){
                Player player = (Player) sender;

                if (args.length == 0) {
                    if (functions.hasPerm(player, servername + ".alert.help")) {
                        player.sendMessage("§c/Alert <message> om een broadcast te versturen!");
                    }
                }else{
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("history")) {
                            if (functions.hasPerm(player, servername + ".alert.history")) {
                                player.sendMessage("§6§l§m------=()=------");
                                if (getBroadcastHistory().isEmpty())
                                    player.sendMessage("§7Geen broadcasts geregistreerd");
                                else for (String s : getBroadcastHistory()) player.sendMessage("§7- §f" + s);
                            }
                            return true;
                        }
                    }
                    if (functions.hasPerm(player, servername + ".alert.send")){
                        StringBuilder message = new StringBuilder();
                        for (String s : args) message.append(s).append(" ");
                        message = new StringBuilder(ChatColor.translateAlternateColorCodes('&', message.toString()));
                        sendServerBroadcast(message.toString());
                        saveBroadcast(message.toString());
                    }
                }
            }
        }
        return false;
    }
    private void sendServerBroadcast(String message){
        for (Player online : Bukkit.getServer().getOnlinePlayers()){
            online.sendMessage(functions.getServerPrefix() + ChatColor.translateAlternateColorCodes('&', message));
        }
    }
    private void saveBroadcast(String broadcast){
        ArrayList<String> broadcasts = new ArrayList<>();
        if (Configs.getCustomConfig2().contains("Broadcasts")) broadcasts = (ArrayList<String>) Configs.getCustomConfig2().getStringList("Broadcasts");
        broadcasts.add(broadcast);
        if (broadcasts.size() > 10)broadcasts.remove(0);
        Configs.getCustomConfig2().set("Broadcasts", broadcasts);
        functions.saveData();
    }
    private ArrayList<String> getBroadcastHistory(){
        ArrayList<String> broadcasts = new ArrayList<>();
        if (Configs.getCustomConfig2().contains("Broadcasts")) broadcasts = (ArrayList<String>) Configs.getCustomConfig2().getStringList("Broadcasts");
        return broadcasts;
    }
}
