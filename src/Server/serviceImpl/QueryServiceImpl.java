package Server.serviceImpl;

import Services.QueryService;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by Sun YuHao on 2016/12/1.
 */
public class QueryServiceImpl extends UnicastRemoteObject implements QueryService {
    private Crawler crawler;

    public QueryServiceImpl() throws RemoteException {
        crawler = new Crawler();
    }

    @Override
    public String[] queryWord(String word) {
        return crawler.queryAll(word);
    }

    @Override
    public void toggleApprovTranslation(String session, String word, String translator) throws RemoteException {
        try {
            int userId = 0;
            ResultSet res = DBManager.getConn().prepareStatement("SELECT user_id FROM Session WHERE session='" + session + "'").executeQuery();
            if (res.next()) {
                userId = res.getInt("user_id");
            }

            PreparedStatement updatePrep = DBManager.getConn().
                    prepareStatement("UPDATE UserWord SET approve=(approve = 0) WHERE user_id=? AND word=? AND translator=?");
            updatePrep.setInt(1, userId);
            updatePrep.setString(2, word);
            updatePrep.setString(3,translator);
            int count = updatePrep.executeUpdate();

            if (count == 0) {
                PreparedStatement insertUpdate = DBManager.getConn().
                        prepareStatement("INSERT INTO UserWord(user_id, approve, word, translator) VALUES(?,?,?,?)");
                insertUpdate.setInt(1, userId);
                insertUpdate.setBoolean(2, true);
                insertUpdate.setString(3, word);
                insertUpdate.setString(4, translator);
                insertUpdate.executeUpdate();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getApprovNum(String word, String translator) throws RemoteException {
        try {
            PreparedStatement prep = DBManager.getConn().
                    prepareStatement("SELECT COUNT(*) AS num FROM UserWord WHERE word=? AND translator=? AND approve=1");
            prep.setString(1, word);
            prep.setString(2, translator);
            ResultSet res = prep.executeQuery();
            res.next();
            return res.getInt("num");
        }catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
