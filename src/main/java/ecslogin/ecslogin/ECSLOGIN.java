package ecslogin.ecslogin;

import ecslogin.ecslogin.command.LoginCommand;
import ecslogin.ecslogin.database.Mysql;
import ecslogin.ecslogin.event.LoginEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class ECSLOGIN extends JavaPlugin {
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();


        Mysql m = new Mysql();
        if (!m.mysqlInit()) {
            getLogger().warning(ChatColor.RED + "Failed to connect to mysql. Check your config.yml to fix this. Plugin is shutting down.");
            getServer().getPluginManager().disablePlugin(this);
        } else {
            getLogger().info(ChatColor.GREEN + "Mysql connected.");
        }
        if (Bukkit.getPluginCommand("login") != null) {
            Objects.requireNonNull(Bukkit.getPluginCommand("login")).setExecutor(new LoginCommand());
        }
        Bukkit.getPluginManager().registerEvents(new LoginEvent(), this);
        getLogger().info(ChatColor.GREEN + "ECS-LOGIN登录插件启动成功，本插件由全知全能的Ender编写");

    }

    @Override
    public void onDisable() {
    }
}
