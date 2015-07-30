package yaweb.action;

import java.sql.ResultSet;

import yaweb.FileProcessor;
import yaweb.Processor;
import yaweb.Request;
import yaweb.Response;
import yaweb.JDBC.JDBCMysql;

public class UserlistAction extends AbstractAction implements Action{

	public String getUri() {
		return "/userlist.html";
	}

	public void onGet(Request request, Response response){
		try {
			Processor processor = new FileProcessor();
			JDBCMysql dc = new JDBCMysql();
			ResultSet rs = dc.getUserSet();
			
			StringBuffer array = new StringBuffer("");
			array.append("<html><body><h1>UserList</h1>");

			while (rs.next()){
				array.append(rs.getString("username")+": "+rs.getString("password"));
				array.append("<a href=\"/delete.html?" +rs.getString("username")+
						"\">Delete</a></td></tr><br/>");
			}
			array.append("</body></html>");
			processor.setFlag(false);
			processor.setBody(array.toString().getBytes());
			processor.handle(request, response);
		}
		catch (Exception e) {
			e.printStackTrace();
		}	
	}
}
