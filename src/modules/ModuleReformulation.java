package modules;

import java.util.ArrayList;
import java.util.List;




// id�e de base partir d'une requete de d�part et l'am�liorer
//pour chaque mot cl� : match exact pour trouver tous les synonymes

public class ModuleReformulation {
	
	public static List<String> enrichir(List <String> mots){
		List<String> requeteEnrichie= new ArrayList<>();
		
		for (String mot:mots){
			List<String> nouveauMots =getSynonymes(mot);
			//nouveauMot.addAll(getInstances(mot));
			for (String syno : nouveauMots){
				requeteEnrichie.add(syno);
			}
		}
		
		return requeteEnrichie;
		
		

	}
	
	private static List<String> getSynonymes(String mot){
		return new ArrayList(); 
	}
	
	private static List<String> getInstances(String mot){
		return new ArrayList();
	}

}
