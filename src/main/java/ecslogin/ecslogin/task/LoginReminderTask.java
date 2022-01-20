package ecslogin.ecslogin.task;

import ecslogin.ecslogin.auth.Auth;
import ecslogin.ecslogin.command.LoginCommand;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.TimerTask;

public class LoginReminderTask extends BukkitRunnable {

    public Player player;

    public int maxLoginTime;

    public int counter = 0;

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setPlayerMaxLoginTime(int time) {
        this.maxLoginTime = time;
    }

    public void run() {

        if (Auth.isLoginCompleted.get(player) == null) {
            this.cancel();
        }

        if (!player.isOnline()) {
            Auth.isLoginCompleted.put(player, null);
            this.cancel();
        }

        if (counter == maxLoginTime) {
            Auth.isLoginCompleted.put(player, null);
            player.kickPlayer("你没有在规定的时间内输入密码");
            this.cancel();
        }

        player.sendMessage(String.format("[ECS-LOGIN]请输入命令 /login <密码> 或 /l <密码> 来登录服务器，你还有%s秒", maxLoginTime - counter));
        counter++;

    }
}
