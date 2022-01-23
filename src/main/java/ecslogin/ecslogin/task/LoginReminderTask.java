package ecslogin.ecslogin.task;

import ecslogin.ecslogin.auth.Auth;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayer;

public class LoginReminderTask extends BukkitRunnable {

    public OfflinePlayer player;

    public int maxLoginTime;

    public int counter = 0;

    public void setPlayer(OfflinePlayer player) {
        this.player = player;
    }

    public void setPlayerMaxLoginTime(int time) {
        this.maxLoginTime = time;
    }

    public void run() {

        Player player = getPlayer(this.player.getUniqueId());

        if (player == null) return;

        if (Auth.isOnLoginSession.get(player) == null) {
            this.cancel();
            return;
        }

//        if (!player.isOnline()) {
//            Auth.isOnLoginSession.put(player, null);
//            this.cancel();
//            return;
//        }

        if (counter == maxLoginTime) {
            Auth.isOnLoginSession.put(player, null);
            player.kickPlayer("你没有在规定的时间内输入密码");
            this.cancel();
            return;
        }
        if (counter % 5 == 0)
            player.sendMessage(String.format("[ECS-LOGIN]请输入命令 /login <密码> 或 /l <密码> 来登录服务器，你还有%s秒", maxLoginTime - counter));
        counter++;

    }
}
