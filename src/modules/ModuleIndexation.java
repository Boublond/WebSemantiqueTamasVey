package modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	public static void indexSimple(boolean withTronc) throws IOException, SQLException{


		int id =0;
		connexionToDB();
		//
		File folder = new File("./documents/CORPUS/");
		File stoplist = new File("./documents/stoplist.txt");


		String []filesName=folder.list();
		Statement statement;

		for (int i=0;i<filesName.length;i++){

			int number =Integer.parseInt( filesName[i].replaceAll("[^0-9]", ""));
			FileWriter file_meta = new FileWriter(new File("./documents/meta"+i+".txt"));
			FileWriter file_h1 = new FileWriter(new File("./documents/h1"+i+".txt"));
			FileWriter file_h2 = new FileWriter(new File("./documents/h2"+i+".txt"));
			FileWriter file_b_or_strong = new FileWriter(new File("./documents/b_or_strong"+i+".txt"));
			FileWriter file_body = new FileWriter(new File("./documents/body"+i+".txt"));

			
			File input = new File("./documents/CORPUS/"+filesName[i]);
			System.out.println(input.getAbsolutePath());


				Document doc =Jsoup.parse(input, "UTF-8");
				
				StringBuilder sb_meta = new StringBuilder();
				StringBuilder sb_h1 = new StringBuilder();
				StringBuilder sb_h2 = new StringBuilder();
				StringBuilder sb_b_or_strong = new StringBuilder();
				StringBuilder sb_body = new StringBuilder();
				
				
				Elements metas = doc.getElementsByTag("meta");
				for (Element meta : metas) {
					// Récuperer le contenue de href 
					sb_meta.append(meta.attr("content")+ "\n");	
					System.out.println("on recupere chaque href des metas data");
				}
				
				Elements h1 = doc.getElementsByTag("h1");
				for (Element elemh1 : h1) {
					sb_h1.append(elemh1.text()+ "\n");
					}
				
				Elements h2 = doc.getElementsByTag("h2");
				for (Element elemh2 : h2) {
					sb_h2.append(elemh2.text()+ "\n");
					}
				
				Elements b = doc.getElementsByTag("b");
				for (Element elemb : b) {
					sb_b_or_strong.append(elemb.text()+ "\n");								
				}
				Elements strong = doc.getElementsByTag("strong");
				for (Element elemstrong : strong) {
					sb_b_or_strong.append(elemstrong.text()+ "\n");								
				}
				
				//get body
				Elements body = doc.getElementsByTag("body");	
				for (Element elembody : body) {
					sb_body.append(elembody.text()+ "\n");								
				}
				
				sb_meta= new StringBuilder(sb_meta.toString().replaceAll("([a-z]|[éèàçù])([A-Z])", "$1 $2"));
				sb_meta=new StringBuilder(sb_meta.toString().toLowerCase());
				sb_meta= new StringBuilder(sb_meta.toString().replaceAll("[0-9]|,|l'|m'|n'|s'|t'|qu'|c'|d'|l’|m’|n’|s’|t’|qu’|c’|d’|\\.|/|:|;|'|\\[|\\]|»|«|\\(|\\)|\"|\\%|…", " "));
				
				sb_h1= new StringBuilder(sb_h1.toString().replaceAll("([a-z]|[éèàçù])([A-Z])", "$1 $2"));
				sb_h1=new StringBuilder(sb_h1.toString().toLowerCase());
				sb_h1= new StringBuilder(sb_h1.toString().replaceAll("[0-9]|,|l'|m'|n'|s'|t'|qu'|c'|d'|l’|m’|n’|s’|t’|qu’|c’|d’|\\.|/|:|;|'|\\[|\\]|»|«|\\(|\\)|\"|\\%|…", " "));

				sb_h2= new StringBuilder(sb_h2.toString().replaceAll("([a-z]|[éèàçù])([A-Z])", "$1 $2"));
				sb_h2=new StringBuilder(sb_h2.toString().toLowerCase());
				sb_h2= new StringBuilder(sb_h2.toString().replaceAll("[0-9]|,|l'|m'|n'|s'|t'|qu'|c'|d'|l’|m’|n’|s’|t’|qu’|c’|d’|\\.|/|:|;|'|\\[|\\]|»|«|\\(|\\)|\"|\\%|…", " "));

				sb_b_or_strong= new StringBuilder(sb_b_or_strong.toString().replaceAll("([a-z]|[éèàçù])([A-Z])", "$1 $2"));
				sb_b_or_strong=new StringBuilder(sb_b_or_strong.toString().toLowerCase());
				sb_b_or_strong= new StringBuilder(sb_b_or_strong.toString().replaceAll(",|l'|m'|n'|s'|t'|qu'|c'|d'|l’|m’|n’|s’|t’|qu’|c’|d’|\\.|/|:|;|'|\\[|\\]|»|«|\\(|\\)|\"|\\%|…", " "));

				sb_body= new StringBuilder(sb_body.toString().replaceAll("([a-z]|[éèàçù])([A-Z])", "$1 $2"));
				sb_body=new StringBuilder(sb_body.toString().toLowerCase());
				sb_body= new StringBuilder(sb_body.toString().replaceAll("[0-9]|,|l'|m'|n'|s'|t'|qu'|c'|d'|l’|m’|n’|s’|t’|qu’|c’|d’|\\.|/|:|;|'|\\[|\\]|»|«|\\(|\\)|\"|\\%|…", " "));

				BufferedReader br = new BufferedReader(new FileReader(stoplist));
				String line = null;
				while ((line = br.readLine()) != null) {
					sb_meta= new StringBuilder(sb_meta.toString().replaceAll(" "+line+ " ", " "));
					sb_meta= new StringBuilder(sb_meta.toString().replaceAll(" "+line+"'", " "));
					
					sb_h1= new StringBuilder(sb_h1.toString().replaceAll(" "+line+ " ", " "));
					sb_h1= new StringBuilder(sb_h1.toString().replaceAll(" "+line+"'", " "));
				
					sb_h2= new StringBuilder(sb_h2.toString().replaceAll(" "+line+ " ", " "));
					sb_h2= new StringBuilder(sb_h2.toString().replaceAll(" "+line+"'", " "));
				
					sb_b_or_strong= new StringBuilder(sb_b_or_strong.toString().replaceAll(" "+line+ " ", " "));
					sb_b_or_strong= new StringBuilder(sb_b_or_strong.toString().replaceAll(" "+line+"'", " "));
				
					sb_body= new StringBuilder(sb_body.toString().replaceAll(" "+line+ " ", " "));
					sb_body= new StringBuilder(sb_body.toString().replaceAll(" "+line+"'", " "));
				
				}
				br.close();


				sb_meta= new StringBuilder(sb_meta.toString().replaceAll("[ \t\n\b\r\f ]+", "\n"));
				sb_h1= new StringBuilder(sb_h1.toString().replaceAll("[ \t\n\b\r\f ]+", "\n"));
				sb_h2= new StringBuilder(sb_h2.toString().replaceAll("[ \t\n\b\r\f ]+", "\n"));
				sb_b_or_strong= new StringBuilder(sb_b_or_strong.toString().replaceAll("[ \t\n\b\r\f ]+", "\n"));
				sb_body= new StringBuilder(sb_body.toString().replaceAll("[ \t\n\b\r\f ]+", "\n"));

				file_meta.write(sb_meta.toString());
				file_meta.close();
				file_h1.write(sb_h1.toString());
				file_h1.close();
				file_h2.write(sb_h2.toString());
				file_h2.close();
				file_b_or_strong.write(sb_b_or_strong.toString());
				file_b_or_strong.close();
				file_body.write(sb_body.toString());
				file_body.close();
				System.out.println("Fin de l'indexation");
				statement = connexion.createStatement();
				int statut; 
				String request = ""; 

				InputStream ips_meta=new FileInputStream("./documents/meta"+i+".txt"); 
				InputStreamReader ipsr_meta=new InputStreamReader(ips_meta);
				BufferedReader br_meta=new BufferedReader(ipsr_meta);
				String ligne_meta;
				while ((ligne_meta=br_meta.readLine())!=null){
					if (ligne_meta.length() > 7)
					{
						ligne_meta = ligne_meta.substring(0,7); // On tronque le terme
						
					} 
					if (ligne_meta != null && !ligne_meta.contains("\\")){
						System.out.println(ligne_meta);
						request = " INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+ligne_meta+"','"+filesName[i]+"','meta');";
						statut = statement.executeUpdate(request);
						id++;
					}
				}
				
				InputStream ips_h1=new FileInputStream("./documents/h1"+i+".txt"); 
				InputStreamReader ipsr_h1=new InputStreamReader(ips_h1);
				BufferedReader br_h1=new BufferedReader(ipsr_h1);
				String ligne_h1;
				while ((ligne_h1=br_h1.readLine())!=null ){
					if (ligne_h1.length() > 7)
					{
						ligne_h1 = ligne_h1.substring(0,7); // On tronque le terme
						
					} 
					if (ligne_h1 != null){

					request = " INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+ligne_h1+"','"+filesName[i]+"','h1');";
					statement = connexion.createStatement();

					id++;
					}
					
				}
				
				InputStream ips_h2=new FileInputStream("./documents/h2"+i+".txt"); 
				InputStreamReader ipsr_h2=new InputStreamReader(ips_h2);
				BufferedReader br_h2=new BufferedReader(ipsr_h2);
				String ligne_h2;
				while ((ligne_h2=br_h2.readLine())!=null){
					if (ligne_h2.length() > 7)
					{
						ligne_h2 = ligne_h2.substring(0,7); // On tronque le terme
						
					} 
					System.out.println(ligne_h2);
					if (ligne_h2 != null|| ligne_h2.contains("'")){
	
						request = " INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+ligne_h2+"','"+filesName[i]+"','h2');";
						statement = connexion.createStatement();
	
						id++;
					}
				}
				
				InputStream ips_b_or_strong=new FileInputStream("./documents/b_or_strong"+i+".txt"); 
				InputStreamReader ipsr_b_or_strong=new InputStreamReader(ips_b_or_strong);
				BufferedReader br_b_or_strong=new BufferedReader(ipsr_b_or_strong);
				String ligne_b_or_strong;
				while ((ligne_b_or_strong=br_b_or_strong.readLine())!=null){
					if (ligne_b_or_strong.length() > 7)
					{
						ligne_b_or_strong = ligne_b_or_strong.substring(0,7); // On tronque le terme
						
					} 
					if (ligne_b_or_strong != null|| ligne_b_or_strong.contains("'")){
						
						request =" INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+ligne_b_or_strong+"','"+filesName[i]+"','b_or_strong');";
						statement = connexion.createStatement();
	
						id++;
					}
				}
				
				InputStream ips_body=new FileInputStream("./documents/body"+i+".txt"); 
				InputStreamReader ipsr_body=new InputStreamReader(ips_body);
				BufferedReader br_body=new BufferedReader(ipsr_body);
				String ligne_body;
				while ((ligne_body=br_body.readLine())!=null){
					if (ligne_body.length() > 7)
					{
						ligne_body = ligne_body.substring(0,7); // On tronque le terme
						
					} 
					if (ligne_body != null|| ligne_body.contains("'")){

						request = " INSERT INTO dico (id, mot, doc, balise) VALUES ("+id+",'"+ligne_body+"','"+filesName[i]+"','body');";
						statement = connexion.createStatement();
	
						id++;
				
					}
				}
				System.out.println("fin de l'ajout des mots d'un fichier ");
				
		}
		closeConnexionToDB();
	}

	
	
	//TODO compl�ter cette fonction
	public void indexTronc7(){
		
	}




	public static void main(String[] args) {

		try {
			indexSimple(true);
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
