package Services;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Sun YuHao on 2016/12/1.
 */
public interface QueryService {
    String[] queryWord(String word);
    void toggleApprovTranslation(String session, String word, String translator);
    int getApprovNum(String word, String translator);
}
