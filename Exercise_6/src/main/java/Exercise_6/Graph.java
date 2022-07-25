package Exercise_6;
import java.util.Arrays;
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
    
    public abstract boolean edgeExists(Vertex source, Vertex destination);
    
    //Returns a Stack of all the vertices connected to the given vertex.
    //The first vertex popped will be the first connected vertex added to
    //the graph, the second popped will be the second added and so on.
    public abstract Stack<Vertex> getConnectedVertices(int sourceIndex); 
    
    public Vertex getCentermostVertex(float[][] distanceMatrix)
    {
        float []maxDistanceInCollumn = new float[distanceMatrix.length];
        Arrays.fill(maxDistanceInCollumn, Float.NEGATIVE_INFINITY);
        for (int i = 0; i < distanceMatrix.length; i++)
        {
            for (int j = 0; j < distanceMatrix[0].length; j++)
            {
                if (maxDistanceInCollumn[i] < distanceMatrix[i][j])
                {
                    maxDistanceInCollumn[i] = distanceMatrix[i][j];
                }
            }
        }
        int vertexIndex = getMinDistanceIndexInCollumn(maxDistanceInCollumn);
        return vertices.get(vertexIndex);
    }

    public Vertex getOuterMostVertex(float[][] distanceMatrix)
    {
        var maxDistanceInCollumn = new float[distanceMatrix.length];
        Arrays.fill(maxDistanceInCollumn, Float.NEGATIVE_INFINITY);
        for (var i = 0; i < distanceMatrix.length; i++)
        {
            for (var j = 0; j < distanceMatrix[0].length; j++)
            {
                if (maxDistanceInCollumn[i] < distanceMatrix[i][j])
                {
                    maxDistanceInCollumn[i] = distanceMatrix[i][j];
                }
            }
        }
        int vertexIndex = getMaxDistanceIndexInCollumn(maxDistanceInCollumn);
        return vertices.get(vertexIndex);
    }

    private int getMinDistanceIndexInCollumn(float[] distanceArray)
    {
        var minIndex = 0;
        var minDistance = distanceArray[0];
        for (var i = 1; i < distanceArray.length; i++)
        {
            if(minDistance > distanceArray[i])
            {
                minDistance = distanceArray[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    private int getMaxDistanceIndexInCollumn(float[] distanceArray)
    {
        var maxIndex = 0;
        float maxDistance = distanceArray[0];
        for (var i = 1; i < distanceArray.length; i++)
        {
            if(maxDistance < distanceArray[i])
            {
                maxDistance = distanceArray[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public Vertex getMostDistantVertex(float[][] distanceMatrix, Vertex source)
    {
        var maxDistance = Float.NEGATIVE_INFINITY;
        var mostDistantVertexIndex = -1;
        var sourceVertexIndex = getVertexIndex(source);
        for (var i = 0; i < distanceMatrix[sourceVertexIndex].length; i++)
        {
            if(distanceMatrix[sourceVertexIndex][i] > maxDistance)
            {
                maxDistance = distanceMatrix[sourceVertexIndex][i];
                mostDistantVertexIndex = i;
            }
        }
        return vertices.get(mostDistantVertexIndex);
    }
}
