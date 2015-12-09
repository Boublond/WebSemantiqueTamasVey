package test;

import java.io.BufferedReader;
import java.io.FileReader;

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
				sb.append(System.lineSeparator());
				line = br.readLine();
			}
			String everything = sb.toString();
			System.out.println(everything);
			br.close();
			return everything.split(",");
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;

		
	}

	public static void main(String[] args) {
		lireRequete("Q1");

	}
}

