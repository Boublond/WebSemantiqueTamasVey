package test;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ModuleIndexation {
	public static void main(String[] args) {
		File input = new File("./documents/CORPUS/D1.html");
//		File file = new File(".");
//		System.out.println(file.getAbsolutePath());
		try {
			Document doc =Jsoup.parse(input, "UTF-8");
			Elements tags=doc.getElementsByTag("*");
			System.out.println(doc.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
