package fakemt.fakemt.Kluizen;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import static fakemt.fakemt.Inventories.kluisInventory;

public class KluisInteract implements Listener {

    @EventHandler
    public void onKluisInteract(PlayerInteractEvent e){

        Player player = e.getPlayer();
        Block clicked = e.getClickedBlock();
        Location vaultLocation = clicked.getLocation();

        PlayerVault playerVault = new PlayerVault(player);

        if (e.getHand() == EquipmentSlot.OFF_HAND)return;
        if (playerVault.isValidVaultBlock(clicked.getType())){
            if (playerVault.hasVault()){
                Kluis kluis = playerVault.getVaultByLocation(vaultLocation);
                player.openInventory(kluisInventory(player, 1, kluis));
            }
        }
    }


}
