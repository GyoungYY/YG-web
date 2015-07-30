package yaweb.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import yaweb.Request;
import yaweb.Session;
import yaweb.User;

public class RequestImpl implements Request  {
	private String url = null;
	private String method = null;
	private User user = null;
	private String username = null;
	private InputStream input;
	private String post;
	private Session session;
	Map<String, String> map = new HashMap<String, String>();
	Map<String, String> map1 = new HashMap<String, String>();
	public RequestImpl(InputStream input) throws IOException{
		this.input = input;
	}
	
	public void proccess() throws IOException  {
		
		url = null;
		method = null;
		user = null;
		map.clear();
		int i = 0;
		byte c;
		
		byte[] b = new  byte [1024*24];
		int len = input.read(b);

		while (i<len){
			StringBuffer sb = new StringBuffer();
			String str = null;
			while (true){
				c = b[i];
				i++;
				sb.append((char)c);
				if (c=='\n'||i==len)	break;
			}
			str = sb.toString();		
			if (str.startsWith("GET")||str.startsWith("POST")){
				String[] st = str.split(" ");
				url = st[1];
				method = st[0];	
			}else if (str.contains(":")){
				String[] st = str.split(" ", 2);
				st[0] = st[0].replace(":", "");
				st[1] = st[1].replace("\r\n", "");
				map.put(st[0], st[1]);
			}else if ("POST".equals(method)){
				post=str;
				if(str.contains("password")){
				String st[] = str.split("&");
				String username = st[0].substring(st[0].indexOf("=")+1,st[0].length());
				String password = st[1].substring(st[1].indexOf("=")+1,st[1].length());
				user = new User(username, password);
				}
			}
		}
	}
	
	public String getUrl(){
		return url;
	}
	
	public Map<String,String> getHeaders(){
		return map;
	}
	
	public String getHeader(String str){		
		if (map.containsKey(str)){
			return map.get(str).toString();
		}else return "";
	}
	
	public String getMethod(){
		return method;
	}
	
	public String[] getCookies(){
		String str= map.get("Cookie");
		String[] splits = str.split(";");
		String[] cookies = new String[splits.length];
		for(int i = 0;i < splits.length;i++){
			cookies[i] = splits[i].trim();
		}
		return cookies;
	}
	
	public Map<String,String> getParameters(){
		if("GET".equals(method)){
			String str[]=url.split("&");
			int i=0,length=str.length;
			map1.put(str[0].substring(str[0].indexOf("?")+1,str[0].indexOf("=")),
					str[0].substring(str[0].indexOf("=")+1, str[0].length()));			
			while(i<length){
				map1.put(str[i].substring(0,str[i].indexOf("=")),str[i].substring(str[i].indexOf("=")+1,str[i].length()));				
				i++;
			}
		}
		if("POST".equals(method)){
			String str[]=post.split("&");
			int i=0,length=str.length;
			map1.put(str[0].substring(str[0].indexOf("?")+1,str[0].indexOf("=")),
					str[0].substring(str[0].indexOf("=")+1, str[0].length()));	
			i++;
			while(i<length){
				map1.put(str[i].substring(0,str[i].indexOf("=")),str[i].substring(str[i].indexOf("=")+1,str[i].length()));				
				i++;
			}
		}
		return map1;
	}
	
	public String getParameter(String key){
		return map1.get(key);
	}
	
	public void setSession(Session s){
		session=s;
	}
	
	public Session getSession(){
		return session;
	}
	
	public User getUser(){
		return user;
	}
	
	public String getUsername(){
		return username;
	}
	
	public void setUrl(String uri){
		url = uri;
	}
	
	public void setUsername(String username){
		this.username = username;
	}	 
}	
	
