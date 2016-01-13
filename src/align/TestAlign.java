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

import fr.inrialpes.exmo.align.impl.eval.PRecEvaluator;
import fr.inrialpes.exmo.align.impl.method.ClassStructAlignment;
import fr.inrialpes.exmo.align.impl.method.EditDistNameAlignment;
import fr.inrialpes.exmo.align.impl.method.NameAndPropertyAlignment;
import fr.inrialpes.exmo.align.impl.method.NameEqAlignment;
import fr.inrialpes.exmo.align.impl.method.SMOANameAlignment;
import fr.inrialpes.exmo.align.impl.renderer.RDFRendererVisitor;
import fr.inrialpes.exmo.align.parser.AlignmentParser;

public class TestAlign {
	public static void main(String[] args) {
		try {
			for (int i=0;i<5;i++){
				AlignmentProcess align = generateAlign(i);
				render(align,i);
				evaluate(align);
			}

			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	//type :0 NameEqAlignment
	//type : 1 EditDistNameAlignment 
	//type : 2 SMOANameAlignment
	//type : 3 NameAndPropertyAlignment 
	//type : 4 ClassStructAlignment

	public static AlignmentProcess generateAlign (int type) throws URISyntaxException ,AlignmentException {
		
		System.out.println(type);
		URI onto1 = new URI("http://oaei.ontologymatching.org/tests/101/onto.rdf");
		URI onto2=new URI("http://oaei.ontologymatching.org/tests/304/onto.rdf");
		AlignmentProcess alignment;
		
		switch(type){
		case 1:
			alignment= new EditDistNameAlignment();
			break;
		case 2:
			alignment= new SMOANameAlignment();
			break;
		case 3:
			alignment= new NameAndPropertyAlignment();
			break;
		case 4:
			alignment= new ClassStructAlignment();
			break;
		default:
			alignment = new NameEqAlignment();
		
		}
		
		
		alignment . init ( onto1 , onto2 ) ;
		alignment . align (null , new Properties () ) ;
		System . out . println ("Num corresp . générées : " + alignment . nbCells () ) ;
		return alignment;
	}

	public static void render ( Alignment alignment,int num ) throws
	FileNotFoundException , UnsupportedEncodingException , AlignmentException
	{
		PrintWriter writer ;
		FileOutputStream f = new FileOutputStream (new File ("./alignements/"+num+".rdf") ) ;
		writer = new PrintWriter ( new BufferedWriter ( new OutputStreamWriter (f ,
				"UTF-8" )) , true ) ;
		AlignmentVisitor renderer = new RDFRendererVisitor ( writer ) ;
		alignment . render ( renderer ) ;
		writer . flush () ;
		writer . close () ;
	}

	public static void evaluate ( Alignment alignment ) throws URISyntaxException ,
	AlignmentException {
		URI reference = new URI ("http://oaei.ontologymatching.org/tests/304/refalign.rdf");
		AlignmentParser aparser = new AlignmentParser (0) ;
		Alignment refalign = aparser . parse ( reference );
		PRecEvaluator evaluator = new PRecEvaluator ( refalign , alignment ) ;
		evaluator . eval (new Properties () ) ;
		System . out . println (" Precision : " + evaluator . getPrecision () ) ;
		System . out . println (" Recall :" + evaluator . getRecall () ) ;
		System . out . println (" FMeasure :" + evaluator . getFmeasure () ) ;
	}

	public static String mofidyURI (String URI){
		URI = URI.substring(URI.lastIndexOf("#")+1, URI.length()-1);
		return URI;
	}

}
