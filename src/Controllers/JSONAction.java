package Controllers;

import Utils.JsonUtil;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sun YuHao on 2016/9/3.
 */
@Controller
public class JSONAction extends BaseAction {
    private Map requestMap;
    private String textResult;
    private Object result;

    @Override
    public void setServletRequest(HttpServletRequest request) {
        super.setServletRequest(request);

        try {
            requestMap = new JsonUtil(request.getInputStream()).getMap();
            if (requestMap == null) {
                requestMap = new HashMap();
            }
            else {
                Integer id = (Integer)requestMap.get("id");
            }
        }
        catch (IOException io) {
            io.printStackTrace();
            requestMap = new HashMap();
        }
    }

    protected Map getRequestMap() {
        return requestMap;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getTextResult() {
        return textResult;
    }

    public void setTextResult(String textResult) {
        this.textResult = textResult;
    }
}
