package ecslogin.ecslogin.init;

import ecslogin.ecslogin.database.Mysql;
import org.bukkit.entity.Player;

public class NewUserInit {
    public static void addConfig(Player player) {
        Mysql m = new Mysql();
        m.prepareSql("INSERT INTO user_config (uuid, username, config) VALUES (?, ?, ?)");
        m.setData(1, String.valueOf(player.getUniqueId()));
        m.setData(2, player.getName());
        m.setData(3, "{}");
        m.execute();
    }
    public static void setUserUUID(Player player) {
        Mysql m = new Mysql();
        m.prepareSql("UPDATE user SET uuid = ? WHERE username = ?");
        m.setData(2, player.getName());
        m.setData(1, String.valueOf(player.getUniqueId()));
        m.execute();
    }
    public static void init(Player player) {
        addConfig(player);
        setUserUUID(player);
    }
}
