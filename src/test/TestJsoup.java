package test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class TestJsoup {
	public static void main(String[] args) {
		try {
			Document doc = Jsoup.connect("http://en.wikipedia.org/").get();
			Elements newsHeadlines = doc.select("#mp-itn b a");
			
		} catch (Exception e){
			e.printStackTrace();
		}

	}

}
