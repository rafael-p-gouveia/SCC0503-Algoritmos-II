/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_Bonus_B;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


public abstract class TraversalStrategy {
    private Graph graph;
    private List<Vertex> path;
    private boolean hasBeenVisited[];
    private static final Logger LOGGER = Logger.getLogger(TraversalStrategy.class.getName());
    private static boolean hasLoggerBeenSet = false;
    //List of path vertices ID in order to print them.
    private List<Integer> pathVerticesID;
    private float distanceToVertices[];
    private int predecessorVertexIndices[];
    
    public TraversalStrategy(Graph graph){
        this.graph = graph;
        path = new ArrayList();
        hasBeenVisited = new boolean[graph.getNumberOfVertices()];
        pathVerticesID = new ArrayList(); 
        Arrays.fill(hasBeenVisited, false);
        distanceToVertices = new float[graph.getNumberOfVertices()];
        Arrays.fill(distanceToVertices, Float.POSITIVE_INFINITY);
        predecessorVertexIndices = new int [graph.getNumberOfVertices()];
        Arrays.fill(predecessorVertexIndices, -1);      
    }
    
    private void setLogger(){
        //Setting logger so that it doesn't print date and time
        //metadata along with the log, so that the output is according
        //to the specifications.
        LOGGER.setUseParentHandlers(false);
        CustomRecordFormatter formatter = new CustomRecordFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
        hasLoggerBeenSet = true;
    }
    
    public abstract void traverse(Vertex start);
    
    public Graph getGraph(){
        return graph;
    }
    
    public List<Vertex> getPath(){
        return path;
    }
    
    public boolean hasVertexBeenVisited(int indexVertex){
        return hasBeenVisited[indexVertex];
    }
    
    public void visitVertex(Vertex vertex){
        path.add(vertex);
    }
    
    public void markAsVisited(int vertexIndex){
        hasBeenVisited[vertexIndex] = true;
    }
    
    public void storePathVertexID(int vertexIndex){
        pathVerticesID.add(vertexIndex);
    }
    
    public void printPath(){
        var stringBuilder = new StringBuilder();
        Point startingPoint;
        
        startingPoint = ((Room)path.get(0)).getPoint();
        for(int i = 0; i < path.size(); i++){
            var vertex = path.get(i);
            stringBuilder.append(vertex.toString()).append(" Distance: ");
            stringBuilder.append(getDistanceToVertex(getGraph().getVertexIndex(vertex))).append("\n");
        }
        /*
        //Would be the correct "Java" way to print the results. The standard output was used instead, as requested. 
        if(!hasLoggerBeenSet){
            setLogger();
        }
        LOGGER.info(stringBuilder.toString());
        */
        System.out.print(stringBuilder.toString());
    }
    
    public void setDistanceToVertex(int vertexIndex, float distance)
    {
        distanceToVertices[vertexIndex] = distance;
    }
   
    public float getDistanceToVertex(int vertexIndex)
    {
        return distanceToVertices[vertexIndex];
    }
    
    public void setPredecessorVertexIndex(int currentVertexIndex, int predecessorIndex)
    {
        predecessorVertexIndices[currentVertexIndex] = predecessorIndex;
    }
    
    public int[] getPredecessorsArray(){
        return predecessorVertexIndices;
    }
}
