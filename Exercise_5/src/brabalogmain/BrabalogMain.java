package brabalogmain;
import brabalog.InputParser;
import graph.*;

/**
 * The main class.
 * @author Maxsuel F. de Almeida.
 * @author Rafael P. Gouveia
 */
public class BrabalogMain{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        InputParser parser;
        AbstractGraph graph;
        FloydWarshallTraversal traversalStrategy;
        Vertex centermostVertex, outermostVertex, mostDistantVertex;
        float[][] distanceMatrix;
        
        parser = new InputParser();
        graph = parser.createGraph();
        traversalStrategy = new FloydWarshallTraversal(graph);
        traversalStrategy.traverseGraph(graph.getVertices().get(0));
        distanceMatrix = traversalStrategy.getDistanceMatrix();
        
        
 
        centermostVertex = 
                graph.getCentermostVertex(distanceMatrix);
        
        outermostVertex = 
                graph.getOuterMostVertex(distanceMatrix);
        
        mostDistantVertex = 
                graph.getMostDistantVertex(distanceMatrix, outermostVertex);
                
        System.out.println((int)centermostVertex.getXCoord() + 
                "," + (int)centermostVertex.getYCoord());
        
        System.out.println((int)outermostVertex.getXCoord() + 
                "," + (int)outermostVertex.getYCoord());
        
        System.out.println((int)mostDistantVertex.getXCoord() + 
                "," + (int)mostDistantVertex.getYCoord());
    }
}