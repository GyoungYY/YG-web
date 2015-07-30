package yaweb;

import java.util.HashMap;

public class Session {
	public HashMap<String, String> session; 
	public Session(){
		session=new HashMap<String, String>();
	}
	public String get(String key){
		return session.get(key);
	}
	
	public void put(String key, String value){
		session.put(key, value);
	}
}
