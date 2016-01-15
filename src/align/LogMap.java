package align;

import java.util.Set;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.ox.krr.logmap2.LogMap2_Matcher;
import uk.ac.ox.krr.logmap2.mappings.objects.MappingObjectStr;;;

public class LogMap {
	public static void callLogMap() throws OWLOntologyCreationException{
		String  onto1_iri = "http://oaei.ontologymatching.org/tests/101/onto.rdf";
		String  onto2_iri ="http://oaei.ontologymatching.org/tests/304/onto.rdf";
		OWLOntologyManager  onto_manager = OWLManager.createOWLOntologyManager ();
		OWLOntology  onto1 = onto_manager.loadOntology(IRI.create(onto1_iri));
		OWLOntology  onto2 = onto_manager.loadOntology(IRI.create(onto2_iri));
		LogMap2_Matcher  logmap2 = new LogMap2_Matcher(onto1 , onto2);
		Set <MappingObjectStr> logmap2_mappings = logmap2.getLogmap2_Mappings();
		for (MappingObjectStr mappingObject : logmap2_mappings){
			System.out.println(" test des mots : "+mappingObject.toString());
		}
		System.out.println("Num.mappings: " + logmap2_mappings.size());	
	}
	
	
	public static void main(String[] args) throws OWLOntologyCreationException {
		LogMap.callLogMap();
	}
}
