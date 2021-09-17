package fakemt.fakemt.Kluizen;

import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static fakemt.fakemt.Head.Configs.customConfigFile5;

public class Kluis {

    Functions functions = new Functions();

    String kluisnaam;
    String kluisnaamraw;
    Player eigenaar;
    HashMap<String, ArrayList<ItemStack>> kluisloot = new HashMap<>();
    int paginas = 1;
    int slots = 9;
    Location location;

    public Kluis(String kluisnaam, OfflinePlayer eigenaar){
        this.kluisnaam = functions.color(kluisnaam).trim();
        this.kluisnaamraw = ChatColor.stripColor(this.kluisnaam);
        this.eigenaar = eigenaar.getPlayer();

        if (configContainsValue("Loot")) {
            ArrayList<String> pages = new ArrayList<>(Configs.getCustomConfig5().getConfigurationSection("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Loot").getKeys(false));
            for (String page : pages){
                ArrayList<ItemStack> loot = (ArrayList<ItemStack>) Configs.getCustomConfig5().getList("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Loot." + page);
                kluisloot.put(page, loot);
            }
        }
        if (configContainsValue("Paginas"))this.paginas = getIntFromConfig("Paginas");
        if (configContainsValue("Slots"))this.slots = getIntFromConfig("Slots");

        if (configContainsValue("Location"))this.location = functions.convertStringToLocation(getStringFromConfig("Location"));
        if (configContainsValue("Kluisnaam"))this.kluisnaam = getStringFromConfig("Kluisnaam");
    }
    public int getPaginas(){return paginas;}
    public ArrayList<ItemStack> getKluisLoot(String page){return kluisloot.containsKey(page) ? kluisloot.get(page) : new ArrayList<>();}
    public String getKluisnaam(){return kluisnaam;}
    public Player getEigenaar(){return eigenaar;}
    public int getSlots(){return slots;}
    public Location getLocation(){return location;}

    public void setPaginas(int paginas){this.paginas = paginas; updateKluisData();}
    public void setKluisLoot(String page, ArrayList<ItemStack> kluisloot){this.kluisloot.put(page, kluisloot); updateKluisData();}
    public void setKluisnaam(String naam){this.kluisnaam = naam; this.kluisnaamraw = ChatColor.stripColor(this.kluisnaam); updateKluisData();}
    public void setEigenaar(Player eigenaar){this.eigenaar = eigenaar; updateKluisData();}
    public void setSlots(int slots){this.slots = slots; updateKluisData();}
    public void setLocation(Location location){this.location = location; updateKluisData();}

    public void addKluisItem(String page, ItemStack item){
        if (kluisloot.containsKey(page)) this.kluisloot.get(page).add(item);
        else{
            ArrayList<ItemStack> loot = new ArrayList<>();
            loot.add(item);
            this.kluisloot.put(page, loot);
            updateKluisData();
        }

    }
    public void addSlots(int slots){this.slots += slots; updateKluisData();}

    public void removeKluisData(){
        Configs.getCustomConfig5().set("Kluizen." + eigenaar.getUniqueId().toString() + "." + kluisnaamraw, null);
        saveKluisData();
    }
    void updateKluisData(){
        for (String page : kluisloot.keySet()){
            Configs.getCustomConfig5().set("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Loot." + page, kluisloot.get(page));
        }
        Configs.getCustomConfig5().set("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Paginas", paginas);
        Configs.getCustomConfig5().set("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Slots", slots);
        Configs.getCustomConfig5().set("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Location", functions.convertLocationToString(this.location));
        Configs.getCustomConfig5().set("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + ".Kluisnaam", this.kluisnaam);
        saveKluisData();
    }
    void saveKluisData(){
        try {
            Configs.getCustomConfig5().save(customConfigFile5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private boolean configContainsValue(String value){
        return Configs.getCustomConfig5().contains("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + "." + value);
    }
    private int getIntFromConfig(String value){
        return Configs.getCustomConfig5().getInt("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + "." + value);
    }
    private String getStringFromConfig(String value){
        return Configs.getCustomConfig5().getString("Kluizen." + eigenaar.getUniqueId().toString() + "." + this.kluisnaamraw + "." + value);
    }
}
