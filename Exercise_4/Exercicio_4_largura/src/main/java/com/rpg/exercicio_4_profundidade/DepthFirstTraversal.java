/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rpg.exercicio_4_profundidade;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.logging.Logger;


public class DepthFirstTraversal extends TraversalStrategy {
    //Graph graph;
    //List<Vertex> path;
    Stack<Vertex> verticesToVisit;
    //boolean hasBeenVisited[];
    
    
    public DepthFirstTraversal(Graph graph){
        super(graph);
        verticesToVisit = new Stack<>();
       
    }

    @Override
    public void traverse(Vertex start){
        int startIndex, candidateIndex;
        Stack<Vertex> pathCandidates, newPathCandidates;
        Vertex candidate;
        
        startIndex = getGraph().getVertices().indexOf(start);
        
        visitVertex(start);
        markAsVisited(startIndex);
        
        //Due to the implementation of the graph, the vertex
        //index serves as their ID, since they exactly the order
        //in wich the vertex is added to the graph.
        storePathVertexID(startIndex);
        
        pathCandidates = getGraph().getConnectedVertices(startIndex);
        
        do{
            
            candidate = pathCandidates.pop();
            candidateIndex = getGraph().getVertexIndex(candidate);
            
            if(!hasVertexBeenVisited(candidateIndex)){
                visitVertex(candidate);
                markAsVisited(candidateIndex);
                storePathVertexID(candidateIndex);
                
                newPathCandidates = getGraph().getConnectedVertices(candidateIndex);
                
                //Add all the connected vertices of the new visited vertex
                //on the top of the "to visit vertex" stack, in order.
                pathCandidates.addAll(newPathCandidates); 
            }
        }while(!pathCandidates.empty());
    }
}
