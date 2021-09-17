package fakemt.fakemt.Tools;

import fakemt.fakemt.FakeMT;
import fakemt.fakemt.Functions;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.material.Stairs;
import org.spigotmc.event.entity.EntityDismountEvent;

import java.util.HashMap;
import java.util.UUID;

public class Shairs implements Listener {

    Functions functions = new Functions();
    public static HashMap<UUID, ArmorStand> sitters = new HashMap<>();
    public static HashMap<UUID, Location> clickLocations = new HashMap<>();
    FakeMT plugin = FakeMT.getPlugin(FakeMT.class);

    @EventHandler
    public void onPlayerShairClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Block clicked = e.getClickedBlock();

        if (e.getHand() == EquipmentSlot.OFF_HAND)return;

        if (e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if (isSitBlock(player, clicked)){
                clickLocations.put(player.getUniqueId(), player.getLocation().clone());
                bindPlayerToShair(player, clicked);
            }
        }
    }
    @EventHandler
    public void onPlayerShairClick(EntityDismountEvent e){
        Entity entity = e.getDismounted();
        if (entity instanceof ArmorStand){
            ArmorStand armorstand = (ArmorStand) entity;
            if (!armorstand.getCustomName().equals("shair"))return;
            if (e.getEntity() instanceof Player){
                Player player = (Player) e.getEntity();
                unBindPlayerFromShair(player);
            }
        }
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        if (sitters.containsKey(player.getUniqueId())){
            unBindPlayerFromShair(player);
        }
    }
    private boolean isSitBlock(Player player, Block shair){
        if (shair.getType().toString().toLowerCase().contains("stairs")){
            if (shair.getLocation().clone().add(0, 1, 0).getBlock().getType() == Material.AIR){
                if (!sitters.containsKey(player.getUniqueId())){
                    Stairs stairs = (Stairs) shair.getState().getData();
                    if (!stairs.isInverted())
                        return player.getLocation().distance(shair.getLocation().clone().add(0.5, 0, 0.5)) <= 1.5f;
                }
            }
        }
        return false;
    }
    private void bindPlayerToShair(Player player, Block shair){
        final Location loc = shair.getLocation().clone().add(0.5, -0.5f, 0.5);
        Stairs stairs = (Stairs) shair.getState().getData();
        int rotation = 0;
        if (shair.getType().toString().toLowerCase().contains("stairs")){
            if (stairs.getFacing() == BlockFace.EAST)
                rotation = -90;
            else if (stairs.getFacing() == BlockFace.NORTH)
                rotation = -180;
            else if (stairs.getFacing() == BlockFace.WEST)
                rotation = 90;
        }
        loc.setYaw(rotation);

        ArmorStand armorStand = (ArmorStand) player.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        armorStand.setPassenger(player);
        armorStand.setInvulnerable(true);
        armorStand.setVisible(false);
        armorStand.setSilent(true);
        armorStand.setSmall(true);
        armorStand.setCustomName("shair");
        armorStand.setCustomNameVisible(false);
        armorStand.setGravity(false);

        sitters.put(player.getUniqueId(), armorStand);
    }
    private void unBindPlayerFromShair(Player player){
        ArmorStand armorStand = sitters.get(player.getUniqueId());
        armorStand.remove();
        sitters.remove(player.getUniqueId());

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            Location teleportLoc = clickLocations.get(player.getUniqueId());
            player.teleport(teleportLoc);

            clickLocations.remove(player.getUniqueId());
        }, 1);

    }
}
