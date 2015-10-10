package grandtheftroster.utilities;

import java.util.HashMap;
import java.util.Set;

class ConfigProfile {
	
	private String type;
	private String subName;
	private String path;
	private HashMap<String, String> properties;
	
	ConfigProfile() {
		type = "";
		subName = "";
		path = "";
		properties = new HashMap<String, String>();
	}
	
	//Default Methods start here
	
	void add(String prop, String value){
		properties.put(prop.toUpperCase().trim(), value.trim());
	}
	void setName(String name){
		this.subName = name;
	}

	void setType(String type){
		this.type = type;
	}
	void setPath(String path){
		this.path = path;
	}
	
	String getProperty(String prop){
		return properties.get(prop);
	}
	String[] getPropertyList(){
		return (String[]) properties.keySet().toArray();
	}
	String getPath(){
		return path;
	}
	String getName(){
		return type + ":" + subName;
	}

	boolean isComplete(){
		return !(subName.equals("")) && !(type.equals(""));
	}
	
	public String toString(){
		String output = "";
		output += "TYPE: " + type + "\n";
		output += "NAME: " + subName + "\n";
		Set<String> keys = properties.keySet();
		for(String current:keys){
			output += current + ": " + properties.get(current) + "\n";
		}
		return output;
	}
	

}
