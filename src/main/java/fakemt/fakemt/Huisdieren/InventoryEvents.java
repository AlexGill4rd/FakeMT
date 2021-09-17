package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.Functions;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Parrot;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.ToIntFunction;

public class InventoryEvents implements Listener {

    HashMap<Player, Pet> petRename = new HashMap<>();
    Functions functions = new Functions();
    PetInventories petInventories = new PetInventories();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        ItemStack is = e.getCurrentItem();

        if (is == null)return;
        if (e.getView().getTitle().equals("§7§l* §6§lPETS §7§l*")){
            e.setCancelled(true);
            if (e.getClickedInventory() instanceof PlayerInventory)return;
            if (is.getType() == Material.BARRIER){
                PetData petData = new PetData(player);
                petData.despawnPets(true);
                petInventories.openMainMenu(player);
                player.closeInventory();
                player.sendMessage(functions.getMessage("PetDespawned"));
            }
            if (is.getType() == Material.DIAMOND_HOE){
                if (e.getClick().isLeftClick()){
                    PetData petData = new PetData(player);
                    petData.despawnPets(true);

                    Pet toSpawn = new Pet(EntityType.valueOf(ChatColor.stripColor(is.getItemMeta().getDisplayName().toUpperCase())), player);
                    toSpawn.setActive(true);
                    petData.spawnPet(toSpawn);
                    player.closeInventory();
                }else if (e.getClick().isRightClick()){
                    PetData petData = new PetData(player);
                    petData.despawnPets(true);
                    player.closeInventory();
                    player.sendMessage(functions.getMessage("PetDespawned"));
                }
            }

        }else if (e.getView().getTitle().equals("§7§l* §5§lPet Editor §7§l*")){
            e.setCancelled(true);
            if (e.getClickedInventory() instanceof PlayerInventory)return;
            if (is.getType() == Material.STAINED_GLASS_PANE || is.getType() == Material.SKULL_ITEM)return;
            String[] split = e.getInventory().getItem(0).getItemMeta().getLore().get(1).split(" ");
            OfflinePlayer target = Bukkit.getOfflinePlayer(ChatColor.stripColor(split[1]));
            player.sendMessage(target.getName());
            Pet pet = new Pet(EntityType.valueOf(ChatColor.stripColor(e.getInventory().getItem(0).getItemMeta().getDisplayName())), target);
            switch (is.getType()){
                case NAME_TAG:
                    petRename.put(player, pet);
                    player.sendMessage(functions.getMessage("PetRename"));
                    player.closeInventory();
                    break;
                case TORCH:
                    pet.setGlow(!pet.hasGlow());
                    petInventories.openPetEditor(player, pet);
                    break;
                case WOOL:
                    for (int i = 0; i < DyeColor.values().length; i++){
                        if (pet.getPetcolor().equals(DyeColor.values()[i].name())){
                            pet.setPetcolor(i == DyeColor.values().length - 1 ? DyeColor.values()[0].name() : DyeColor.values()[i].name());
                        }
                    }
                    petInventories.openPetEditor(player, pet);
                    break;
                case ANVIL:
                    pet.setBaby(!pet.isBaby());
                    petInventories.openPetEditor(player, pet);
                    break;
                case STICK:
                    for (int i = 0; i < Parrot.Variant.values().length; i++){
                        if (pet.getPetcolor().equals(Parrot.Variant.values()[i].name())){
                            pet.setPetcolor(i == Parrot.Variant.values().length - 1 ? Parrot.Variant.values()[0].name() : Parrot.Variant.values()[i].name());
                        }
                    }
                    petInventories.openPetEditor(player, pet);
                    break;
                case SHEARS:
                    pet.setSheared(!pet.isSheared());
                    petInventories.openPetEditor(player, pet);
                    break;
            }
        }
    }
    @EventHandler
    public void onPetRename(AsyncPlayerChatEvent e){
        Player player = e.getPlayer();
        String petName = e.getMessage();
        if (petRename.containsKey(player)){
            Pet pet = petRename.get(player);
            pet.setName(petName);
            petRename.remove(player, pet);
            player.sendMessage(functions.getMessage("PetRenamed"));
            petInventories.openPetEditor(player, pet);
        }
    }
}
