package yaweb;

public class Filter {
//	SessionMgr sMgr;
//	public Filter(SessionMgr s){
//		sMgr=s;
//	}
	/**
	 * 
	 * @param request
	 */
	public void doFilter(Request request){
		String uri = request.getUrl();
/*		if ("/userlist.html".equals(uri)){
			if (!"admin".equals(request.getUsername())){
				request.setUrl("/");
				return;
			}	
		}*/
/*		if ("/userdata.html".equals(uri)&&sMgr.sessionMap.containsKey(session)){
			request.setUsername(sMgr.sessionMap.get(session).id);
			return;
		}*/
		
		if ("/jquery-1.3.2.min.js".equals(uri)){
			return;
		}
		Session session = request.getSession();
		String loginstate=session.get("loginstate");
/*		if("true".equals(loginstate)&&"/userlist.html".equals(uri)){
			if("admin".equals(request.getUsername()))
				return;
			else
				request.setUrl("/");
		}else if("true".equals(loginstate)&&isUserUri(uri)){
			request.setUrl("/");
		}else if("true".equals(loginstate)&&!isUserUri(uri)){
			return;
		}else{
			request.setUrl("/login.html");
		}
	}
*/
/*		if("true".equals(loginstate)&&!isUserUri(uri)){
			if("/userlist.html".equals(uri)&&("admin".equals(request.getUsername()))){
				return;
			}
			if("/userlist.html".equals(uri)&&!("admin".equals(request.getUsername())))
				request.setUrl("/");
			else*/
		if("true".equals(loginstate)&&!isUserUri(uri)){
			    return;
		}else if("true".equals(loginstate)&&isUserUri(uri)){
			request.setUrl("/");
		}else if(!"true".equals(loginstate)&&isUserUri(uri)){
			return;
		}
//			request.setUrl("/login.html");
	}
	private boolean isUserUri(String uri){
		if ("/login.html".equals(uri)||"/regist.html".equals(uri)){
			return true;
		}else 
			return false;
	}
}
