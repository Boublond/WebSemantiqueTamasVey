package data;

import java.util.ArrayList;
import java.util.List;

public class Document implements Comparable<Document> {
	
	private float pertinence;
	private String name;
	
	public List <Mot> mots;
	
	
	
	
	@Override
	public int compareTo(Document doc2) {
		if (this.pertinence<doc2.pertinence){
			return 1;
		} else if (this.pertinence==doc2.pertinence) {
			return 0;
		} else {
			return -1;
		}
		
	}





	public Document(String name){
	
		
		this.name=name;
		this.mots = new ArrayList<Mot>();
	}
	
	public Document(float pertinence,String name){
		this.pertinence=pertinence;
		this.name=name;
	}





	public float getPertinence() {
		return pertinence;
	}





	public void setPertinence(float pertinence) {
		this.pertinence = pertinence;
	}





	public String getName() {
		return name;
	}





	public void setName(String name) {
		this.name = name;
	}





	@Override
	public String toString() {
		return "Document [pertinence=" + pertinence + ", name=" + name + "]";
	}
	
	
	

}
