package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import net.minecraft.server.v1_12_R1.EntityInsentient;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.entity.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import static fakemt.fakemt.FakeMT.pets;

public class PetData {
    Functions functions = new Functions();
    OfflinePlayer player;
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);
    public PetData(OfflinePlayer player){
        this.player = player;
    }
    public boolean hasLoadedPet(){
        if (hasAPet()){
            for (Pet pet : getPets())
                if (pet.isActive())return true;
        }
        return false;
    }
    public Pet getActivePet(){
        Pet active = null;
        if (hasAPet()){
            for (Pet pet : getPets())
                if (pet.isActive())active = pet;
        }
        return active;
    }
    public void spawnPet(Pet pet){
        Player owner = player.getPlayer();
        Entity spawnPet = owner.getWorld().spawnEntity(owner.getLocation(), pet.getEntityType());
        spawnPet.setCustomNameVisible(true);
        spawnPet.setCustomName(pet.getName());
        spawnPet.setInvulnerable(true);
        spawnPet.setSilent(true);
        followPlayer(owner, (LivingEntity) spawnPet, 1.75);
        pets.put(owner.getUniqueId(), spawnPet);
    }
    public void despawnPets(boolean setactive){
        for (UUID uuid : pets.keySet()){
            if (uuid == player.getUniqueId()){
                Entity pet = pets.get(player.getUniqueId());
                pet.remove();
                pets.remove(player.getUniqueId(), pet);
                if (setactive)getActivePet().setActive(false);
            }
        }
    }
    public void unloadPets(){
        for (Pet pet : getPets()){
            pet.setActive(false);
        }
    }
    public ArrayList<Pet> getPets(){
        Set<String> set = null;
        ArrayList<Pet> huisdieren = new ArrayList<>();
        if (hasAPet()){
            set = Configs.getCustomConfig4().getConfigurationSection("Pets." + player.getUniqueId().toString()).getKeys(false);
        }
        if (set != null) {
            for (String s : set)
                huisdieren.add(new Pet(EntityType.valueOf(s), player));
        }
        return huisdieren;
    }
    public void addPetToPlayer(Pet pet, String name){
        if (getPets().contains(pet))return;
        Configs.getCustomConfig4().set("Pets." + player.getUniqueId().toString() + "." + pet.getEntityType().name() + ".Name", ChatColor.translateAlternateColorCodes('&', name));
        Configs.getCustomConfig4().set("Pets." + player.getUniqueId().toString() + "." + pet.getEntityType().name() + ".IsActive", false);
        functions.saveHuisdierData();
    }
    public boolean hasPet(EntityType petType){ return Configs.getCustomConfig4().contains("Pets." + player.getUniqueId().toString() + "." + petType.name()); }
    public boolean hasAPet(){
        return Configs.getCustomConfig4().contains("Pets." + player.getUniqueId().toString());
    }
    public String getMobDefaultName(String mobtype){return StringUtils.capitalize(mobtype.toLowerCase().replace("_", " "));}
    public void followPlayer(Player player, LivingEntity entity, double d) {
        final LivingEntity e = entity;
        final Player p = player;
        final float f = (float) d;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                if (player.getLocation().distance(e.getLocation()) >= 17 && !player.isFlying())e.teleport(player.getLocation());
                if (player.getLocation().distance(e.getLocation()) > 3) {
                    ((EntityInsentient) ((CraftEntity) e).getHandle()).getNavigation().a(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), f);
                }
            }
        }, 0, 10);
    }
}
