package fakemt.fakemt.Huisdieren;

import fakemt.fakemt.Functions;
import fakemt.fakemt.Head.Configs;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.io.File;

public class PetCommand implements CommandExecutor, Listener {
    Functions functions = new Functions();
    PetInventories petInventories = new PetInventories();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("pet")){

            if (sender instanceof Player){
                Player player = (Player) sender;
                if (args.length == 0){
                    if (functions.hasPerm(player, "pet.help")){
                        sendHelp(player);
                    }
                }else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        if (functions.hasPerm(player, "pet.help")) {
                            sendHelp(player);
                        }
                    } else if (args[0].equalsIgnoreCase("menu")) {
                        if (functions.hasPerm(player, "pet.menu")) {
                            petInventories.openMainMenu(player);
                        }
                    } else if (args[0].equalsIgnoreCase("info")) {
                        if (functions.hasPerm(player, "pet.info")) {
                            PetData petData = new PetData(player);
                            player.sendMessage("§7§l§m------------§8§l [ §e§lINFO §8§l] §7§l§m------------");
                            if (petData.getActivePet() == null) {
                                player.sendMessage("§7Je hebt op dit moment geen huisdier actief!");
                                return true;
                            }
                            Pet pet = petData.getActivePet();
                            player.sendMessage("§6Huisdier Naam: §7" + pet.getName());
                            player.sendMessage("§6Huisdier Type: §7" + pet.getEntityType().name());
                            player.sendMessage("§6Huisdier Eigenaar: §7" + pet.getOwner().getName());
                            player.sendMessage("§7§l§m-----------------------------");
                        }
                    }
                }else if (args.length == 3){
                    if (args[0].equalsIgnoreCase("edit")){
                        if (functions.hasPerm(player, "pet.edit")){
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                            PetData petData = new PetData(target);
                            if (petData.hasAPet()){
                                EntityType pettype = EntityType.valueOf(args[2].toUpperCase());
                                if (functions.getAllEntityTypes().contains(pettype)){
                                    if (petData.hasPet(pettype)){
                                        if (petData.hasLoadedPet()){
                                            if (petData.getActivePet().getEntityType() == pettype){
                                                player.sendMessage(functions.getMessage("PetEquipped"));
                                                return true;
                                            }
                                        }
                                        petInventories.openPetEditor(player, new Pet(pettype, target));
                                    }else player.sendMessage(functions.getMessage("PlayerNotOwnPet"));
                                }else player.sendMessage(functions.getMessage("InvalidPet"));
                            }else player.sendMessage(functions.getMessage("NoPet"));
                        }
                    }
                }else if (args.length >= 4){
                    if (args[0].equalsIgnoreCase("give")){
                        if (functions.hasPerm(player, "pet.give")){
                            OfflinePlayer target = Bukkit.getOfflinePlayer(args[1]);
                            PetData petData = new PetData(target);
                            EntityType entityType;
                            try {
                                entityType = EntityType.valueOf(args[2].toUpperCase());
                            }catch (IllegalArgumentException e){
                                player.sendMessage(functions.getMessage("InvalidPet"));
                                return true;
                            }
                            if (!functions.getAllEntityTypes().contains(entityType)){player.sendMessage(functions.getMessage("InvalidPet")); return true; }
                            if (petData.hasPet(entityType)){ player.sendMessage(functions.getMessage("PetOwned"));return true; }

                            petData.addPetToPlayer(new Pet(entityType, target), args[3]);
                            player.sendMessage(functions.getMessage("AddedPet"));
                        }
                    }
                }
            }
        }
        return false;
    }
    private void sendHelp(Player player){
        player.sendMessage("§7§l* §6/Pet §ehelp                                                §7Bekijk de mogelijke commando's");
        player.sendMessage("§7§l* §6/Pet §emenu                                               §7Bekijk een menu met al de informatie over huisdieren");
        player.sendMessage("§7§l* §6/Pet §egive §7<§cplayer§7> §7<§cpet§7> §7<§cpetnaam§7>    §7Geef een speler een huidier");
        player.sendMessage("§7§l* §6/Pet §eremove §7<§cplayer§7> §7<§cpet§7>                  §7Verwijder een huisdier van een speler");
        player.sendMessage("§7§l* §6/Pet §einfo                                                §7Bekijk al de informatie over een huisdier");
        player.sendMessage("§7§l* §6/Pet §eedit §7<§cplayer§7> §7<§cpet§7>                      §7Bewerk een speler zijn huisdier");
    }

}
