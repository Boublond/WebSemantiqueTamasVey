package modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.Document;
import data.Mot;

public class ModuleRecherche {
	private static final String pathRequetes ="./documents/requetes/";
	private static final String pathDocuments="./documents/CORPUS/";
	
	

	//Lis une requete et retourne un tableau de String contenant tous les mots de la requete
	private static String [] lireRequete(String requetename){
		BufferedReader br;

		try {
			br = new BufferedReader(new FileReader(pathRequetes+requetename));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			String everything = sb.toString();
			br.close();

			return everything.split(", ");
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;

	}

	public static int CountWord (String mot, String document) {
		String url = "jdbc:mysql://localhost:3306/la_base";
		String utilisateur = "user";
		String motDePasse = "password";
		Connection connexion = null;
		int nombreDeMot = 0;
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			Statement statement = connexion.createStatement();
			String  query = "SELECT count(mot) as nombre FROM dico WHERE mot='"+mot+"' AND doc='"+document+"';";
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

	public static float isPertinent(String file, String [] requeteWords){
		float isPertinent = 1.0f; 
		ArrayList<Integer> nombreOcurenceParMot = new ArrayList<Integer>() ; 
		for (int i=0;i<requeteWords.length;i++){
			String mot = requeteWords[i];
			//			System.out.println(mot);
			nombreOcurenceParMot.add(CountWord(mot, file));
		}
		for (int nb : nombreOcurenceParMot){
			if (nb ==0){
				isPertinent = 0.0f;
				break;
			}else if (nb<5){
				isPertinent = 0.5f;
			}
		}
		return isPertinent; 
	}
	
	
	//Renvoie la liste de documents triés pour une requete donnée
	public static List<Document> recherche(String [] requete){
		//Documents dans le dossier
		File folderDocuments = new File(pathDocuments);
		String [] fileNames = folderDocuments.list();
		List<Document> listDoc = new ArrayList<Document>();
		
		for (String documentName: fileNames){
			Document document = new Document(documentName,requete.length);
			for (int i=0;i<requete.length;i++){
				document.mots[i].setMot(requete[i]);
				document.mots[i].setScore(calculerScore(documentName,requete[i]));
			}
			
			document.setPertinence(calculerPertinence());
			
			
		}
		
		
		//On tri la liste
		Collections.sort(listDoc);
		
		return listDoc;
		
		
		
		
	}
	
	public static float calculerScore(String docName, String mot){
		return Ponderation.tf(docName,mot);
	}
	
	public static float calculerPertinence(){
		return 0.0f;
	}
	
	
	
	
	
	
	
	

	
	

	public static void main(String[] args) {
		String [] requeteWords=lireRequete("Q1");
		File folder = new File("./documents/CORPUS/");
		String []filesName=folder.list();
		List<Document> listDoc = new ArrayList<Document>();
		for (int i=0;i<filesName.length;i++){
			float pertinent = isPertinent(filesName[i], requeteWords);
			//listDoc.add(new Document (pertinent, filesName[i]));
		}

		Collections.sort(listDoc);

		for(Document d:listDoc){
			System.out.println(d);
		}
	}
}

