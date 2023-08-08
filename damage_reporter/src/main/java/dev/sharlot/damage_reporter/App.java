package dev.sharlot.damage_reporter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.chat.TranslatableComponent;

public class App extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamageEntity(EntityDamageByEntityEvent e) { // we do this instead of just using EntityDamageEvent and getting getLastDamageCause bc getLastDamageCause seems to not always update correctly
        if(e.getEntity() instanceof Player) {
            String playerDamagee = ((Player)e.getEntity()).getPlayerListName();
            String cause = e.getCause().name();
            String cause2 = "";
            String cause3 = "";
            if(e.getDamager() instanceof Player) {
                cause2 = " from " +  ((Player)e.getDamager()).getPlayerListName();
            } else {
                cause2 = " from " + new TranslatableComponent(((Entity)e.getDamager()).getType().getTranslationKey()).toPlainText(); //ugly
            }
            if(e.getDamager() instanceof Projectile) {
                if(e.getDamager() instanceof Player) {
                    cause3 = " from " + ((Player)((Projectile)e.getDamager()).getShooter()).getPlayerListName();
                } else {
                    cause3 = " from " + new TranslatableComponent(((Entity)((Projectile)e.getDamager()).getShooter()).getType().getTranslationKey()).toPlainText(); //ugly
                }
            }
            Bukkit.broadcastMessage(playerDamagee + " took \u00A7c" + e.getDamage() + "\u00A7r from " + cause + cause2 + cause3);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDamage(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.ENTITY_EXPLOSION && e.getCause() != DamageCause.ENTITY_SWEEP_ATTACK && e.getCause() != DamageCause.PROJECTILE) {
                String playerDamagee = ((Player)e.getEntity()).getPlayerListName();
                Bukkit.broadcastMessage(playerDamagee + " took \u00A7c" + e.getDamage() + "\u00A7r from " + e.getCause().name());
            }
        }
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onHeal(EntityRegainHealthEvent e) {
        if(e.getEntity() instanceof Player) {
            String player = ((Player)e.getEntity()).getPlayerListName();
            Bukkit.broadcastMessage(player + " healed \u00A7a" + e.getAmount() + "\u00A7r from " + e.getRegainReason());
        }
    }
}
