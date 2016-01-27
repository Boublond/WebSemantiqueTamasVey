package Sparql;

import java.util.ArrayList;
import java.util.Map;

public class SparqlClientExample {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
    		String mot = "prix";
    		ArrayList<String> synonymes = SparqlClient.findSynonyme(mot);
    		
    }
    
    private static void nbPersonnesParPiece(SparqlClient sparqlClient) {
        String query = "PREFIX : <http://www.lamaisondumeurtre.fr#>\n"
                    + "SELECT ?piece (COUNT(?personne) AS ?nbPers) WHERE\n"
                    + "{\n"
                    + "    ?personne :personneDansPiece ?piece.\n"
                    + "}\n"
                    + "GROUP BY ?piece\n";
            Iterable<Map<String, String>> results = sparqlClient.select(query);
            System.out.println("nombre de personnes par pièce:");
            for (Map<String, String> result : results) {
                System.out.println(result.get("piece") + " : " + result.get("nbPers"));
            }
    }    
}
