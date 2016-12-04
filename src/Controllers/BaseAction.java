package Controllers;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;


public class BaseAction extends ActionSupport implements SessionAware,  
        ServletRequestAware, ServletResponseAware {  
  
    private static final long serialVersionUID = 1L;  
 
    public HttpServletRequest   request;  
    public HttpServletResponse  response;  
    public ServletContext context;
    @SuppressWarnings("unchecked")
    public Map session;
  
    public void setSession(Map session) {
        this.session = session;  
    }  
  
    public void setServletRequest(HttpServletRequest request) {  
       this.request = request;  
    }  
  
    public void setServletResponse(HttpServletResponse response) {  
       this.response = response;  
    }
   
    public void setServletContext(ServletContext context){
    	this.context=context;
    }
}

