package fakemt.fakemt;

import fakemt.fakemt.Head.Configs;
import org.apache.commons.lang.StringUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import static fakemt.fakemt.Head.Configs.*;

public class Functions {

    public boolean isArmor(ItemStack item) {
        String itemtype = item.getType().toString().toLowerCase();
        return itemtype.contains("helmet") ||
                itemtype.contains("chestplate") ||
                itemtype.contains("leggings") ||
                itemtype.contains("boots");
    }
    public boolean isCriminalItem(ItemStack item){
        String itemtype = item.getType().toString().toLowerCase();
        String itemname = "";
        if (item.hasItemMeta()){
            if (item.getItemMeta().getDisplayName() != null){
                itemname = item.getItemMeta().getDisplayName().toLowerCase();
            }
        }
        return itemtype.contains("sword") ||
                itemtype.contains("bow") ||
                itemtype.contains("arrow") ||
                itemname.contains("bivak") ||
                itemtype.contains("shield") ||
                itemname.contains("knuppel") ||
                itemtype.contains("eye");
    }
    public ChatColor getItemColor(ItemStack item){
        ChatColor color = ChatColor.GOLD;
        if (isArmor(item)) {
            if (item.getType().toString().toLowerCase().contains("leather"))color = ChatColor.YELLOW;
            if (item.getType().toString().toLowerCase().contains("chainmail"))color = ChatColor.DARK_GRAY;
            if (item.getType().toString().toLowerCase().contains("iron"))color = ChatColor.GREEN;
            if (item.getType().toString().toLowerCase().contains("gold"))color = ChatColor.GOLD;
            if (item.getType().toString().toLowerCase().contains("diamond"))color = ChatColor.AQUA;
        }
        return color;
    }
    public ItemStack createHead(OfflinePlayer target, String title, ArrayList<String> lore){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());

        SkullMeta skull_meta = (SkullMeta) skull.getItemMeta();
        skull_meta.setOwningPlayer(Bukkit.getOfflinePlayer(target.getUniqueId()));
        skull_meta.setDisplayName(title);
        if (lore != null)skull_meta.setLore(lore);
        skull.setItemMeta(skull_meta);

        return skull;
    }
    public ArrayList<String> getAnnoyingPeople(){

        return getArray("Annoying");

    }
    public String getItemName(ItemStack item){
        String itemname = "";
        ChatColor color = getItemColor(item);
        if (item.hasItemMeta()){
            if (item.getItemMeta().getDisplayName() != null){
                String displayname = ChatColor.stripColor(item.getItemMeta().getDisplayName());
                if (item.getItemMeta().getDisplayName().contains("- ")){
                    displayname = displayname.substring(displayname.indexOf("- ") + 2);
                    displayname = displayname.substring(0, displayname.indexOf(" -"));
                }
                itemname = "§7§l- " + color + StringUtils.capitalize(displayname) + "§7§l -";
            }else{
                itemname = "§7§l- " + color + StringUtils.capitalize(item.getType().toString().toLowerCase().replace("_", " ")) + "§7§l -";
            }
        }else{
            itemname = "§7§l- " + color + StringUtils.capitalize(item.getType().toString().toLowerCase().replace("_", " ")) + "§7§l -";
        }
        return itemname;
    }
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);

    public String color(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getServerPrefix(){return color(plugin.getConfig().getString("ServerPrefix"));}

    public String getMessage(String path){
        if (Configs.getCustomConfig1().contains(path)){
            return color(Configs.getCustomConfig1().getString(path));
        }
        return "§4§lERROR";
    }
    public ArrayList<String> getArray(String path){
        ArrayList<String> array = new ArrayList<>();
        if (Configs.getCustomConfig1().contains(path)){
            for (String s : Configs.getCustomConfig1().getStringList(path)){
                array.add(color(s));
            }
            return array;
        }
        return array;
    }
    public ItemStack editItemMeta(ItemStack stack, String displayname, ArrayList<String> lore){
        ItemStack newItem = stack.clone();
        ItemMeta meta = newItem.getItemMeta();;
        if (displayname != null)meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayname));
        if (lore != null)meta.setLore(lore);
        newItem.setItemMeta(meta);
        return newItem;
    }
    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    public void saveData(){
        try {
            Configs.getCustomConfig2().save(customConfigFile2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void savePlayerData(){
        try {
            Configs.getCustomConfig3().save(customConfigFile3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean hasJoinedBefore(Player player){
        return Configs.getCustomConfig3().contains("Players." + player.getUniqueId().toString());
    }
    public boolean hasPerm(Player player, String permission){
        if (player.hasPermission(permission)) return true;
        player.sendMessage(getMessage("No permissions"));
        return false;
    }
    public ItemStack createItemstack(Material material, String title, ArrayList<String> lore){
        ItemStack stack = new ItemStack(material);
        ItemMeta stack_meta = stack.getItemMeta();
        if (title != null)stack_meta.setDisplayName(title);
        if (lore != null)stack_meta.setLore(lore);
        stack.setItemMeta(stack_meta);
        return stack;
    }
    public ItemStack createGlass(String title, int color, ArrayList<String> lore){
        ItemStack stack = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) color);
        ItemMeta stack_meta = stack.getItemMeta();
        stack_meta.setDisplayName(title);
        if(lore!=null) stack_meta.setLore(lore);
        stack.setItemMeta(stack_meta);
        return stack;
    }
    public ArrayList<String> createArraylist(String... args){
        ArrayList<String> lines = new ArrayList<>();
        Collections.addAll(lines, args);
        return lines;
    }
    public void fillInv(Inventory inventory, int color){
        for (int i = 0; i < inventory.getSize(); i++){
            ItemStack current = inventory.getItem(i);
            if (current == null || current.getType() == Material.AIR){
                inventory.setItem(i, createGlass(" ", color, null));
            }
        }
    }
    public String convertLocationToString(Location location){
        return location.getWorld().getName() + "," + (int) location.getX() + "," + (int) location.getY() + "," + (int) location.getZ();
    }
    public Location convertStringToLocation(String stringLoc){
        String[] args = stringLoc.split(",");
        if (args.length == 4){
            return new Location(Bukkit.getWorld(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]), Integer.parseInt(args[3]), 0, 0);
        }return null;
    }

}
