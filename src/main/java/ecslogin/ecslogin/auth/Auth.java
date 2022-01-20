package ecslogin.ecslogin.auth;

import ecslogin.ecslogin.database.Mysql;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.bukkit.Bukkit.getLogger;

public class Auth {
    public static Map<Player, Boolean> isLoginCompleted = new HashMap<>();;

    public static boolean authPassword(Player player, String password) {
        ResultSet resultSet = getPlayerData(player);
        String realPassword = "";
        try {
            assert resultSet != null;
            resultSet.next();
            realPassword = resultSet.getString("password");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return realPassword.equals(password);
    }

    public static boolean isUserExists(Player player) {
        ResultSet resultSet = getPlayerData(player);
        return !(resultSet == null);
    }

    public static String isUserBanned(Player player) {
        ResultSet resultSet = getPlayerData(player);
        String bannedReason = null;
        try {
            assert resultSet != null;
            resultSet.next();
            bannedReason = resultSet.getString("banned_reason");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bannedReason;
    }

    public static boolean isPlayerFirstLogin(Player player) {
        ResultSet resultSet = getPlayerData(player);
        try {
            assert resultSet != null;
            resultSet.next();
            return  (resultSet.getString("uuid") == null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getUserLevel(Player player) {
        ResultSet resultSet = getPlayerData(player);
        int userLevel = 0;
        try {
            assert resultSet != null;
            resultSet.next();
            userLevel = resultSet.getInt("level");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userLevel;
    }



    public static ResultSet getPlayerData(Player player) {
        Mysql m = new Mysql();
        m.prepareSql("SELECT * FROM user WHERE username = ?");
        m.setData(1, player.getName());
        m.execute();
        return m.getResult();

        //        try {
//            if (resultSet.getRow() > 0) return resultSet;
//            else return null;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return null;
//        }
    }
}
