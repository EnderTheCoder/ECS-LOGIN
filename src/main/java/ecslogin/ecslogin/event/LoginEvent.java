package ecslogin.ecslogin.event;

import ecslogin.ecslogin.ECSLOGIN;
import ecslogin.ecslogin.auth.Auth;
import ecslogin.ecslogin.config.ConfigReader;
import ecslogin.ecslogin.task.LoginReminderTask;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;

public class LoginEvent implements Listener {
    @EventHandler
    public void login(PlayerJoinEvent event) {
        getLogger().info("User login event detected.");
        Player player = event.getPlayer();


        Auth.isLoginCompleted.put(player, false);


        if (!Auth.isUserExists(player)) {
            player.kickPlayer("你没有在ECS注册，请前往官网http://ecs-mc.xyz/注册并答题通过");
            return;
        }
        if (Auth.getUserLevel(player) == 0) {
            player.kickPlayer("你已在ECS注册，但未通过答题认证，请前往官网http://ecs-mc.xyz/进行答题");
            return;
        }
        if (!(Auth.isUserBanned(player) == null)) {
            player.kickPlayer(String.format("你已被ECS封禁，原因：%s", Auth.isUserBanned(player)));
            return;
        }
//        if (Auth.isPlayerFirstLogin(player)) {
//            player.sendMessage(ChatColor.GREEN + String.format("[ECS-LOGIN]新玩家%s您好，欢迎加入ECS机械动力服务器，请开始您的建造之旅吧", player.getDisplayName()));
//        }
//        Timer t = new Timer();
        LoginReminderTask l = new LoginReminderTask();
        l.setPlayer(player);
        l.setPlayerMaxLoginTime(ConfigReader.getPlayerMaxLoginTime());
//        t.schedule(l, 1, ConfigReader.getPlayerMaxLoginTime());
        l.runTaskTimer(ECSLOGIN.instance, 1, ConfigReader.getPlayerMaxLoginTime());
    }
}
