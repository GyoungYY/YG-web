package yaweb;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import yaweb.action.Action;
import yaweb.action.DeleteAction;
import yaweb.action.LoginAction;
import yaweb.action.LogoutAction;
import yaweb.action.RegistAction;
import yaweb.action.UserdataAction;
import yaweb.action.UserlistAction;

public class Server {
	public static void main(String[] args) throws IOException{
		SessionMgr sm=new SessionMgr();
		Action login = new LoginAction();
		Action regist = new RegistAction();
		Action logout = new LogoutAction();
		Action userlist = new UserlistAction();
		Action delete = new DeleteAction();
		Action userdata = new UserdataAction();
		Map<String, Action> actions = new HashMap<String,Action>(6);
		actions.put(login.getUri(), login);
		actions.put(regist.getUri(), regist);
		actions.put(logout.getUri(), logout);
		actions.put(userlist.getUri(), userlist);
		actions.put(delete.getUri(), delete);
		actions.put(userdata.getUri(), userdata);
		ServerSocket server = new ServerSocket(8080);
		Filter filter = new Filter();
		ExecutorService pool = Executors.newCachedThreadPool();
		boolean f = true;
		while (f){
			Socket socket = server.accept();
			pool.execute(new ServerThread(socket, actions, filter,sm));
		}
		server.close();
	}
}
