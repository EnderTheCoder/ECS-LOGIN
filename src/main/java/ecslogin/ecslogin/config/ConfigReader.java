package ecslogin.ecslogin.config;

import ecslogin.ecslogin.ECSLOGIN;
import org.bukkit.configuration.file.FileConfiguration;

public final class ConfigReader {

    static FileConfiguration config = ECSLOGIN.instance.getConfig();

    public static String getMysqlConfig(String mysqlConfigTag) {
        return config.getString(mysqlConfigTag);
    }

    public static int getPlayerMaxLoginTime() {
        return config.getInt("PlayerMaxLoginTime");
    }
}