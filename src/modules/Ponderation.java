package modules;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Ponderation {
	static String url = "jdbc:mysql://localhost:3306/la_base";
	static String utilisateur = "user";
	static String motDePasse = "password";
	static Connection connexion = null;
	
	
	
	public static float tf(String docName,String mot){

		int nombreDeMot = 0;
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			Statement statement = connexion.createStatement();
			String  query = "SELECT count(mot) as nombre FROM dico WHERE mot='"+mot+"' AND doc='"+docName+"';";
			ResultSet resultat = statement.executeQuery(query );
			resultat.beforeFirst();
			while ( resultat.next() ) {

				nombreDeMot = resultat.getInt( "nombre" );
				//				System.out.println("Il y a " + nombreDeMot + " fois  le mot "+mot+" dans le document "+document);

			}
			connexion.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nombreDeMot;


	}
	
	public static float tfLog(){
		return 0.0f;
	}
	
	public static float tfNorm(){
		//Nombre d'occurence du mot dans le document
		//Divisé par le nombre de mot dans le document
		
		
		return 0.0f;
	}
	
	public static float tfIdf(){
		return 0.0f;
	}
	
	public static float balises(){
		//Nombre d'occurences du mot en es comptant par balises
		//Divisé par le nombre 
		
		return 0.0f;
	}
	
	

}
