package fakemt.fakemt.Tools;

import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;

import java.util.ArrayList;
import java.util.List;

public class TabCompletion implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        if (command.getName().equalsIgnoreCase("autoenchant")){
            if (args.length == 1){
                ArrayList<String> possible = new ArrayList<>();
                for (Enchantment enchantment : Enchantment.values()){
                    if (enchantment.getName().toLowerCase().startsWith(args[0].toLowerCase())){
                        possible.add(StringUtils.capitalize(enchantment.getName().toLowerCase()));
                    }
                }
                return possible;

            }
        }
        return null;
    }
}
