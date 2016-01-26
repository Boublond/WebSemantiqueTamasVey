package modules;

import data.Document;
import data.Mot;

public class Pertinence {
//	- distance de produit scalaire
//	- distance de cosinus
//	- distance de dice
//	- distance de jaccard

	//somme des scores
	public static float scalaire(Document doc){
		float resultat=0f;
		
		for(Mot mot : doc.mots){
			resultat+=mot.getScore();
		}
		System.out.println(doc.getName()+"  "+resultat);
		return resultat;
	}
	
//	public static float dice(Document doc){
//		// 2 * ps / norme(X) + norme(Y)
//	}
//	
//	
//	public static float cosinus(Document doc){
//		// ps /sqrt(norme(x))*sqrt(norme(Y))
//	}
//	
//	public static float jacard(Document doc){
//		//ps/norme(X) + norme(Y) -PS
//	}

}
