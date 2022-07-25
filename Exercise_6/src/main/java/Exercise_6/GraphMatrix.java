/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class GraphMatrix extends DigraphMatrix{ //Non-directional graph
    
    public GraphMatrix(){
        super();
    }
    
    public GraphMatrix(ArrayList<Vertex> vertices){
        super(vertices); 
    }
    
    @Override
    public void addEdge(Vertex source, Vertex destination, float value){
        int iSource, iDestination;
        iSource = getVertexIndex(source);
        iDestination = getVertexIndex(destination);
        addEdge(iSource,iDestination,value);
    }
    
    @Override
    public void addEdge(int source, int destination, float value){
        super.addEdge(source, destination, value);
        super.addEdge(destination, source, value);
    }
}
