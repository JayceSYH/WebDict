package Client.ClientService;

import Services.QueryService;
import Utils.GetUtil;
import Utils.JsonUtil;

import java.util.Map;

/**
 * Created by Sun YuHao on 2016/12/4.
 */
public class QueryServiceClient implements QueryService {
    @Override
    public String[] queryWord(String word) {
        String ret = GetUtil.sendGet(GetUtil.url + "/query", "word=" + word);
        JsonUtil jsonUtil = new JsonUtil(ret);
        Map map = jsonUtil.getMap();

        String[] retval = new String[3];
        retval[0] = (String)map.get("baidu");
        retval[1] = (String)map.get("youdao");
        retval[2] = (String)map.get("bing");

        return retval;
    }

    @Override
    public void toggleApprovTranslation(String session, String word, String translator) {
        GetUtil.sendGet(GetUtil.url + "/toggleApprov", "word=" + word + "&translator=" + translator +
                "&isession=" + session);
    }

    @Override
    public int getApprovNum(String word, String translator) {
        String ret = GetUtil.sendGet(GetUtil.url + "/getApprov", "word=" + word + "&translator=" + translator);
        JsonUtil jsonUtil = new JsonUtil(ret);
        Map map = jsonUtil.getMap();

        return (int)map.get("numApprov");
    }
}
