package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.EntityType;

public class Pet {

    Functions functions = new Functions();
    private final EntityType entityType;
    private String name;
    private final OfflinePlayer owner;
    private boolean isActive;

    public Pet(EntityType mobtype, OfflinePlayer owner){
        this.owner = owner;
        this.entityType = mobtype;
        this.name = Configs.getCustomConfig4().getString("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".Name");
        this.isActive = Configs.getCustomConfig4().getBoolean("Pets." + owner.getUniqueId().toString() + "." + mobtype.name() + ".IsActive");

    }

    public String getName(){return name;}
    public OfflinePlayer getOwner(){return owner;}
    public EntityType getEntityType(){return entityType;}
    public boolean isActive(){return isActive;}

    public void setActive(boolean active){isActive = active; saveData();}
    public void setName(String petname){name = petname; saveData();}

    private void saveData(){
        Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".Name", name);
        Configs.getCustomConfig4().set("Pets." + owner.getUniqueId().toString() + "." + entityType.name() + ".IsActive", isActive);
        functions.saveHuisdierData();
    }
}
