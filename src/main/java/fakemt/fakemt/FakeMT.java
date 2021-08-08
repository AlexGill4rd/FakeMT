package fakemt.fakemt;

import fakemt.fakemt.ArmorstandStuff.ArmorstandBreak;
import fakemt.fakemt.ArmorstandStuff.ArmorstandHead;
import fakemt.fakemt.ArmorstandStuff.ArmorstandPlace;
import fakemt.fakemt.Automatisation.AutoEnchant;
import fakemt.fakemt.Automatisation.AutoKit;
import fakemt.fakemt.Automatisation.AutoLore;
import fakemt.fakemt.Automatisation.AutoRemover;
import fakemt.fakemt.Tools.*;
import org.bukkit.plugin.java.JavaPlugin;

import static fakemt.fakemt.Head.Configs.*;

public final class FakeMT extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        createCustomConfig1();
        createCustomConfig2();
        createCustomConfig3();

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

        getCommand("alert").setExecutor(new Alert());
        getCommand("fastname").setExecutor(new FastItemEdit());
        getCommand("fastlore").setExecutor(new FastItemEdit());
        getCommand("links").setExecutor(new InformationTab());
        getCommand("autolore").setExecutor(new AutoLore());
        getCommand("kit").setExecutor(new AutoKit());
        getCommand("autoenchant").setExecutor(new AutoEnchant());
        getCommand("autoenchant").setTabCompleter(new TabCompletion());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
