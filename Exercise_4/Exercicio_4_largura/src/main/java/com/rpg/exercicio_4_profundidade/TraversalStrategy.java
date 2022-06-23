/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rpg.exercicio_4_profundidade;

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
    //List of path vertices ID in order to print them.
    private List<Integer> pathVerticesID;
    
    public TraversalStrategy(Graph graph){
        this.graph = graph;
        path = new ArrayList();
        hasBeenVisited = new boolean[graph.numberOfVertices];
        pathVerticesID = new ArrayList(); 
        Arrays.fill(hasBeenVisited, false);
        
        //Setting logger so that it doesn't print date and time
        //metadata along with the log, so that the output is according
        //to the specifications.
        LOGGER.setUseParentHandlers(false);
        CustomRecordFormatter formatter = new CustomRecordFormatter();
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        LOGGER.addHandler(consoleHandler);
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
        
        for(int i = 0; i < path.size(); i++){
            stringBuilder.append("Quest{\n	ID= '").append(pathVerticesID.get(i).toString()).append("'\n");
            stringBuilder.append("	name= '").append(path.get(i).getName()).append("'\n");
            stringBuilder.append("	description= '").append(path.get(i).getDescription()).append("'\n}\n");
            if(i != (path.size() - 1)){
                stringBuilder.append("\n");
            }
        }
        LOGGER.info(stringBuilder.toString());
    }
}
