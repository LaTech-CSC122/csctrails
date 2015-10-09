package grandtheftroster.configuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

abstract class ConfigProfileParser {
	
	public static ArrayList<ConfigProfile> parseDir(File folder){
		ArrayList<ConfigProfile> profiles = new ArrayList<ConfigProfile>();
		File[] files = folder.listFiles();
		
		for(File file:files){
			if(file.toString().endsWith(".config")){
				ConfigProfile cfg = parseFile(file);
				if(cfg != null){ profiles.add(cfg); }
			}
		}
		return profiles;
	}
	
	public static ConfigProfile parseFile(File file){
		Scanner scanner;
		try{
			scanner = new Scanner(file);
		}catch(FileNotFoundException e){
			System.out.println(e.toString());
			System.out.println("Could not locate file: " + file.toPath());
			return null;
		}
		
		ConfigProfile config = new ConfigProfile();
		
		while(scanner.hasNextLine()){
			String currentLine = scanner.nextLine();
			if(currentLine.startsWith("#"));
			else if(currentLine.startsWith("CONFIG_TYPE:")){
				config.setType(currentLine.substring(currentLine.indexOf(":")+1));
			}
			else if(currentLine.startsWith("CONFIG_NAME:")){
				config.setName(currentLine.substring(currentLine.indexOf(":")+1));
			}
			else if(currentLine.contains("=")){
				String name = currentLine.substring(0, currentLine.indexOf("=")).trim();
				String entry = currentLine.substring(currentLine.indexOf("=")+1).trim();
				config.add(name, entry);
			}
			
		}
		scanner.close();
		return config;
	}


}
