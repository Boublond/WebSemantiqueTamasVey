package align;
// Alignment API classes
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

import org.semanticweb.owl.align.Alignment;
import org.semanticweb.owl.align.AlignmentException;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import fr.inrialpes.exmo.align.impl.URIAlignment;
import fr.inrialpes.exmo.ontowrap.OntowrapException;

/**
** Copyright (C) IRIT, 2015-2016
**/
public class Matcher extends URIAlignment {

	private OWLOntology ontology1;
	private OWLOntology ontology2;

	private OWLOntologyManager man1;
	private OWLOntologyManager man2;
	
	public Matcher() {
    			 
        }
   
	/**
	 * Initialise the alignment parameters
	 * @param uri1
	 * @param uri2
	 * @throws AlignmentException
	 * @throws OWLOntologyCreationException
	 * @throws OntowrapException
	 */
	public void init(URI uri1, URI uri2) throws AlignmentException, OWLOntologyCreationException, OntowrapException {
		   super.init(uri1, uri2);
		   load(uri1, uri2);
	}
	
	/**
	 * Load the ontologies
	 * @param uri1
	 * @param uri2
	 * @throws OWLOntologyCreationException
	 * @throws AlignmentException
	 * @throws OntowrapException
	 */
    public void load(URI uri1, URI uri2) throws OWLOntologyCreationException, AlignmentException, OntowrapException {
    	   man1 = OWLManager.createOWLOntologyManager();
    	   man2 = OWLManager.createOWLOntologyManager();
	       ontology1 = man1.loadOntologyFromOntologyDocument(IRI.create(uri1));
	       ontology2 = man2.loadOntologyFromOntologyDocument(IRI.create(uri2));
     }
    
    /**
     * Generate an alignment (set of correspondences)
     * @throws AlignmentException 
     * @throws URISyntaxException 
     */
    public void align( Alignment alignment, Properties param )  {
	       // For the classes : no optmisation cartesian product !
    	   for ( OWLEntity cl1 : ontology1.getClassesInSignature()){
 			  	for ( OWLEntity cl2: ontology2.getClassesInSignature() ){
 			  		   double confidence = match(cl1,cl2);
				       if (confidence > 0) {
				    	   try {
				    		   addAlignCell(cl1.getIRI().toURI(),cl2.getIRI().toURI(),"=", confidence);
				    	   } catch (Exception e) {
				    		   System.out.println(e.toString());
				    	   }
				  	   }
			       }
    	   }
    	   
    }

    /**
     * Get the labels in lang for a given entity 
     * @param entity
     * @param ontology
     * @param lang
     * @return
     */
    private ArrayList<OWLLiteral> getLabels(OWLEntity entity, OWLOntology ontology, String lang) {
    	OWLDataFactory df =  OWLManager.createOWLOntologyManager().getOWLDataFactory();
	    OWLAnnotationProperty label = df.getOWLAnnotationProperty(OWLRDFVocabulary.RDFS_LABEL.getIRI());	
		
    	ArrayList<OWLLiteral> labels = new ArrayList<OWLLiteral>();
       	for (OWLAnnotation annotation : entity.getAnnotations(ontology, label)) {
            if (annotation.getValue() instanceof OWLLiteral) {
                OWLLiteral val = (OWLLiteral) annotation.getValue();
                if (val.hasLang("en")) {
                    labels.add(val);
                }
            }
        }
       	return labels;
    }
    
    public static ArrayList<OWLDataProperty> getDataProperties(OWLOntology ontology) {
    	Set<OWLDataPropertyDomainAxiom> set =ontology.getAxioms(AxiomType.DATA_PROPERTY_DOMAIN);
    	ArrayList<OWLDataProperty> dataProperties = new ArrayList<OWLDataProperty>();
    	for(OWLDataPropertyDomainAxiom d : set) {
    		dataProperties.addAll(d.getDataPropertiesInSignature());
    	}
    	
    	return dataProperties; 
    }
    
    public static ArrayList<OWLObjectProperty> getObjectProperties(OWLOntology ontology) {
    	Set<OWLObjectPropertyDomainAxiom> set =ontology.getAxioms(AxiomType.OBJECT_PROPERTY_DOMAIN);
    	ArrayList<OWLObjectProperty> objectProperties = new ArrayList<OWLObjectProperty>();
    	for(OWLObjectPropertyDomainAxiom d : set) {
    		objectProperties.addAll(d.getObjectPropertiesInSignature());
    	}
    	
    	return objectProperties; 
    }
    
    
     
    /**
     * Match two specific ontology entities o1 and o2
     * @param o1
     * @param o2
     * @return
     */
    public double match(OWLEntity o1, OWLEntity o2) {
    	ArrayList<OWLLiteral> labels1 = getLabels(o1,ontology1,"en");
    	ArrayList<OWLLiteral> labels2 = getLabels(o2,ontology2,"en");
    	for (OWLLiteral lit1 : labels1) {
    		for (OWLLiteral lit2 : labels2) {
                 // Comparison based on equality of labels
    			 if (lit1.getLiteral().toLowerCase().equals(lit2.getLiteral().toLowerCase())) { 
    				 return 1.0;
    			 }
            }
    	}
    	return 0.;
    }            
}