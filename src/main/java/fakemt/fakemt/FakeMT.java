package fakemt.fakemt;

import fakemt.fakemt.ArmorstandStuff.ArmorstandBreak;
import fakemt.fakemt.ArmorstandStuff.ArmorstandHead;
import fakemt.fakemt.ArmorstandStuff.ArmorstandPlace;
import fakemt.fakemt.Automatisation.AutoEnchant;
import fakemt.fakemt.Automatisation.AutoKit;
import fakemt.fakemt.Automatisation.AutoLore;
import fakemt.fakemt.Automatisation.AutoRemover;
import fakemt.fakemt.Huisdieren.*;
import fakemt.fakemt.Kluizen.KluisCommand;
import fakemt.fakemt.Kluizen.KluisInteract;
import fakemt.fakemt.Kluizen.KluisInventoryClick;
import fakemt.fakemt.Kluizen.KluisPlace;
import fakemt.fakemt.Tools.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static fakemt.fakemt.Head.Configs.*;
import static fakemt.fakemt.Tools.Shairs.clickLocations;
import static fakemt.fakemt.Tools.Shairs.sitters;

public final class FakeMT extends JavaPlugin implements Listener {

    public static HashMap<UUID, Entity> pets = new HashMap<>();
    public static String servername;
    public static int season;
    public static ArrayList<UUID> blocked = new ArrayList<>();

    @Override
    public void onEnable() {
        servername = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ServerName"));
        season = this.getConfig().getInt("Season");

        createCustomConfig1();
        createCustomConfig2();
        createCustomConfig3();
        createCustomConfig4();
        createCustomConfig5();

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        getServer().getPluginManager().registerEvents(new AutoRemover(), this);
        getServer().getPluginManager().registerEvents(new AutoKit(), this);
        getServer().getPluginManager().registerEvents(new BottleStacker(), this);
        getServer().getPluginManager().registerEvents(new ArmorstandHead(), this);
        getServer().getPluginManager().registerEvents(new ArmorstandBreak(), this);
        getServer().getPluginManager().registerEvents(new ArmorstandPlace(), this);
        getServer().getPluginManager().registerEvents(new CtrlStats(), this);
        getServer().getPluginManager().registerEvents(new InformationTab(), this);
        getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new PetInteractEvent(), this);
        getServer().getPluginManager().registerEvents(new JoinPetSpawn(), this);
        getServer().getPluginManager().registerEvents(new Shairs(), this);
        getServer().getPluginManager().registerEvents(new JuliaBlocker(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new KluisInteract(), this);
        getServer().getPluginManager().registerEvents(new KluisInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new KluisPlace(), this);


        getCommand("kluis").setExecutor(new KluisCommand());
        getCommand("block").setExecutor(new JuliaBlocker());
        getCommand("pet").setExecutor(new PetCommand());
        getCommand("fakemt").setExecutor(new FakeMTCommand());
        getCommand("alert").setExecutor(new Alert());
        getCommand("itemedit").setExecutor(new FastItemEdit());
        getCommand("links").setExecutor(new InformationTab());
        getCommand("autolore").setExecutor(new AutoLore());
        getCommand("kit").setExecutor(new AutoKit());
        getCommand("autoenchant").setExecutor(new AutoEnchant());

        getCommand("autoenchant").setTabCompleter(new TabCompletion());
        getCommand("itemedit").setTabCompleter(new TabCompletion());
        getCommand("pet").setTabCompleter(new TabCompletion());

    }

    @Override
    public void onDisable() {
        removeSittingPlayers();
        for (Player online : Bukkit.getServer().getOnlinePlayers()){
            PetData petData = new PetData(online);
            if (petData.hasAPet()) petData.unloadPets();
        }
        for (Entity entity : pets.values())entity.remove();
    }
    public void removeSittingPlayers(){
        for (UUID s : sitters.keySet()){
            Player player = Bukkit.getPlayer(s);
            ArmorStand armorStand = sitters.get(s);
            armorStand.removePassenger(player);
            armorStand.remove();
            player.teleport(clickLocations.get(player.getUniqueId()));
        }
    }
}
