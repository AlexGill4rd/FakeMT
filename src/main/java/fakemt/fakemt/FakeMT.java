package fakemt.fakemt;

import fakemt.fakemt.ArmorstandStuff.ArmorstandBreak;
import fakemt.fakemt.ArmorstandStuff.ArmorstandHead;
import fakemt.fakemt.ArmorstandStuff.ArmorstandPlace;
import fakemt.fakemt.Automatisation.AutoEnchant;
import fakemt.fakemt.Automatisation.AutoKit;
import fakemt.fakemt.Automatisation.AutoLore;
import fakemt.fakemt.Automatisation.AutoRemover;
import fakemt.fakemt.Huisdieren.*;
import fakemt.fakemt.Tools.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

import static fakemt.fakemt.Head.Configs.*;

public final class FakeMT extends JavaPlugin implements Listener {

    public static HashMap<UUID, Entity> pets = new HashMap<>();
    public static String servername;
    public static int season;

    @Override
    public void onEnable() {
        servername = ChatColor.translateAlternateColorCodes('&', this.getConfig().getString("ServerName"));
        season = this.getConfig().getInt("Season");

        createCustomConfig1();
        createCustomConfig2();
        createCustomConfig3();
        createCustomConfig4();

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
        for (Player online : Bukkit.getServer().getOnlinePlayers()){
            PetData petData = new PetData(online);
            if (petData.hasAPet()) petData.unloadPets();
        }
        for (Entity entity : pets.values())entity.remove();
    }
}
