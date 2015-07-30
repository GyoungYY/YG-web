package yaweb;

import java.io.IOException;
import java.util.Map;

public interface Request {
	
	public String getUrl();
	
	public Map<String,String> getHeaders();
	
	public String getHeader(String key);
	
	public String getMethod();

	public String[] getCookies();
	
	public void proccess() throws IOException;
	
	/**
	 * GET请求时，将url中?后的键值对获取，POST请求时，将body的键值对获取
	 * @return
	 */
	public Map<String,String> getParameters();
	
	public String getParameter(String key);
//	
	
	public User getUser();

	public void setUrl(String string);
//
	public void setUsername(String string);
//
	public String getUsername();
	
	public void setSession(Session ss);
	
	public Session getSession();

}
