package fakemt.fakemt.Tools;

import fakemt.fakemt.Functions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TabCompletion implements TabCompleter {
    Functions functions = new Functions();
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
        }else if (command.getName().equalsIgnoreCase("itemedit")){
            if (args.length == 1){
                ArrayList<String> possible = new ArrayList<>();
                possible.add("lore");
                possible.add("name");
                possible.add("durability");
                possible.removeIf(s -> !s.startsWith(args[0]));
                return possible;
            }
        }else if (command.getName().equalsIgnoreCase("pet")){
            if (args.length == 1){
                ArrayList<String> possible = new ArrayList<>();
                possible.add("help");
                possible.add("menu");
                possible.add("give");
                possible.add("remove");
                possible.add("info");
                possible.removeIf(s -> !s.startsWith(args[0]));
                return possible;
            }else if (args.length == 3){
                if (args[0].equalsIgnoreCase("give")){
                    ArrayList<String> possible = functions.getAllEntityTypes().stream().map(entityType -> StringUtils.capitalize(entityType.name().toLowerCase())).collect(Collectors.toCollection(ArrayList::new));
                    possible.removeIf(s -> !s.startsWith(args[2]));
                    return possible;
                }
            }
        }
        return null;
    }
}
