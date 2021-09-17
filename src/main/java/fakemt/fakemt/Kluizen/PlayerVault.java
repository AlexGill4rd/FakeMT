package fakemt.fakemt.Kluizen;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import static fakemt.fakemt.Head.Configs.customConfigFile5;

public class PlayerVault {
    Player player;

    Functions functions = new Functions();
    public PlayerVault(Player player){
        this.player = player;
    }

    private final FakeMT plugin = FakeMT.getPlugin(FakeMT.class);
    public boolean isValidVaultBlock(Material material){return material == getVaultMaterial();}
    public boolean hasVault(){
        Set<String> kluizenSection = null;
        if (Configs.getCustomConfig5().contains("Kluizen." + player.getUniqueId().toString())){
            kluizenSection = Configs.getCustomConfig5().getConfigurationSection("Kluizen." + player.getUniqueId().toString()).getKeys(false);
        }
        if (kluizenSection == null)return false;
        return !kluizenSection.isEmpty();
    }
    public ArrayList<Kluis> getPlayerVaults(){
        Set<String> vaultNames = null;
        ArrayList<Kluis> vaults = new ArrayList<>();
        if (Configs.getCustomConfig5().contains("Kluizen." + player.getUniqueId().toString())){
            vaultNames = Configs.getCustomConfig5().getConfigurationSection("Kluizen." + player.getUniqueId().toString()).getKeys(false);
        }
        if (vaultNames == null)return vaults;
        for (String s : vaultNames){
            Kluis vault = new Kluis(s, player);
            vaults.add(vault);
        }

        return vaults;
    }
    public Material getVaultMaterial(){
        return Material.valueOf(plugin.getConfig().getString("KluisBlok"));
    }
    public Kluis getVaultByLocation(Location location){
        for (Kluis kluis : getPlayerVaults()){
            if (functions.convertLocationToString(kluis.getLocation()).equals(functions.convertLocationToString(location))){
                return kluis;
            }
        }
        return null;
    }
    public boolean isVaultItemSlot(int slot, Inventory inventory, Kluis kluis){
        if (slot != 0){
            if (slot % 7 == 0 || slot % 8 == 0)return false;
        }
        int invalidslot;
        if (kluis.getSlots() - (kluis.getSlots() * (getCurrentPage(inventory)-1)) >= 42) invalidslot = 52;
        else invalidslot = kluis.getSlots() - (kluis.getSlots() * (getCurrentPage(inventory)-1))-1;
        int extraslots = 0;
        int counter = 0;
        for (int i = 0; i < inventory.getSize(); i++){
            if (counter >= invalidslot)continue;
            if (i % 7 == 0)extraslots+=2;
            counter+=7;
        }
        return slot <= invalidslot+extraslots;
    }
    public void savePageData(Inventory inventory, Kluis kluis){
        int pagina = getCurrentPage(inventory);

        ArrayList<ItemStack> validItems = new ArrayList<>();
        for (int i = 0; i < inventory.getSize(); i++){
            if (isVaultItemSlot(i, inventory, kluis)) {
                if (inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR)continue;
                validItems.add(inventory.getItem(i));
            }
        }
        kluis.setKluisLoot(String.valueOf(pagina), validItems);
    }
    public int getCurrentPage(Inventory inventory){
        ItemStack pageItem = inventory.getItem(8);
        return Integer.parseInt(pageItem.getItemMeta().getDisplayName().split(" ")[1]);
    }
    public Kluis getKluisByItemstack(ItemStack item){
        OfflinePlayer eigenaar = Bukkit.getOfflinePlayer(ChatColor.stripColor(item.getItemMeta().getLore().get(0).split(" ")[1]));
        String kluisnaam = item.getItemMeta().getLore().get(1).split(" ")[1];
        return new Kluis(kluisnaam, eigenaar);
    }
    public void saveKluisData(){
        try {
            Configs.getCustomConfig5().save(customConfigFile5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
