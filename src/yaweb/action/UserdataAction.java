package yaweb.action;

import yaweb.FileProcessor;
import yaweb.Processor;
import yaweb.Request;
import yaweb.Response;
import yaweb.User;
import yaweb.JDBC.JDBCMysql;


public class UserdataAction extends AbstractAction implements Action{

	public String getUri() {
		return "/userdata.html";
	}
	
	public void onGet(Request request, Response response) {
		
		try {
			Processor processor = new FileProcessor();
			JDBCMysql dc = new JDBCMysql();
			String username = request.getUsername();
			User user = dc.getUserData(username);
			String str = user.getUserHtml();
			processor.setFlag(false);
			processor.setBody(str.getBytes());
			processor.handle(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
