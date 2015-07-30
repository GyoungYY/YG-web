package yaweb.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import yaweb.Response;

public class ResponseImpl implements Response{
	
	private PrintStream os;

	Map<String, String> headers = new HashMap<String, String>(100);
	String firstline;
	byte[] body;
	public ResponseImpl(OutputStream os){		
		this.os = new PrintStream(os);
	}

	public void setStatus(int status) {	
		switch (status) {
			case STATUS_200:
				firstline="HTTP/1.1 200 OK!";
				break;
			case STATUS_404:
				firstline="HTTP/1.1 404 File Not Found!";
				break;
			case STATUS_503:
				firstline="HTTP/1.1 503 server cannot use!";
				break;
		}
	}	

	public void addHeader(String key, String value) {	
		headers.put(key, value);
	}

	public void addBody(byte[] bdy) {	
		body=bdy;
	}

	public void send() {
		os.println(firstline);
		for(String key:headers.keySet()){
			os.println(key+headers.get(key));
		}
		os.println();
		try {
			os.write(body);
			os.println();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		os.flush();		
	}
}
