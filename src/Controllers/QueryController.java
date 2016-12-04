package Controllers;

import Server.serviceImpl.QueryServiceImpl;
import Services.QueryService;
import org.hibernate.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Sun YuHao on 2016/12/4.
 */
@Controller
public class QueryController extends JSONAction {
    private QueryService queryService;

    QueryController() {
        queryService = new QueryServiceImpl();
    }

    public String queryWord() {
        String word = request.getParameter("word");
        setResult(new QuerySet(queryService.queryWord(word)));

        return SUCCESS;
    }

    public String toggleApprovTranslation() {
        String session = request.getParameter("isession");
        String word = request.getParameter("word");
        String translator = request.getParameter("translator");
        setResult("");

        queryService.toggleApprovTranslation(session, word, translator);

        return SUCCESS;
    }

    public String getApprovNum() {
        String word = request.getParameter("word");
        String translator = request.getParameter("translator");

        setResult(new NumRes(queryService.getApprovNum(word, translator)));

        return SUCCESS;
    }

    public class QuerySet {
        public String baidu;
        public String youdao;
        public String bing;

        public QuerySet(String[] res) {
            baidu = res[0];
            youdao = res[1];
            bing = res[2];
        }

        public String getBaidu() {
            return baidu;
        }

        public void setBaidu(String baidu) {
            this.baidu = baidu;
        }

        public String getYoudao() {
            return youdao;
        }

        public void setYoudao(String youdao) {
            this.youdao = youdao;
        }

        public String getBing() {
            return bing;
        }

        public void setBing(String bing) {
            this.bing = bing;
        }
    }

    public class NumRes {
        public int numApprov;

        public NumRes(int numApprov) {
            this.numApprov = numApprov;
        }

        public int getNumApprov() {
            return numApprov;
        }

        public void setNumApprov(int numApprov) {
            this.numApprov = numApprov;
        }
    }
}
