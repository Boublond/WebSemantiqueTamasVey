package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class ModuleEvaluation {
	private static final String path ="documents/qrels/";
	
	
	private static void test(){
		
	}


	private static List<Document> genererListe(){
		List<Document> list = new ArrayList<Document>();
		File folder = new File(path);
		System.out.println(folder.getAbsolutePath());
		String []filesName=folder.list();

		BufferedReader br;

		try {


			for (String element : filesName) {
				System.out.println(element);

				br = new BufferedReader(new FileReader(path+element));
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
			}



		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
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
		List<Document> list = genererListe();

		for(Document d:list){
			System.out.println(d);
		}
	}

}
