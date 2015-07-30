package yaweb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionMgr{
	public Map<String, Session> sessionMap = new HashMap<String, Session>(); 
	String random;
	public void process(Request request,Response response){
		String[] cookies=request.getCookies();
		ArrayList<Cookie> list = new ArrayList<Cookie>(cookies.length);
		for(int i=0;i<cookies.length;i++){
			if(cookies[i]!=null){
				list.add(new CookieImpl(cookies[i]));
			}
		}
		String sessionid=null;
		for(int i=0;i<list.size();i++){
			if("sessionid".equals(list.get(i).getKey())){
				sessionid=list.get(i).get("sessionid");
			}else {
				continue;
			}
			if(sessionid!=null)
				break;
		}
		
		if(sessionid==null){
			Session se=createSession(null);
			request.setSession(se);
			response.addHeader("Set-Cookie:","sessionid="+random);
			return;
		}		
		Session se = this.sessionMap.get(sessionid);
		if(se == null){
			se=createSession(sessionid);
			request.setSession(se);
			return;
		}
		request.setSession(se);
	}
	public Session createSession(String id){
		Session se=new Session();
		se.put("loginstate","false");
		random=UUID.randomUUID().toString();
		String str=((id == null)?random:id);
		sessionMap.put(str, se);
		return se;
	}
	public void dropSession(String id){	
		sessionMap.remove(id);
	}
}
