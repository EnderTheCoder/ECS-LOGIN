package ecslogin.ecslogin.event;

import ecslogin.ecslogin.ECSLOGIN;
import ecslogin.ecslogin.auth.Auth;
import ecslogin.ecslogin.config.ConfigReader;
import ecslogin.ecslogin.task.LoginReminderTask;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import static org.bukkit.Bukkit.*;
//import static org.bukkit.Bukkit.*;

public class LoginEvent implements Listener {
    @EventHandler
    public void login(AsyncPlayerPreLoginEvent event) {
        getLogger().info(String.format("User(%s) login event detected.", event.getName()));
        OfflinePlayer player = getOfflinePlayer(event.getUniqueId());
        Auth.isOnLoginSession.put(player, false);

        if (!Auth.isUserExists(player)) {

            event.setKickMessage("你没有在ECS注册，请前往官网http://ecs-mc.xyz/注册并答题通过");
//            player.kickPlayer("你没有在ECS注册，请前往官网http://ecs-mc.xyz/注册并答题通过");

            return;
        }
        if (Auth.getUserLevel(player) == 0) {
            event.setKickMessage("你已在ECS注册，但未通过答题认证，请前往官网http://ecs-mc.xyz/进行答题");
            return;
        }
        if (!(Auth.isUserBanned(player) == null)) {
            event.setKickMessage(String.format("你已被ECS封禁，原因：%s", Auth.isUserBanned(player)));
            return;
        }
        LoginReminderTask l = new LoginReminderTask();
        l.setPlayer(player);
        l.setPlayerMaxLoginTime(ConfigReader.getPlayerMaxLoginTime());
        l.runTaskTimer(ECSLOGIN.instance, 1, ConfigReader.getPlayerMaxLoginTime());
    }
    @EventHandler
    public void quit(PlayerQuitEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null)
            Auth.isOnLoginSession.put(event.getPlayer(), null);
    }

    //禁止移动
    @EventHandler
    public void move(PlayerMoveEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //登录期间无敌保护
    @EventHandler
    public void damage(PlayerItemDamageEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止使用物品
    @EventHandler
    public void consume(PlayerItemConsumeEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止登录期间输入命令
    @EventHandler
    public void command(PlayerCommandPreprocessEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            if (!(event.getMessage().contains("l") || event.getMessage().contains("login")))
                event.setCancelled(true);
        }
    }
    //禁止交互实体
    @EventHandler
    public void entity(PlayerInteractEntityEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止扔出物品
    @EventHandler
    public void drop(PlayerDropItemEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止捡起物品
    @EventHandler
    public void pickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            if (Auth.isOnLoginSession.get((Player) event.getEntity()) != null) {
                event.setCancelled(true);
            }
        }
    }
    //禁止聊天
    @EventHandler
    public void destroy(AsyncPlayerChatEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止使用传送门
    @EventHandler
    public void portal(PlayerPortalEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止物品被破坏
    @EventHandler
    public void itemDamage(PlayerItemDamageEvent event) {
        if (Auth.isOnLoginSession.get(event.getPlayer()) != null) {
            event.setCancelled(true);
        }
    }
    //禁止打开物品栏
    @EventHandler
    public void openInventory(InventoryOpenEvent event) {
        if (Auth.isOnLoginSession.get(getOfflinePlayer(event.getPlayer().getUniqueId())) != null) {
            event.setCancelled(true);
        }
    }
}
