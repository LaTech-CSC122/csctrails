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
		tags.add(newTag.toUpperCase());
	}
	public void add(String[] newTags){
		for(int i=0; i<newTags.length; i++){
			add(newTags[i].toUpperCase());
		}
	}
	
	public void remove(String oldTag){
		if(tags.contains(oldTag.toUpperCase())){
			tags.remove(oldTag.toUpperCase());
		}
	}
	
	public boolean contains(String tag){
		String[] tags = tag.toUpperCase().split(",");
		return contains(tags);
	}
	public boolean contains(String[] tagInput){
		for(int i=0; i < tagInput.length; i++){
			if(!tags.contains(tagInput[i].trim().toUpperCase())) return false;
		}
		return true;
	}

	public String getId(){
		return id;
	}
}
