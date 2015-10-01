package csctrails.elements;

import java.util.HashSet;

public class TagList {
	HashSet<String> tags;
	
	public TagList() {
		tags = new HashSet<String>();
	}

	public void add(String newTag){
		tags.add(newTag);
	}
	public void add(String[] newTags){
		for(int i=0; i<newTags.length; i++){
			add(newTags[i]);
		}
	}
	public void remove(String oldTag){
		if(tags.contains(oldTag)){
			tags.remove(oldTag);
		}
	}
	public boolean contains(String tag){
		return tags.contains(tag);
	}
	public boolean contains(String[] tagInput){
		for(int i=0; i < tagInput.length; i++){
			if(!contains(tagInput[i])) return false;
		}
		return true;
	}
}
