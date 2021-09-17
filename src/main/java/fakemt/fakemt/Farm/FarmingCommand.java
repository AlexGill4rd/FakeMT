package fakemt.fakemt.Farm;

import fakemt.fakemt.Functions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static fakemt.fakemt.FakeMT.servername;

public class FarmingCommand implements CommandExecutor {
    Functions functions = new Functions();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        if (command.getName().equalsIgnoreCase("farming")){
            if (sender instanceof Player){
                Player player = (Player) sender;
                if (args.length == 0){
                    if (functions.hasPerm(player, servername + ".farming.help")){
                        
                    }
                }
            }
        }

        return false;
    }
}
