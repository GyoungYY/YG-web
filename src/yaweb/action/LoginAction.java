package yaweb.action;

import yaweb.FileProcessor;
import yaweb.Processor;
import yaweb.Request;
import yaweb.Response;
import yaweb.Session;
//import yaweb.SessionMgr;
//import yaweb.SessionMgr;
//import yaweb.SessionMgr;
import yaweb.JDBC.JDBCMysql;

public class LoginAction extends AbstractAction implements Action {
//	private SessionMgr sMgr;
//	public LoginAction(SessionMgr s){
//		sMgr=s;
//	}

	public String getUri() {
		return "/login.html";
	}

	public void onPost(Request request, Response response) {	
		Processor processor = new FileProcessor();
		JDBCMysql dc = null;
		byte[] body = null;
		try {
			dc = new JDBCMysql();
			if (dc.isUser(request.getUser())) {
				String str = "success";
				body = str.getBytes();
				Session session=request.getSession();
				session.put("loginstate","true");
			}else {
				String str = "failed";
				body = str.getBytes();
			} 
			processor.setFlag(false);
			processor.setBody(body);
			processor.handle(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onGet(Request request, Response response) {
		super.onGet(request, response);
	}
}
