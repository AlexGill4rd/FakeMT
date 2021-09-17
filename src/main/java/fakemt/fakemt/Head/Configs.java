package fakemt.fakemt.Head;

import fakemt.fakemt.FakeMT;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Configs {

    public static File customConfigFile1;
    private static FileConfiguration customConfig1;

    private static final FakeMT plugin = FakeMT.getPlugin(FakeMT.class);

    public static FileConfiguration getCustomConfig1() {
        return customConfig1;
    }

    public static void createCustomConfig1() {
        customConfigFile1 = new File(plugin.getDataFolder(), "Messages.yml");
        if (!customConfigFile1.exists()) {
            customConfigFile1.getParentFile().mkdirs();
            plugin.saveResource("Messages.yml", false);
        }

        customConfig1 = new YamlConfiguration();

        try {
            customConfig1.load(customConfigFile1);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static File customConfigFile2;
    private static FileConfiguration customConfig2;

    public static FileConfiguration getCustomConfig2() {
        return customConfig2;
    }

    public static void createCustomConfig2() {
        customConfigFile2 = new File(plugin.getDataFolder(), "Data.yml");
        if (!customConfigFile2.exists()) {
            customConfigFile2.getParentFile().mkdirs();
            plugin.saveResource("Data.yml", false);
        }

        customConfig2 = new YamlConfiguration();

        try {
            customConfig2.load(customConfigFile2);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static File customConfigFile3;
    private static FileConfiguration customConfig3;

    public static FileConfiguration getCustomConfig3() {
        return customConfig3;
    }

    public static void createCustomConfig3() {
        customConfigFile3 = new File(plugin.getDataFolder(), "SpelerData.yml");
        if (!customConfigFile3.exists()) {
            customConfigFile3.getParentFile().mkdirs();
            plugin.saveResource("SpelerData.yml", false);
        }

        customConfig3 = new YamlConfiguration();

        try {
            customConfig3.load(customConfigFile3);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
    public static File customConfigFile4;
    private static FileConfiguration customConfig4;

    public static FileConfiguration getCustomConfig4() {
        return customConfig4;
    }

    public static void createCustomConfig4() {
        customConfigFile4 = new File(plugin.getDataFolder(), "HuisdierenData.yml");
        if (!customConfigFile4.exists()) {
            customConfigFile4.getParentFile().mkdirs();
            plugin.saveResource("HuisdierenData.yml", false);
        }

        customConfig4 = new YamlConfiguration();

        try {
            customConfig4.load(customConfigFile4);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static File customConfigFile5;
    private static FileConfiguration customConfig5;

    public static FileConfiguration getCustomConfig5() {
        return customConfig5;
    }

    public static void createCustomConfig5() {
        customConfigFile5 = new File(plugin.getDataFolder(), "KluisData.yml");
        if (!customConfigFile5.exists()) {
            customConfigFile5.getParentFile().mkdirs();
            plugin.saveResource("KluisData.yml", false);
        }

        customConfig5 = new YamlConfiguration();

        try {
            customConfig5.load(customConfigFile5);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
