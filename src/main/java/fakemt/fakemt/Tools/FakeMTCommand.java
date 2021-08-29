package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;

import static fakemt.fakemt.FakeMT.servername;
import static fakemt.fakemt.Head.Configs.customConfigFile1;

public class FakeMTCommand implements CommandExecutor {

    Functions functions = new Functions();
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("fakemt")){

            if (sender instanceof Player){

                Player player = (Player) sender;

                if (args.length == 0){
                    if (functions.hasPerm(player, servername + ".help")){
                        player.sendMessage("§7§l    * §6/FakeMT §ereload         §7Reload de server");
                    }
                }else if (args.length == 1){
                    if (args[0].equalsIgnoreCase("reload")){
                        if (functions.hasPerm(player, servername + ".reload")){
                            plugin.reloadConfig();
                            try {
                                Configs.getCustomConfig1().load(customConfigFile1);
                            } catch (IOException | InvalidConfigurationException e) {
                                e.printStackTrace();
                            }
                            player.sendMessage(functions.getMessage("Plugin Reloaded"));
                        }
                    }
                }

            }

        }

        return false;
    }
}
