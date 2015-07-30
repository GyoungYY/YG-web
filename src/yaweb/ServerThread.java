package yaweb;

import java.net.Socket;
import java.util.Map;

import yaweb.action.Action;
import yaweb.action.AllAction;
import yaweb.impl.RequestImpl;
import yaweb.impl.ResponseImpl;

public class ServerThread extends Thread{
	private Socket socket;
	private Map<String, Action> actions;
	private Filter filter;
	private SessionMgr sMgr;
	public ServerThread(Socket socket, Map<String, Action> actions, Filter filter,SessionMgr sMgr){
		this.socket = socket;
		this.actions = actions;
		this.filter = filter;
		this.sMgr=sMgr;
	}
	
	public void run(){
		try {
			execute(socket);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void execute(Socket socket) throws Exception{
		Request request = new RequestImpl(socket.getInputStream());
		Response response = new ResponseImpl(socket.getOutputStream());
		Processor processor = new FileProcessor();
		Action action = null;
		while(!processor.match(request)){
			String uri = request.getUrl();
			if (uri==null) continue;
			//将request中的cookie中的sessionid获取，处理会话，最终将session设置到request中
			sMgr.process(request,response);
			//处理request的过滤
			filter.doFilter(request);
			//查找合适的action处理界面
			uri = request.getUrl();
			if (actions.containsKey(uri)){
				action = actions.get(uri);
			}else {
				if (uri.startsWith("/delete.html")){
					action = actions.get("/delete.html");
				}else action = new AllAction();
			}
			if("GET".equals(request.getMethod())){
				action.onGet(request, response);
			}else if ("POST".equals(request.getMethod())){
				action.onPost(request, response);
			}
		}
		socket.close();		
	}
}
