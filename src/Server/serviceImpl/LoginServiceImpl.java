package Server.serviceImpl;

import Services.LoginService;
import Utils.MD5Util;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * Created by Sun YuHao on 2016/12/3.
 */
public class LoginServiceImpl extends UnicastRemoteObject implements LoginService {
    public LoginServiceImpl() throws RemoteException {
    }

    private String OK(String session) {
        return "ok;0;" + session;
    }

    private String ERR(int errno) {
        switch (errno) {
            case 1:case 2:case 3:case 4:case 5:
                return "err;" + errno;
            default:
                return "err;-1";
        }
    }

    @Override
    public String login(String username, String password) throws RemoteException {
        try {
            PreparedStatement prep = DBManager.getConn().prepareStatement("SELECT * FROM User WHERE username=?");
            prep.setString(1, username);
            ResultSet res = prep.executeQuery();
            while (res.next()) {
                String pass = res.getString("password");
                int id = res.getInt("id");
                if (pass.equals(password)) {
                    return OK(updateSession(username, password, id));
                }
                else {
                    return ERR(2);
                }
            }

            return ERR(1);
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String register(String username, String password) throws RemoteException {
        if (!validUserName(username)) {
            return ERR(4);
        } else if (!validPassword(password)) {
            return ERR(3);
        } else if (existUser(username)) {
            return ERR(5);
        }

        try {
            PreparedStatement prep = DBManager.getConn().prepareStatement("INSERT INTO User(username, password) VALUES(?,?)");
            prep.setString(1, username);
            prep.setString(2, password);
            prep.executeUpdate();

            return login(username,password);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String updateSession(String username, String password, int id) {
        try {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String session = MD5Util.MD5(username + password + timestamp.toString());
            PreparedStatement insertPrep = DBManager.getConn().prepareStatement("INSERT INTO Session VALUES (?,?,?)");
            PreparedStatement updatePrep = DBManager.getConn().prepareStatement("UPDATE Session SET session=? WHERE user_id=?");
            updatePrep.setString(1, session);
            updatePrep.setInt(2, id);
            int count = updatePrep.executeUpdate();

            if (count == 0) {
                insertPrep.setInt(1, id);
                insertPrep.setString(2, session);
                insertPrep.setTimestamp(3, timestamp);
                insertPrep.executeUpdate();
            }

            return session;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private boolean validUserName(String username) {
        return  (username.length() >= 4 && username.length() <= 20);
    }

    private boolean validPassword(String password) {
        return (password.length() >= 6 && password.length() <= 20);
    }

    private boolean existUser(String username) {
        try {
            PreparedStatement prep = DBManager.getConn().prepareStatement("SELECT * FROM User WHERE username=?");
            prep.setString(1, username);
            ResultSet res = prep.executeQuery();
            return  res.next();
        }
        catch (Exception e) {
            e.printStackTrace();
            return true;
        }
    }
}
