package modules;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import data.Document;

public class ModuleEvaluation {
	private static final String path ="documents/qrels/";



	private static List<Document> genererListe(String name){
		List<Document> list = new ArrayList<Document>();
		File folder = new File(path);
		System.out.println(folder.getAbsolutePath());
		String []filesName=folder.list();

		BufferedReader br;

		try {



			System.out.println(name);

			br = new BufferedReader(new FileReader(path+name));
			String line = new String();


			while (line != null) {
				line = br.readLine();
				System.out.println(line);

				if (line!=null){
					String words[] = line.split("\t");
					list.add(new Document(parseFloat(words[1]), words[0]));
				}

			}

			br.close();

			return list;




		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
	
	
	private static float evaluer(List<Document> maListe,List<Document> laBonneListe,int criter){
		float result=0f;
		for(int i=0;i<criter;i++){
			Document doc = maListe.get(i);
			result+=getPertinenceByName(doc.getName(),laBonneListe);	
		}
		return result/criter;
	}
	
	private static float getPertinenceByName(String name,List<Document> laBonneListe){
		for(Document doc:laBonneListe){
			if(doc.getName().equals(name)){
				return doc.getPertinence();
			}
		}
		return 0f;
	}

	private static float parseFloat(String s){
		if (s.equals("0")){
			return 0f;
		} else if (s.equals("1")){
			return 1f;
		} else {
			return 0.5f;
		}
	}


	public static void main(String[] args) {
		List<Document> list = genererListe("qrelQ1.txt");
		Collections.sort(list);
		
		for(Document d:list){
			System.out.println(d);
		}
	}

}
