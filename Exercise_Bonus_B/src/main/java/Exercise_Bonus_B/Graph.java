package Exercise_Bonus_B;
import java.util.List;
import java.util.Stack;

public abstract class Graph {
    int numberOfVertices;
    List<Vertex> vertices;
    
    public Graph(List<Vertex> vertices){
        this.vertices = vertices;
        this.numberOfVertices = vertices.size();
    }
    
    public int getNumberOfVertices(){
        return this.numberOfVertices;
    }
    
    public List<Vertex> getVertices(){
        return this.vertices;
    }
    
    public int getVertexIndex(Vertex vertex){
        return vertices.indexOf(vertex);
    }
    
    public abstract void addEdge(Vertex source, Vertex destination, float value);
    public abstract void addEdge(int source, int destination, float value);
    public abstract void addEdge(String source, String destination, float value);
    
    public abstract Vertex getFirstConnectedVertex(Vertex source);
    public abstract Vertex getNextConnectedVertex(Vertex source, Vertex currentConnection);
    
    public abstract boolean hasAnyEdge(Vertex vertex);
    public abstract float getDistance(Vertex source, Vertex destination);
    public abstract float getDistance(int sourceIndex, int destinationIndex);
    
    //Returns a Stack of all the vertices connected to the given vertex.
    //The first vertex popped will be the first connected vertex added to
    //the graph, the second popped will be the second added and so on.
    public abstract Stack<Vertex> getConnectedVertices(int sourceIndex); 
}
