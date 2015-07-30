package yaweb.action;

import yaweb.FileProcessor;
import yaweb.Processor;
import yaweb.Request;
import yaweb.Response;
import yaweb.Session;
//import yaweb.SessionMgr;


public class LogoutAction extends AbstractAction implements Action{
//	private SessionMgr sMgr;
//	public LogoutAction(SessionMgr s){
//		sMgr=s;
//	}

	public String getUri() {
		return "/logout.html";
	}
	public void onGet(Request request, Response response){
		super.onGet(request, response);
	}
	
	public void onPost(Request request, Response response){
		
		try {
			Processor processor = new FileProcessor();
			processor.setFlag(false);	
			byte[] body = "success".getBytes();
			processor.setBody(body);
			processor.handle(request, response);
			Session session=request.getSession();
			session.put("loginstate","false");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}

