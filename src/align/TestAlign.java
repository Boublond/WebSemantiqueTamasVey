package align;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owl.align.AlignmentProcess;
import org.semanticweb.owl.align.AlignmentVisitor;

import fr.inrialpes.exmo.align.impl.method.NameEqAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;

public class TestAlign {
	public static void main(String[] args) {
		try {
			generateAlign();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	public static void generateAlign () throws URISyntaxException ,
	AlignmentException {
		URI onto1 = new URI("http://oaei.ontologymatching.org/tests/101/onto.rdf");
		URI onto2=new URI("http://oaei.ontologymatching.org/tests/302/onto.rdf");
		AlignmentProcess alignment = new NameEqAlignment();
		alignment . init ( onto1 , onto2 ) ;
		alignment . align (null , new Properties () ) ;
		System . out . println ("Num corresp . générées : " + alignment . nbCells () ) ;
	}

	public static void render ( Alignment alignment ) throws
	FileNotFoundException , UnsupportedEncodingException , AlignmentException
	{
		PrintWriter writer ;
		FileOutputStream f = new FileOutputStream (new File ("C:/Users/Aur�lien/Downloads/align/test.rdf") ) ;
		writer = new PrintWriter ( new BufferedWriter ( new OutputStreamWriter (f ,
				"UTF -8" )) , true ) ;
		AlignmentVisitor renderer = new RDFRendererVisitor ( writer ) ;
		alignment . render ( renderer ) ;
		writer . flush () ;
		writer . close () ;
	}

}
