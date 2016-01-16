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
				//				System.out.println("Il y a " + nombreDeMot + " fois  le mot "+mot+" dans le document "+document);

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

		if (tf ==0){
			return tf;
		} else {
			return tf/(1+(float)Math.log(tf));
		}

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


	public static float balises(){
		//Nombre d'occurences du mot en es comptant par balises
		//Divisé par le nombre 

		return 0.0f;
	}



}
