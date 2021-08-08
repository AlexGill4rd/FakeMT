package fakemt.fakemt.ArmorstandStuff;

import fakemt.fakemt.Functions;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

public class ArmorstandHead implements Listener {

    Functions functions = new Functions();
    HashMap<UUID, ArmorStand> editor = new HashMap<>();

    @EventHandler
    public void onArmorstandInteract(PlayerInteractAtEntityEvent e){

        Player player = e.getPlayer();
        Entity entity = e.getRightClicked();

        if (entity instanceof ArmorStand){
            ArmorStand armorStand = (ArmorStand) entity;
            if (player.isSneaking()){
                if (functions.hasPerm(player, "armorstand.editor")){
                    armorStand.setCustomName("Editing...");

                    editor.put(player.getUniqueId(), armorStand);
                    openArmorstandMenu(player);
                    e.setCancelled(true);
                }
            }else{
                if (armorStand.getCustomName().equals("Editing...")){
                    e.setCancelled(true);
                    player.sendMessage(functions.getMessage("ArmorstandEditing"));
                }
            }
        }
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){

        String invnaam = e.getView().getTitle();
        ItemStack is = e.getCurrentItem();
        Player player = (Player) e.getWhoClicked();
        Inventory inventory = e.getInventory();
        if (invnaam.equals("§7§l* §6Armorstand Editor §7§l*")){
            e.setCancelled(true);
            if (is == null || is.getType() == Material.AIR || is.getType() == Material.BARRIER)return;
            ArmorStand armorStand = editor.get(player.getUniqueId());

            if (e.getClickedInventory() instanceof PlayerInventory){
                if (e.getClick().isLeftClick()){
                    if (e.getInventory().getItem(4).getType() != Material.BARRIER)player.getInventory().addItem(e.getInventory().getItem(4));
                    ItemStack clone = is.clone();
                    clone.setAmount(1);
                    inventory.setItem(4, clone);
                    showAccept(inventory);
                    ItemStack item = player.getInventory().getItem(e.getSlot());
                    item.setAmount(item.getAmount()-1);

                }else if (e.getClick().isRightClick()){
                    if (e.getInventory().getItem(3).getType() != Material.BARRIER)player.getInventory().addItem(e.getInventory().getItem(3));
                    ItemStack clone = is.clone();
                    clone.setAmount(1);
                    inventory.setItem(3, clone);
                    showAccept(inventory);
                    ItemStack item = player.getInventory().getItem(e.getSlot());
                    item.setAmount(item.getAmount()-1);
                }
                return;
            }
            switch (e.getSlot()){
                case 5:
                    if (is.getItemMeta().getDisplayName().toLowerCase().contains("false"))e.getInventory().setItem(5, functions.createItemstack(Material.ARMOR_STAND, "§6Small §7(§fTrue§7)", functions.createArraylist("§8Verander de grootte van de armorstand")));
                    else e.getInventory().setItem(5, functions.createItemstack(Material.ARMOR_STAND, "§6Small §7(§fFalse§7)", functions.createArraylist("§8Verander de grootte van de armorstand")));
                    showAccept(inventory);
                    break;
                case 3:
                    if (is.getType() == Material.BARRIER)return;
                    player.getInventory().addItem(e.getInventory().getItem(3));
                    e.getInventory().setItem(3, getToolItemstackRaw());
                    showAccept(inventory);
                    break;
                case 4:
                    if (is.getType() == Material.BARRIER)return;
                    player.getInventory().addItem(e.getInventory().getItem(4));
                    e.getInventory().setItem(4, getHeadItemstackRaw());
                    showAccept(inventory);
                    break;
                case 8:
                    if (is.getType() != Material.GREEN_SHULKER_BOX)return;
                    if (e.getInventory().getItem(4).getType() != Material.BARRIER){
                        ItemStack clone = e.getInventory().getItem(4).clone();
                        clone.setAmount(1);
                        armorStand.setHelmet(clone);
                    }else armorStand.setHelmet(null);
                    if (e.getInventory().getItem(3).getType() != Material.BARRIER){
                        ItemStack clone = e.getInventory().getItem(3).clone();
                        clone.setAmount(1);
                        armorStand.setItemInHand(clone);
                    }else armorStand.setItemInHand(null);

                    armorStand.setSmall(e.getInventory().getItem(5).getItemMeta().getDisplayName().toLowerCase().contains("true"));
                    armorStand.setCustomName(" ");
                    editor.remove(player.getUniqueId());
                    player.closeInventory();
                    break;
            }
        }
    }
    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        String invnaam = e.getView().getTitle();
        Player player = (Player) e.getPlayer();
        if (invnaam.equals("§7§l* §6Armorstand Editor §7§l*")){
            ArmorStand armorStand = editor.get(player.getUniqueId());
            if (editor.containsKey(player.getUniqueId())){
                if (!e.getInventory().getItem(3).equals(armorStand.getItemInHand())){
                    player.getInventory().removeItem(armorStand.getItemInHand());
                    if (e.getInventory().getItem(3).getType() != Material.BARRIER) player.getInventory().addItem(e.getInventory().getItem(3));
                    e.getInventory().setItem(4, null);
                }
                if (!e.getInventory().getItem(4).equals(armorStand.getHelmet())){
                    player.getInventory().removeItem(armorStand.getHelmet());
                    if (e.getInventory().getItem(4).getType() != Material.BARRIER) player.getInventory().addItem(e.getInventory().getItem(4));
                }
                armorStand.setCustomName(" ");
                editor.remove(player.getUniqueId());
            }
        }
    }

    private void openArmorstandMenu(Player player){
        ArmorStand armorStand = editor.get(player.getUniqueId());
        Inventory inventory = Bukkit.createInventory(null, 9, "§7§l* §6Armorstand Editor §7§l*");

        ItemStack helm;
        ItemStack tool;

        if (armorStand.getHelmet().getType() != Material.AIR) helm = armorStand.getHelmet();
        else helm = getHeadItemstackRaw();

        if (armorStand.getItemInHand().getType() != Material.AIR) tool = armorStand.getItemInHand();
        else tool = getToolItemstackRaw();

        inventory.setItem(5, functions.createItemstack(Material.ARMOR_STAND, "§6Small §7(§f" + StringUtils.capitalize(String.valueOf(armorStand.isSmall()).toLowerCase()) + "§7)", functions.createArraylist("§7Left-Click §7§l- §8Toggle armorstand size")));
        inventory.setItem(4, helm);
        inventory.setItem(3, tool);
        inventory.setItem(0, functions.createItemstack(Material.PAPER, "§7§l- §9§lINFO §7§l-", functions.createArraylist("§6§lPlayer Inventory", "§7§l§m--------------------", "§7Left-Click §f-> §7Verander armorstand helmet", "§7Right-Click §f-> §7Verander armorstand tool", " ", "§6§lArmorstand Inventory", "§7§l§m--------------------", "§7Left-Click §f-> §7Haal het item weg van het slot", "§a§lGREEN SHULKER §f-> §7Pas de aanpassing toe")));

        functions.fillInv(inventory, 15);

        player.openInventory(inventory);
    }
    private void showAccept(Inventory inventory){inventory.setItem(8, functions.createItemstack(Material.GREEN_SHULKER_BOX, "§a§aAccepteer", functions.createArraylist("§8Bij klikken op de knop wordt de", "§8tool van de armorstand verandert!")));}
    private ItemStack getHeadItemstackRaw(){return functions.createItemstack(Material.BARRIER, "§7§l- §6Verander Helm §7§l-", functions.createArraylist("§7Verander het item dat de armorstand", "§7op zijn hoofd heeft"));}
    private ItemStack getToolItemstackRaw(){return functions.createItemstack(Material.BARRIER, "§7§l- §6Verander Tool §7§l-", functions.createArraylist("§7Verander de tool dat de armorstand", "§7in zijn hand vasthoudt"));}
}
