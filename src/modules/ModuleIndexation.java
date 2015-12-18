package modules;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ModuleIndexation {

	private final static String url = "jdbc:mysql://localhost:3306/la_base";
	private final static String utilisateur = "user";
	private final static String motDePasse = "password";

	public static Connection connexion;



	
	//TODO ins�rer les nom des balises dans la base/ retirer les determinants et mots inutiles 
	public static void indexSimple(){


		int id =0;

		//
		File folder = new File("./documents/CORPUS/");

		String []filesName=folder.list();

		for (int i=0;i<filesName.length;i++){

			int number =Integer.parseInt( filesName[i].replaceAll("[^0-9]", ""));

			File input = new File("./documents/CORPUS/"+filesName[i]);
			System.out.println(input.getAbsolutePath());
			try {

				connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
				Document doc =Jsoup.parse(input, "UTF-8");

				Elements elements = doc.getAllElements();
				StringBuilder sb = new StringBuilder();
				for (Element e:elements){
					if (!e.tagName().equals("script") || e.tagName().equals("style")){
						sb.append(" "+e.text());
						System.out.println(e.nodeName());
					} 

				}

				String grosText = sb.toString();
				//System.out.println(grosText);
				Statement statement = connexion.createStatement();


				StringTokenizer str = new StringTokenizer(grosText,"[ .,;:\'\"&-]+" );
				while (str.hasMoreTokens()){
					String nextToken =str.nextToken();
					if(!nextToken.equals(" ")){
						int statut = statement.executeUpdate( "INSERT INTO dico (id, mot, doc,value) VALUES ("+id+",'"+nextToken+"','"+filesName[i]+"',value);" );
						id ++;
					}
				}

				//			String [] texts=grosText.split("[ .,;:\'\"\\s+]");
				//			System.out.println(texts[34]);

				/* Exécution d'une requête d'écriture */

				//			for(String mot : texts){
				//				 
				//			}

			} catch ( SQLException e ) {
				e.printStackTrace();
				//    Gérer les éventuelles erreurs ici 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				if ( connexion != null )
					try {
						connexion.close();
					} catch ( SQLException ignore ) {

					}
			}
		}
	}

	//TODO compl�ter cette fonction
	public void indexTronc7(){

	}




	public static void main(String[] args) {

		indexSimple();
	}
}
