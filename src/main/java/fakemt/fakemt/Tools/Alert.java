package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Alert implements CommandExecutor {

    Functions functions = new Functions();
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);
    String prefix = functions.color(plugin.getConfig().getString("Prefix"));
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("alert")){
            if (sender instanceof Player){
                Player player = (Player) sender;

                if (args.length == 0){
                    if (functions.hasPerm(player, "alert.help")){
                        player.sendMessage("§c/Alert <message> om een broadcast te versturen!");
                    }
                }else{
                    if (functions.hasPerm(player, "alert.send")){
                        StringBuilder message = new StringBuilder();
                        for (String s : args){
                            message.append(s).append(" ");
                        }
                        for (Player online : Bukkit.getServer().getOnlinePlayers()){
                            online.sendMessage(prefix + message);
                        }
                    }
                }
            }
        }
        return false;
    }
}
