package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Sun YuHao on 2016/12/1.
 */
public interface QueryService extends Remote {
    String[] queryWord(String word) throws RemoteException;
    void toggleApprovTranslation(String session, String word, String translator) throws RemoteException;
    int getApprovNum(String word, String translator) throws RemoteException;
}
