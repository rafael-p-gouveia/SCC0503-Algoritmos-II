/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Dell
 */

public class BreadthFirstTraversal extends TraversalStrategy{
    Queue<Vertex> verticesToVisit;
    
    public BreadthFirstTraversal(Graph graph){
        super(graph);
        
    }
    
    @Override
    public void traverse(Vertex start){
        verticesToVisit = new <Vertex>LinkedList();
        
        setDistanceToVertex(getGraph().getVertexIndex(start),0);
        verticesToVisit.add(start);
        
        while(!verticesToVisit.isEmpty()){
            Vertex candidate;
            int candidateIndex;
            
            candidate = verticesToVisit.poll();
            candidateIndex = getGraph().getVertexIndex(candidate);
            
            if(!hasVertexBeenVisited(candidateIndex)){
                visitVertex(candidate);
                markAsVisited(candidateIndex);
                storePathVertexID(candidateIndex);
                
                var newPathCandidates = getGraph().getConnectedVertices(candidateIndex);
                while(!newPathCandidates.empty()){ //get neighbor vertices
                    var neighborVertex = newPathCandidates.pop();
                    var neighborIndex = getGraph().getVertexIndex(neighborVertex);
                    //first time this neighbor vertex was analized
                    if (!verticesToVisit.contains(neighborVertex)&&!hasVertexBeenVisited(neighborIndex)){
                        
                        float distanceToNeighbor = getGraph().getDistance(candidateIndex, neighborIndex);
                        setDistanceToVertex(neighborIndex,getDistanceToVertex(candidateIndex) + distanceToNeighbor);
                        
                        verticesToVisit.add(neighborVertex);
                    }  
                }
            }
        }
    }

    @Override
    public void traverse(Vertex start, Vertex end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
