package grandtheftroster.utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Configuration {
	private HashMap<String, ConfigProfile> configuration;
	
	
	public Configuration() {
		configuration = new HashMap<String, ConfigProfile>();
	}
	
	public void loadConfiguration(String dir){
		File file = new File(dir);
		if(file.isDirectory()){
			addProfile(ConfigProfileParser.parseDir(file));
		}
		else if(file.isFile()){
			addProfile(ConfigProfileParser.parseFile(file));
		}
	}
	
	public String getProperty(String id){
		if((id.indexOf(":") != id.lastIndexOf(":")) || !id.contains(":")){ return null; }
		if((id.indexOf("@") != id.lastIndexOf("@")) || !id.contains("@")){ return null; }
		// change "BODY_WIDTH@MODEL:KEY" to "MODEL", "KEY" and "BODY_WIDTH"
		String profile = id.substring(id.indexOf("@")+1);
		String property = id.substring(0, id.indexOf("@"));
		if(configuration.containsKey(profile)){
			return configuration.get(profile).getProperty(property);
		}
		else{ return null; }
	}
	
	public boolean hasProperty(String id){
		return getProperty(id) != null;
	}

	private void addProfile(ArrayList<ConfigProfile> profileList){
		for(ConfigProfile profile:profileList){
			addProfile(profile);
		}
	}
	private void addProfile(ConfigProfile profile){
		if(profile.isComplete() && !configuration.containsKey(profile.getName())){
			configuration.put(profile.getName(), profile);
		}
	}
	
	public Set<String> getKeySet(){
		return configuration.keySet();
	}
	
	
	
}
