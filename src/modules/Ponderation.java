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

		int tf = 0;
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

				tf = resultat.getInt( "nombre" );
				//System.out.println("Il y a " + tf + " fois  le mot "+mot+" dans le document "+docName);

			}
			connexion.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tf;

	}


	public static float TF(String mot,String docName){

		int tf = 0;
		/*try {
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

				tf = resultat.getInt( "nombre" );
				//				System.out.println("Il y a " + nombreDeMot + " fois  le mot "+mot+" dans le document "+document);

			}
			connexion.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		tf = balises(mot, docName);

		if (tf ==0){
			return tf;
		} else {
			return tf/(1+(float)Math.log(tf));
		}

	}
	
	public static float tfIDF(String docName,String mot){
		float tf = tf(docName, mot);
		int nbDoc = 138;
		int nb =0;
		
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {

			Statement statement = connexion.createStatement();
			String  query = "SELECT  distinct doc as nb FROM la_base.dico WHERE mot='"+mot+"';";
			ResultSet resultat = statement.executeQuery(query );
			resultat.beforeFirst();
			
			while ( resultat.next() ) {
				nb++;
				

			}
			connexion.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tf * (float)Math.log(nbDoc/nb);
		
		
		
		
	}



	public static float Robertson(String docName,String mot){
		int tf = 0;
		int K =0;
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

				tf = resultat.getInt( "nombre" );
				//				System.out.println("Il y a " + nombreDeMot + " fois  le mot "+mot+" dans le document "+document);

			}
			connexion.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		




		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {

			Statement statement = connexion.createStatement();
			String  query = "SELECT count(mot) as nombre FROM dico WHERE doc='"+docName+"';";
			ResultSet resultat = statement.executeQuery(query );
			resultat.beforeFirst();
			while ( resultat.next() ) {

				K = resultat.getInt( "nombre" );

			}
			connexion.close();


		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if (K!=0 && tf !=0){
			return tf/(K+tf);
		} else {
			return 0;
		}


	}


	public static int balises(String mot, String docName) {
		//Nombre d'occurences du mot en es comptant par balises
		//Divis� par le nombre 
		int count = 0; 
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {
			Statement statement;
			statement = connexion.createStatement();
			String  query = "SELECT mot, balise FROM dico WHERE mot='"+mot+"' AND doc='"+docName+"';";
			ResultSet rs = statement.executeQuery(query);
			while (rs.next()){
				String balise = rs.getString("balise");
				//System.out.println(balise);
				if (balise.equals("meta")){
					count = count +10;
				}else if (balise.equals("title")){
					
					count = count + 20; 
				}
				else if (balise.equals("h1")){
					count = count +10;
				}else if (balise.equals("h2")){
					count = count + 5; 
				}else if (balise.equals("b_or_strong")){
					count = count + 2; 
				}else {
					count++;
				}
				
			}
			
		} catch (Exception e){
			e.printStackTrace();
		}
		try {
			connexion.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		return count;
	}



}
