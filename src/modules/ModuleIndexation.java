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



	public static Connection connexion;

	public static void connexionToDB(){
		String url = "jdbc:mysql://localhost:3306/la_base";
		String utilisateur = "user";
		String motDePasse = "password";
		try {
			connexion = DriverManager.getConnection( url, utilisateur, motDePasse );
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void closeConnexionToDB(){
		if ( connexion != null )
			try {
				connexion.close();
			} catch ( SQLException ignore ) {

			}
	}
	
	//TODO ins�rer les nom des balises dans la base/ retirer les determinants et mots inutiles 
	public static void indexSimple(boolean withTronc){


		int id =0;
		connexionToDB();
		//
		File folder = new File("./documents/CORPUS/");
		
		String []filesName=folder.list();
		Statement statement;
		try {
			statement = connexion.createStatement();


		for (int i=0;i<filesName.length;i++){

			int number =Integer.parseInt( filesName[i].replaceAll("[^0-9]", ""));

			File input = new File("./documents/CORPUS/"+filesName[i]);
			System.out.println(input.getAbsolutePath());


				Document doc =Jsoup.parse(input, "UTF-8");

				Elements elements = doc.getAllElements();
				StringBuilder sb = new StringBuilder();
				for (Element e:elements){
					if (!e.tagName().equals("script") || e.tagName().equals("style")){
						sb.append(e.text()+"\n");
						//System.out.println(e.nodeName());
						String text = sb.toString();
						text= text.toLowerCase();
						text = text.replaceAll("[\n\t\b\r]", " ");
						text = text.replaceAll("�|�|[0-9]| alors | au | aucuns | aussi | autre | avant | avec | avoir | bon | car | ce | cela | ces | ceux | chaque | ci | comme | comment | dans | de | des | du | dedans | dehors | depuis | devrait | doit | donc | dos | d�but | elle | elles | en | encore | essai | est | et | eu | fait | faites | fois | font | hors | ici | il | ils | je | juste | la | le|les | leur | l� | ma | maintenant | mais | mes | mine | moins | mon | mot | m�me | ne | ni | nomm�s | notre | nos | nous | ok | ou | o� | par | parce | pas | peut | peu | plupart | pour | pourquoi | quand | que | quel | quelle | quelles | quels | qui | sa | sans | ses | seulement | si | sien | son | sont | sous | soyez | sujet | sur | ta | tandis | tellement | tels | tes | ton | tous | tout | trop | tr�s | tu | voient | vont | votre | vous | vu | �a | �taient | �tat | �tions | �t� | �tre | a | � | l'| d'| j'| m'| n'|[ .?!$�,;:\'\"&-]+", " ");
						StringTokenizer str = new StringTokenizer(text);
						while (str.hasMoreTokens()){
							String nextToken =str.nextToken();
							if(!nextToken.equals(" ")){
								//Troncature à 7 ! 
								if (withTronc && nextToken.length() > 7){
									nextToken = nextToken.substring(0, nextToken.length() - (nextToken.length()-7));
								}
								int statut = statement.executeUpdate( "INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+nextToken+"','"+filesName[i]+"','"+e.nodeName()+"');" );
								id ++;
							}
						}
					}

				}

/*				String grosText = sb.toString();
				grosText.replaceAll("alors|au|aucuns|aussi|autre|avant|avec|avoir|bon|car|ce|cela|ces|ceux|chaque|ci|comme|comment|dans|des|du|dedans|dehors|depuis|devrait|doit|donc|dos|début|elle|elles|en|encore|essai|est|et|eu|fait|faites|fois|font|hors|ici|il|ils|je|juste|la|le|les|leur|là|ma|maintenant|mais|mes|mine|moins|mon|mot|même|ni|nommés|notre|nous|ou|où|par|parce|pas|peut|peu|plupart|pour|pourquoi|quand|que|quel|quelle|quelles|quels|qui|sa|sans|ses|seulement|si|sien|son|sont|sous|soyez|sujet|sur|ta|tandis|tellement|tels|tes|ton|tous|tout|trop|très|tu|voient|vont|votre|vous|vu|ça|étaient|état|étions|été|être|a|à|l'|d'|j'|m'|n'", " ");
				//System.out.println(grosText);

				Statement statement = connexion.createStatement();

				StringTokenizer str = new StringTokenizer(grosText,"[ .,;:\'\"&-]+" );
				while (str.hasMoreTokens()){
					String nextToken =str.nextToken();
					if(!nextToken.equals(" ")){
						int statut = statement.executeUpdate( "INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+nextToken+"','"+filesName[i]+"',value);" );
						id ++;
					}
				}*/

				//			String [] texts=grosText.split("[ .,;:\'\"\\s+]");
				//			System.out.println(texts[34]);

				/* Exécution d'une requête d'écriture */

				//			for(String mot : texts){
				//				 
				//			}


		}
		closeConnexionToDB();
		} catch ( SQLException e ) {
			e.printStackTrace();
			//    Gérer les éventuelles erreurs ici 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {

		}
	}

	
	
	//TODO compl�ter cette fonction
	public void indexTronc7(){
		
	}




	public static void main(String[] args) {

		indexSimple(true);
	}
}
