package Exercise_6;
import java.io.File;
import java.io.IOException;
import java.text.Format;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

public class DigraphMatrix extends Graph{
    Edge recurrenceMatrix[][];
    
    public DigraphMatrix(){
        super(new ArrayList<Vertex>());
        initializeMatrix();
    }
    
    public DigraphMatrix(ArrayList<Vertex> vertices){
        super(vertices); 
        initializeMatrix();
        
    }
    
    private void initializeMatrix(){
        int i,j;
        
        recurrenceMatrix = new Edge[numberOfVertices][numberOfVertices];
        for(i = 0; i < numberOfVertices; i++){
            for(j = 0; j < numberOfVertices; j++){
                recurrenceMatrix[i][j] = null; 
            }
        }
    }
    
    public void addVertex(Vertex vertex){
        int i,j;
        Edge newMatrix[][];
        newMatrix = new Edge[numberOfVertices + 1][numberOfVertices + 1];
        
        for(i = 0; i < numberOfVertices; i++){
            for(j = 0; j < numberOfVertices; j++){
                newMatrix[i][j] = recurrenceMatrix[i][j]; 
            }
        }
        for(i = 0; i < numberOfVertices + 1; i++){
            newMatrix[i][numberOfVertices] = null;
        }
        for(j = 0; j < numberOfVertices + 1; j++){
            newMatrix[numberOfVertices][j] = null;
        }
        
        super.vertices.add(vertex);
        super.numberOfVertices++;
    }
    
    public boolean hasAnyEdge(Vertex vertex){
        int index;
        index = getVertexIndex(vertex);
        for(int i = 0; i < numberOfVertices; i++){
            if(recurrenceMatrix[i][index] != null){
                return true;
            }
        }
        return false;
    }
    
    public boolean edgeExists(int source, int destination){
        return recurrenceMatrix[source][destination] != null;
    }
    
    public boolean edgeExists(Vertex source, Vertex destination){
        return edgeExists(getVertexIndex(source),getVertexIndex(destination));
    }
    
    @Override
    public void addEdge(Vertex source, Vertex destination, float value){
        addEdge(getVertexIndex(source),getVertexIndex(destination),value);
    }
    
    @Override
    public void addEdge(int source, int destination, float value){
        if(!edgeExists(source,destination)){
            recurrenceMatrix[source][destination] = new Edge(value);
        }
    }
    
    @Override
    public void addEdge(String source, String destination, float value){
        int indexSource, indexDestination;
        
        indexSource = getVertexIndex(source);
        indexDestination = getVertexIndex(destination);
        if(indexSource != -1 && indexDestination != -1){
            addEdge(indexSource, indexDestination, value);
        }
    }
    
    public int getVertexIndex(Vertex vertex){ //busca linear podre. n teria esse problema se usasse hashmap
        int i;
        List<Vertex> vertices;
        
        vertices = getVertices();
        
        for(i = 0; i < numberOfVertices; i++){
            if(vertices.get(i).equals(vertex)){
                return i;
            }
        }
        return -1;
    }
    
    public int getVertexIndex(String name){ //busca linear podre. n teria esse problema se usasse hashmap
        int i;
        List<Vertex> vertices;
        
        vertices = getVertices();
        
        for(i = 0; i < numberOfVertices; i++){
            if(vertices.get(i).getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    
    @Override
    public Stack<Vertex> getConnectedVertices(int sourceIndex){
        int j;
        Stack<Vertex> connectedVertices;
        
        connectedVertices = new Stack<>();
        
        if(sourceIndex != -1){
            for(j = getNumberOfVertices() - 1; j >= 0; j--){
                if(recurrenceMatrix[sourceIndex][j] != null){
                    connectedVertices.push(getVertices().get(j));
                }
            }
        }
        return connectedVertices;
    }
    
    @Override
    public Vertex getFirstConnectedVertex(Vertex vertex)
    {
        if(!hasAnyEdge(vertex))
        {
            return null;
        }
        else
        {
            var currentVertexIndex = 0;
            Vertex connected;
            do
            {
                connected = getVertices().get(currentVertexIndex++);
            }while(!edgeExists(vertex, connected));
            return connected;
        }
    }

    @Override
    public Vertex getNextConnectedVertex(Vertex source, Vertex currentConnection)
    {
        Vertex newConnection;
        for (int i = getVertices().indexOf(currentConnection)+1; i < getNumberOfVertices(); i++)
        {
            newConnection = getVertices().get(i);
            if(edgeExists(source, newConnection))
            {
                return newConnection;
            }
        }
        return null;
    }
    
    @Override
    public float getDistance(int sourceIndex, int destinationIndex) {
        var edge = recurrenceMatrix[sourceIndex][destinationIndex];

        if(edge == null) return -1;

        return recurrenceMatrix[sourceIndex][destinationIndex].getValue();
    }
    
    @Override
    public float getDistance(Vertex source, Vertex destination) {
        int sourceIndex = getVertices().indexOf(source);
        int destinationIndex = getVertices().indexOf(destination);
        
        return getDistance(sourceIndex, destinationIndex);
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < numberOfVertices; i++) {
            s.append(i).append("(").append(vertices.get(i).getName()).append("): ");
            for (int j = 0; j < numberOfVertices; ++j)
            {
                if(edgeExists(i, j)){
                    s.append(recurrenceMatrix[i][j].value);
                }
                else{
                    s.append("no connection");
                }
                s.append(" to ").append(j).append("(").append(vertices.get(j).getName()).append(");");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
