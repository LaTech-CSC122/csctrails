package csctrails.elements;

import java.util.HashSet;

public class TagList {
	private static int nextId = 1;
	private static int idLength = 5;
	private HashSet<String> tags;
	private String id;
	
	public TagList() {
		tags = new HashSet<String>();
		//set ID
		id = "" + nextId;
		nextId++;
		while(id.length()<idLength){
			id = "0" + id;
		}
		
	}

	public void add(String newTag){
		add(newTag.split(","));
	}
	
	public void add(String[] newTags){
		for(int i=0; i<newTags.length; i++){
			tags.add(newTags[i].toUpperCase().trim());
		}
	}
	
	public void remove(String oldTag){
		if(tags.contains(oldTag.toUpperCase())){
			tags.remove(oldTag.toUpperCase());
		}
	}
	
	public boolean contains(String inputTag){
		return contains(inputTag.split(","));
	}
	public boolean contains(String[] tagList){
		for(int i=0; i<tagList.length; i++){
			if(!tags.contains(tagList[i].toUpperCase().trim())){ return false; }
		}
		return true;
	}

	public String getId(){
		return id;
	}
	
	public String toString(){
		return id + ": " + tags.toString();
	}

	public String[] getTags() {
		String[] output = new String[tags.size()];
		Object[] objects = tags.toArray();
		for(int i=0; i<output.length; i++){
			output[i] = (String) objects[i];
		}
		return output;
	}
}
