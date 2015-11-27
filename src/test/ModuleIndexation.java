package test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ModuleIndexation {
	public static void main(String[] args) {
		File input = new File("./documents/CORPUS/D1.html");

		try {
			Document doc =Jsoup.parse(input, "UTF-8");
			Elements elements = doc.getAllElements();
			
			StringBuilder sb = new StringBuilder();
			for (Element e:elements){
				if (!e.tagName().equals("script") || e.tagName().equals("style")){
					sb.append(" "+e.text());
				} 
				
			}
			
			String grosText = sb.toString();
			System.out.println(grosText);
			String [] texts=grosText.split("\\s+");
			System.out.println(texts[34]);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
