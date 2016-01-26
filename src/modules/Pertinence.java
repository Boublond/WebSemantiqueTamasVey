package modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import data.Document;
import data.Mot;

public class Pertinence {
	
	static String url = "jdbc:mysql://localhost:3306/la_base";
	static String utilisateur = "user";
	static String motDePasse = "password";
	static Connection connexion = null;
	

	
	
	private static double calculerNorme(Document doc) throws SQLException{
		double somme = 0;
		connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		Statement statement = connexion.createStatement();
		String query = "SELECT mot,count(mot) as nb FROM la_base.dico "
				+ "WHERE doc='"+doc.getName()+"'"
				+ "GROUP BY mot;";
		
		
		ResultSet resultat = statement.executeQuery(query );
		resultat.beforeFirst();
		while ( resultat.next() ) {
			float xi =resultat.getInt("nb");
			somme+=xi*xi;
			
		}
		connexion.close();
		return Math.sqrt(somme);
		
	}
	
	
	
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
		//System.out.println(doc.getName()+"  "+resultat);
		return resultat;
	}
	
	// 2 * ps / norme(X) + norme(Y)
	public static float dice(Document doc,List<String> requete){
		try {
			float normeX = (float)calculerNorme(doc);
			float normeY=(float)Math.sqrt(requete.size());
			
			
			return 2*scalaire(doc) / (normeX+normeY);
			
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return -1;


	}
//	
	
	// ps /sqrt(norme(x))*sqrt(norme(Y))
	public static float cosinus(Document doc,List<String> requete){
			try {
				float normeX = (float)calculerNorme(doc);
				float normeY=(float)Math.sqrt(requete.size());
				float produit =(float) (Math.sqrt(normeX)*Math.sqrt(normeY));
				
				return scalaire(doc) / produit;
				
			} catch (Exception e){
				e.printStackTrace();
			}
			
			return -1;


		
		
	}
//	//ps/norme(X) + norme(Y) -PS
	public static float jacard(Document doc,List<String> requete){
		try {
			float normeX = (float)calculerNorme(doc);
			float normeY=(float)Math.sqrt(requete.size());
			return scalaire(doc)/(normeX+normeY-scalaire(doc));
			
		}catch (Exception e){
			e.printStackTrace();
		}
		return -1;
	}

}
