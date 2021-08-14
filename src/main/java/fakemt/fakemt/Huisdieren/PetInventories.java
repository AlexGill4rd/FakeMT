package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.Functions;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.Inventory;

public class PetInventories {
    Functions functions = new Functions();
    public void openMainMenu(Player player){
        PetData petData = new PetData(player);
        Inventory inventory = Bukkit.createInventory(null, 27, "§7§l* §6§lPETS §7§l*");
        inventory.setItem(26, functions.createItemstack(Material.BARRIER, "§7§l- §6§lUNLOAD ALL §7§l-", functions.createArraylist("§7Verberg het active huisdier")));

        for (Pet pet : petData.getPets())
            inventory.addItem(functions.createItemstack(Material.DIAMOND_HOE, ChatColor.BLUE + pet.getEntityType().name(), functions.createArraylist("§7Nickname: §r" + pet.getName(), "§7Left-Click om het huisdier te spawnen", "§7Right-Click om pet te verbergen", "§7Active: §e" + pet.isActive())));

        functions.fillInv(inventory, 15);
        player.openInventory(inventory);
    }
    public void openPetEditor(Player player, Pet pet){

        Inventory inventory = Bukkit.createInventory(null, 9, "§7§l* §5§lPet Editor §7§l*");

        inventory.setItem(0, functions.createItemstack(Material.SKULL_ITEM, "§5§l" + pet.getEntityType().name(), functions.createArraylist("§7Op dit moment wordt deze pet bewerkt", "§7Eigenaar: §f" + pet.getOwner().getName())));
        inventory.setItem(4, functions.createItemstack(Material.NAME_TAG, "§7§l- §6Rename Pet §7(§3 " + pet.getName() + " §7) §7§l-", functions.createArraylist("§7Verander de naam van het huisdier")));
        inventory.setItem(5, functions.createItemstack(Material.TORCH, "§7§l- §6Pet Glow §7(§3 " + pet.hasGlow() + " §7) §7§l-", functions.createArraylist("§7Toggle dat het huisdier gloeit")));

        switch (pet.getEntityType()){
            case SHEEP:
                inventory.setItem(3, functions.createItemstack(Material.WOOL, "§7§l- §6Sheep Color §7(§3 " + pet.getPetcolor() + " §7) §7§l-", functions.createArraylist("§7Verander de kleur van het schaap")));
                inventory.setItem(6, functions.createItemstack(Material.ANVIL, "§7§l- §6Sheep Baby §7(§3 " + pet.isBaby() + " §7) §7§l-", functions.createArraylist("§7Verander de grootte van het schaap")));
                inventory.setItem(2, functions.createItemstack(Material.SHEARS, "§7§l- §6Sheep Sheared §7(§3 " + pet.isSheared() + " §7) §7§l-", functions.createArraylist("§7Zet aan/uit dat de schaap geschoren is")));
                break;
            case PARROT:
                inventory.setItem(3, functions.createItemstack(Material.STICK, "§7§l- §6Parrot Variant §7(§3 " + pet.getVariant() + " §7) §7§l-", functions.createArraylist("§7Verander het type papegaai")));
                inventory.setItem(6, functions.createItemstack(Material.ANVIL, "§7§l- §6Parrot Baby §7(§3 " + pet.isBaby() + " §7) §7§l-", functions.createArraylist("§7Verander de grootte van het schaap")));

                break;
        }
        functions.fillInv(inventory, 0);

        player.openInventory(inventory);
    }
}
