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

import Sparql.SparqlClient;
import data.Document;
import data.Mot;

public class ModuleRecherche {
	private static final String pathRequetes ="./documents/requetes/";
	private static final String pathDocuments="./documents/CORPUS/";



	//Lis une requete et retourne un tableau de String contenant tous les mots de la requete
	private static List<String> lireRequete(String requetename){
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
			String [] tableau = everything.split(", | ");
			List<String> resultat = new ArrayList<String>();
			//Sans troncature
			//resultat=Arrays.asList(tableau);

			//Avec troncature
			for (String s:tableau){
				/*if (s.length()>6){
					resultat.add(s.substring(0,7));
					System.out.println(s.substring(0,7));
				} else {
					resultat.add(s);
					System.out.println(s);
				}*/
				resultat.add(s);
				
			}

			br.close();
			return resultat;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;

	}

	private static List<String> lireRequeteSparql(String requetename){
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
			String [] tableau = everything.split(", ");
			List<String> resultat = new ArrayList<String>();
			//Sans troncature
			//resultat=Arrays.asList(tableau);

			//Avec troncature
			for (String s:tableau){
				/*if (s.length()>6){
					resultat.add(s.substring(0,7));
					System.out.println(s.substring(0,7));
				} else {
					resultat.add(s);
					System.out.println(s);
				}*/
				resultat.add(s);
				
			}

			br.close();
			return resultat;
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


	//Renvoie la liste de documents tri�s pour une requete donn�e
	public static List<Document> recherche(List<String> requete){
		//Documents dans le dossier
		File folderDocuments = new File(pathDocuments);
		String [] fileNames = folderDocuments.list();
		List<Document> listDoc = new ArrayList<Document>();

		for (String documentName: fileNames){
			Document document = new Document(documentName);
			for (int i=0;i<requete.size();i++){
				Mot mot = new Mot();
				String motRequete = requete.get(i);
				mot.setMot(motRequete);
				mot.setScore(calculerScore(documentName,motRequete));
				document.mots.add(mot);
			}

			document.setPertinence(calculerPertinence(document,requete));
			listDoc.add(document);

		}		
		//On trie la liste
		Collections.sort(listDoc);

		return listDoc;

	}
	
	

	public static float calculerScore(String docName, String mot){
		//TODO choisir parmi les diff�rentes m�thodes de pond�ration
		//return Ponderation.tf(docName,mot);
		return Ponderation.balises(mot, docName);
		//return Ponderation.TF(mot, docName);
		//return Ponderation.Robertson(docName, mot);
	}

	public static float calculerPertinence(Document doc,List<String> requete){
		//TODO chosir parmi les m�thodes de pertinence
		//return Pertinence.cosinus(doc);
		//return Pertinence.scalaire(doc);
		//return Pertinence.jacard(doc, requete);
		return Pertinence.cosinus(doc, requete);
	}


	public static void main(String[] args) {


		//int indice = 3;
		float precisionMoyenne5=0f;
		float precisionMoyenne10=0f;
		float precisionMoyenne25=0f;
		for (int indice=1; indice<=9; indice++){
			List<String> requeteWords=lireRequete("Q"+indice);
			List<String> requeteWordsSparql=lireRequeteSparql("Q"+indice);
			ArrayList<String> synonymes = new ArrayList<String>();
			System.out.println("La requete est :");
			List<String> resultat = new ArrayList<String>();

/*			for (String s:requeteWordsSparql){
				//System.out.println(s);
				for (String sparql : SparqlClient.findSynonyme(s)){
					String [] tableau = sparql.split(" ");
					for (String s2:tableau){
						if (s2.equals("a")||s2.equals("de")||s2.equals("pour")||s2.equals("est")||s2.contains("/")){
							System.out.println("suppression d'un terme");
						}else{
							resultat.add(sparql);
						}						}

				}
				
			}

			for (String s : resultat){
				int i = 1; 
				for(String s2 : requeteWords){
					if(s.length()>6){
						s = s.substring(0,7);
					}
					if(s2.length()>6){
						s2 = s2.substring(0,7);
					}
					System.out.println("comparaison " + s + "    "+ s2);
					if (s.equals(s2)){
						i=0;
					}
				}
				if(i==1){
					requeteWords.add(s);
				}
			}*/
			
			ArrayList<String> requeteTronc = new ArrayList<String>();
			for (String s:requeteWords){
				if (s.length()>6){
					requeteTronc.add(s.substring(0,7));
					System.out.println(s.substring(0,7));
				} else {
					requeteTronc.add(s);
					System.out.println(s);
				}				
			}

			List <Document> listDoc = recherche(requeteWords);
			List <Document> vraieListe=ModuleEvaluation.genererListe("qrelQ"+indice+".txt");
			float precision=ModuleEvaluation.evaluer(listDoc, vraieListe, 5);
			System.out.println("La précision a 5 pour la requête : "+ indice+ " est de : " +precision);
			float precision_10=ModuleEvaluation.evaluer(listDoc, vraieListe, 10);
			System.out.println("La précision a 10 pour la requête : "+ indice+ " est de : " +precision_10);
			float precision_25=ModuleEvaluation.evaluer(listDoc, vraieListe, 25);
			System.out.println("La précision a 25 pour la requête : "+ indice+ " est de : " +precision_25);

			precisionMoyenne5 = precisionMoyenne5 + precision;
			precisionMoyenne10 = precisionMoyenne10 + precision_10;
			precisionMoyenne25 = precisionMoyenne25 +precision_25;
			

		}
		
		precisionMoyenne5 = precisionMoyenne5/9;
		precisionMoyenne10 = precisionMoyenne10/9;
		precisionMoyenne25 = precisionMoyenne25/9;
		
		System.out.println("La précision moyenne a 5 est de : "+precisionMoyenne5);
		System.out.println("La précision moyenne a 10 est de : "+precisionMoyenne10);		
		System.out.println("La précision moyenne a 25 est de : "+precisionMoyenne25);
		


	}
}

