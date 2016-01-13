package modules;

import data.Document;
import data.Mot;

public class Pertinence {
	
	
	public static float scalaire(Document doc){
		float resultat=0f;
		
		for(Mot mot : doc.mots){
			resultat+=mot.getScore();
		}
		
		return resultat;
	}
	
	public static float cosinus(Document doc){
		return 0f;
	}

}
