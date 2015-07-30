package yaweb;

//import java.util.HashMap;
//import java.util.Map;

public class CookieImpl implements Cookie{
	private String key;
	private String value;
	public CookieImpl(String cookie){
		String[] temp={};
    	temp=cookie.split("=");
    	if(temp.length==2){
    		this.key=temp[0];
    		this.value=temp[1]; 	
    	}
	}
	public String get(String key){
		if(key.equals(this.key))
			return value;
		else
			return null;
	}
	
	public String getKey(){
		return key;
	}
}
/*
public class CookieImpl implements Cookie {
	String cookie;

	String name;

	String value;

	String path;

	String life;

	Map<String, String> map1 = new HashMap<String, String>();

	public CookieImpl(String s) {
		if (s != null&&s.length()>0) {
			String str[] = s.split(";");
			int i = 0, length = str.length;
			while (i < length) {
				map1.put(str[i].substring(0, str[i].indexOf("=")),
						str[i].substring(str[i].indexOf("=") + 1, str[i].length()));
				i++;
			}
		}
	}

	public String getName() {
		return map1.get("NAME");
	}

	public String getDomain() {
		return map1.get("Domain");
	}

	public String getPath() {
		return map1.get("Path");
	}

	public String getLife() {
		return map1.get("Expires");
	}
}
*/