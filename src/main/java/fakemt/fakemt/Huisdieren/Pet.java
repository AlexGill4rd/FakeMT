package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;

import java.io.File;

public class Pet {

    Functions functions = new Functions();
    private final EntityType entityType;

    private final OfflinePlayer owner;
    private boolean isActive;
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);

    //PET PROPERTIES
    private String name;
    private String petcolor;
    private boolean glow;
    private boolean sheared;
    private boolean baby;
    private String variant;

    public Pet(EntityType mobtype, OfflinePlayer owner){
        this.owner = owner;
        this.entityType = mobtype;
        this.name = Configs.getCustomConfig4().getString("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".Name");
        this.isActive = Configs.getCustomConfig4().getBoolean("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".IsActive");
        this.glow = Configs.getCustomConfig4().getBoolean("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".Glow");

        switch (mobtype) {
            case SHEEP:
                this.petcolor = Configs.getCustomConfig4().getString("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".PetColor");
                this.sheared = Configs.getCustomConfig4().getBoolean("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".Sheared");
                this.baby = Configs.getCustomConfig4().getBoolean("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".IsBaby");
                break;
            case PARROT:
                this.variant = Configs.getCustomConfig4().getString("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".Variant");
                this.baby = Configs.getCustomConfig4().getBoolean("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".IsBaby");
                break;
        }
    }
    public String getName(){return name;}
    public OfflinePlayer getOwner(){return owner;}
    public EntityType getEntityType(){return entityType;}
    public boolean isActive(){return isActive;}
    public String getPetcolor(){return petcolor;}
    public boolean hasGlow(){return glow;}
    public boolean isSheared(){return sheared;}
    public boolean isBaby(){return baby;}
    public String getVariant(){return variant;}

    public void setActive(boolean active){isActive = active; saveData();}
    public void setName(String petname){name = ChatColor.translateAlternateColorCodes('&', petname); saveData();}
    public void setPetcolor(String petcolor){this.petcolor = petcolor; saveData();}
    public void setGlow(boolean glow){this.glow = glow; saveData();}
    public void setSheared(boolean sheared){this.sheared = sheared; saveData();}
    public void setBaby(boolean baby){this.baby = baby; saveData();}
    public void setVariant(String variant){this.variant = variant; saveData();}

    private void saveData(){
        File myfile = new File(plugin.getDataFolder(), "HuisdierenData.yml");
        if(myfile.exists()) {
            Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".Name", name);
            Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".IsActive", isActive);
            Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".Glow", glow);
            switch (entityType){
                case SHEEP:
                    Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".PetColor", petcolor);
                    Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".Sheared", sheared);
                    Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".IsBaby", baby);
                    break;
                case PARROT:
                    Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".Variant", variant);
                    Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".IsBaby", baby);
                    break;
            }
            functions.saveHuisdierData();
        }
    }
}
