package dev.sharlot.damage_reporter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.chat.TranslatableComponent;

public class App extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            String playerDamagee = ((Player)e.getEntity()).getPlayerListName();
            String cause = e.getCause().name();
            String cause2 = "";
            String cause3 = "";
            // Bukkit.broadcastMessage(e.getEntity().getLastDamageCause().getEventName());
            if(e.getEntity().getLastDamageCause() instanceof EntityDamageByEntityEvent) {
                EntityDamageByEntityEvent a = (EntityDamageByEntityEvent)e.getEntity().getLastDamageCause();
                if(a.getDamager() instanceof Player) {
                    cause2 = " from " +  ((Player)a.getDamager()).getPlayerListName();
                } else {
                    cause2 = " from " + new TranslatableComponent(((Entity)a.getDamager()).getType().getTranslationKey()).toPlainText(); //ugly
                }
                if(a.getDamager() instanceof Projectile) {
                    if(a.getDamager() instanceof Player) {
                        cause3 = " from " + ((Player)((Projectile)a.getDamager()).getShooter()).getPlayerListName();
                    } else {
                        cause3 = " from " + new TranslatableComponent(((Entity)((Projectile)a.getDamager()).getShooter()).getType().getTranslationKey()).toPlainText(); //ugly
                    }
                }
            }
            Bukkit.broadcastMessage(playerDamagee + " took \u00A7c" + e.getDamage() + "\u00A7r from " + cause + cause2 + cause3);
        }
    }
    @EventHandler
    public void onHeal(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player) {
            String player = ((Player)e.getEntity()).getPlayerListName();
            Bukkit.broadcastMessage(player + " healed \u00A7a" + e.getAmount() + "\u00A7r from " + e.getRegainReason());
        }
    }
}
