package ecslogin.ecslogin.command;

import ecslogin.ecslogin.auth.Auth;
import ecslogin.ecslogin.init.NewUserInit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LoginCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        if (args.length != 1) return false;

        Player player = (Player) sender;

        if (Auth.isLoginCompleted.get(player) == null) {
            player.sendMessage(ChatColor.YELLOW + "[ECS-LOGIN]你已经登录，无需再次登录");
            return true;
        }

        if (Auth.authPassword(player, args[0])) {
            sender.sendMessage(ChatColor.GREEN + "[ECS-LOGIN]登陆成功！欢迎进入ECS机械动力服务器，致力于创建最具创造力的机械动力服务器。");
            if (Auth.isPlayerFirstLogin(player)) {
                NewUserInit.init(player);
                player.sendMessage(ChatColor.GREEN + String.format("[ECS-LOGIN]新玩家%s您好，欢迎加入ECS机械动力服务器，请开始您的建造之旅吧，有问题随时可群内提问。", player.getDisplayName()));
            }
            Auth.isLoginCompleted.put(player, null);
            return true;
        } else {
            player.kickPlayer("你输入了错误的密码");
        }

        return true;
    }
}
