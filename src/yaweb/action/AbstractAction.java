package yaweb.action;

import yaweb.FileProcessor;
import yaweb.Request;
import yaweb.Response;


public abstract class AbstractAction implements Action{

	public void onGet(Request request, Response response) {
		try {
			FileProcessor handler =new FileProcessor();
			handler.setFlag(true);
			handler.handle(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void onPost(Request request, Response response) {
		
	}
	
	public boolean isLogout() {
		return false;
	}
	
	public boolean isLogin() {
		return false;
	}
}
