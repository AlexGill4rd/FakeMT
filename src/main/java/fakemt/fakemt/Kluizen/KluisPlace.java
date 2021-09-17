package fakemt.fakemt.Kluizen;

import fakemt.fakemt.Functions;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class KluisPlace implements Listener {

    @EventHandler
    public void onVaultPlace(BlockPlaceEvent e){
        Player player = e.getPlayer();
        PlayerVault playerVault = new PlayerVault(player);
        Material hand = e.getBlockPlaced().getType();
        Functions functions = new Functions();
        if (playerVault.isValidVaultBlock(hand)){
            if (functions.hasPerm(player, "vault.place")){
                String kluisnaam = functions.getItemDisplayname(player.getInventory().getItemInMainHand());
                Kluis kluis = new Kluis(kluisnaam, player);
                if (playerVault.getPlayerVaults().contains(kluis)){
                    e.setCancelled(true);
                    return;
                }
                kluis.setLocation(e.getBlockPlaced().getLocation());
                player.sendMessage(functions.getMessage("Vault Placed"));
            }
        }
    }

}
