package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ModuleRecherche {
	private static final String path ="./documents/requetes/";

	private static String [] lireRequete(String requetename){
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(path+requetename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			String everything = sb.toString();
			System.out.println(everything);
			br.close();
			
			return everything.split(", ");
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;


	}

	public static void CountWord (String mot) {
		String url = "jdbc:mysql://localhost:3306/la_base";
		String utilisateur = "user";
		String motDePasse = "password";
		Connection connexion = null;
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Statement statement = connexion.createStatement();
			String  query = "SELECT count(mot) as nombre, num FROM dico WHERE mot='"+mot+"' GROUP BY num;";
			ResultSet resultat = statement.executeQuery(query );
			resultat.beforeFirst();
			while ( resultat.next() ) {
				
				int nombreDeMot = resultat.getInt( "nombre" );
				int idDoc = resultat.getInt( "num" );
				System.out.println("Il y a " + nombreDeMot + " fois  le mot "+mot+" dans le document "+idDoc);

			}
			connexion.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public static void main(String[] args) {
		String [] requeteWords=lireRequete("Q1");
		for (int i=0;i<requeteWords.length;i++){
			String mot = requeteWords[i];
			System.out.println(mot);
			CountWord(mot);

		}

	}
}

