package yaweb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;
//import java.util.Random;
import java.text.DateFormat;

public class FileProcessor implements Processor{
	
	private String pathname = null;
	private byte[] body = null;
	private Map<Integer, String> error = new HashMap<Integer, String>();
	private String root = "D:/WEB";
	private boolean flag = true;
	
	public FileProcessor(){
		setError();
	}
	public boolean match(Request request) {
		try {
			request.proccess();
			return "close".equals(request.getHeader("Connection"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void handle(Request request, Response response) throws Exception {
		
		pathname = root + request.getUrl();
		String header[] = {"Data: ","Server: ","Content-Length: ",
				"Keep-Alive: ","Connection: ","Content-Type: "};	
		response.setStatus(getStatus());
		for (int i=0; i<header.length; i++){
			response.addHeader(header[i], parse(i));
		}
		response.addBody(body);
		response.send();
	}
	
	private String parse(int i){
		
		switch (i){
			case 0: {	
				Date date = new Date( );
				DateFormat df = DateFormat.getDateTimeInstance();
				return df.format(date);
			}
			case 1: {
				return System.getProperty("os.name")+System.getProperty("os.version");
			}
			case 2: {
				return String.valueOf(body.length);
			}		
			case 3: {
				return "timeout=15, max=100";
			}
			case 4: {
				return "keep-alive";
			}
			case 5: {
				return getType();
			}
		}
		return "";
	}
	public void setFlag(boolean flag){
		this.flag = flag;
	}
	
	public void setBody(byte[] body){
		this.body = body;
	}
	
	private void proccess() throws Exception{
		
		if (pathname.endsWith("/")){
			proccessDir();
		}else proccessFile();
	}
	
	private void proccessDir(){
		
		File f = new File(pathname);
		String[] childFile = f.list();
		StringBuffer array = new StringBuffer("");
		array.append("<html><body><h1>"+pathname+"'s directory</h1>");
		for (int i=0; i<childFile.length; i++){
			File tempFile = new File(pathname+childFile[i]);
			if (tempFile.isDirectory()) childFile[i] += "/";
			array.append("<b><a href='"+childFile[i]+"'>");
			array.append(childFile[i]);
			array.append("</a></b><br/>");
		}
		array.append("</body></html>");
		body = array.toString().getBytes();
	}
	
	private void proccessFile() throws Exception{
		
		File f = new File (pathname);
		InputStream is = new FileInputStream(f);
		int len = (int)f.length();
		body = new byte[len];
		int offset = 0;
		while (offset<len){
			offset += is.read(body,offset,len-offset);
		}
		is.close();
	}
	
	private void proccessError(int status) throws UnknownHostException{
		
		String str = "<!DOCTYPE HTML PUBLIC \"-//IETF//DTD HTML 2.0//EN\">\r\n"+
				"<html><head>\r\n"+
				"<title>"+status+error.get(status)+"</title>\r\n"+
				"</head><body>"+"<h1>"+"404 File Not Found!"+"</h1>"+
				"</body></html>";
		body = str.getBytes();
	}
	
	private int getStatus() throws Exception{
				
		if (!flag) return 200;
		File file = new File (pathname);
		if (!file.exists()){
			proccessError(404);
			return 404;
		}else {
			proccess();
			return 200;
		}
	}
	
	private String getType(){
		if (pathname.endsWith(".png")) {
			return "image/png";
		}
		else if (pathname.endsWith(".pdf")) {
			return "application/pdf";
		}
		else if (pathname.endsWith(".mp3")) {
			return "audio/mpeg";
		}else if(pathname.endsWith(".gif")){
			return "image/gif";
		}
		else {
			return "text/html";
		}
	}
	
	private void setError(){		
		error.put(404, "Not Found");
	}
}
