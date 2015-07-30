package yaweb.action;

import yaweb.FileProcessor;
import yaweb.Request;
import yaweb.Response;
import yaweb.Session;
//import yaweb.SessionMgr;
import yaweb.JDBC.JDBCMysql;


public class RegistAction extends AbstractAction implements Action{
//	private SessionMgr sMgr;
//	public RegistAction(SessionMgr s){
//		sMgr=s;
//	}
	public String getUri() {
		return "/regist.html";
	}

	public void onPost(Request request, Response response) {
		//注册的提交
		FileProcessor handler = new FileProcessor();
		JDBCMysql dc = null;
		byte[] body = null;
		try{
			dc = new JDBCMysql();
			if (dc.addUser(request.getUser())){
				String str = "success";
				body = str.getBytes();
				Session session=request.getSession();
				session.put("loginstate","true");
			}else {
				String str = "failed";
				body = str.getBytes();
			}
			handler.setFlag(false);
			handler.setBody(body);
			handler.handle(request, response);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
