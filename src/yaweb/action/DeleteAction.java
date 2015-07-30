package yaweb.action;

import yaweb.FileProcessor;
import yaweb.Processor;
import yaweb.Request;
import yaweb.Response;
import yaweb.JDBC.JDBCMysql;

public class DeleteAction extends AbstractAction implements Action{

	public String getUri() {
		return "/delete.html";
	}
	
	public void onGet(Request request, Response response){
		
		try {
			Processor processor = new FileProcessor();
			String uri = request.getUrl();
			String username = uri.substring(uri.indexOf("?")+1, uri.length());
			JDBCMysql dc = new JDBCMysql();
			dc.deleteUser(username);
			request.setUrl("/");
			processor.setFlag(true);
			processor.handle(request, response);
		}catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
