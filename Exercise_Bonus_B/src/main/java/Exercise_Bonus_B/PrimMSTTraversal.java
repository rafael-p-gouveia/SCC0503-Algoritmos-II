/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_Bonus_B;

/**
 *
 * @author Dell
 */
public class PrimMSTTraversal extends TraversalStrategy { //meio sus. Isso eh um dijkstra

    public PrimMSTTraversal(Graph graph) {
        super(graph);
    }

    @Override
    public void traverse(Vertex source) {
        int sourceIndex;
        
        sourceIndex = getGraph().getVertices().indexOf(source);
        setDistanceToVertex(sourceIndex, 0);
        setPredecessorVertexIndex(sourceIndex, -1);
        visitAllVertices();
        //printVisitTree();
    }

    private void visitAllVertices() {
        for (int i = 0; i < getGraph().getNumberOfVertices(); i++) {
            int closestVertexIndex = getClosestVertexIndex();
            if(closestVertexIndex != -1)
            {
                markAsVisited(closestVertexIndex);
                var closestVertex = getGraph().getVertices().get(closestVertexIndex);
                var adjacentVertex = getGraph().getFirstConnectedVertex(closestVertex);
                updateAdjacentVertices(closestVertexIndex, closestVertex, adjacentVertex);
            }
        }
    }

    private void updateAdjacentVertices(int closestVertexIndex, Vertex closestVertex, Vertex adjacentVertex) {
        while(adjacentVertex != null)
        {
            int adjacentVertexIndex = getGraph().getVertices().indexOf(adjacentVertex);
            if(isCloserAndNotVisited(closestVertexIndex, adjacentVertexIndex))
            {
                setDistanceToVertex(adjacentVertexIndex, getGraph().getDistance(closestVertex, adjacentVertex));
                setPredecessorVertexIndex(adjacentVertexIndex, closestVertexIndex);
            }
            adjacentVertex = getGraph().getNextConnectedVertex(closestVertex, adjacentVertex);
        }
    }

    private boolean isCloserAndNotVisited(int closestVertexIndex, int adjacentVertexIndex) {
        var adjacentVertex = getGraph().getVertices().get(adjacentVertexIndex);
        var closestVertex = getGraph().getVertices().get(closestVertexIndex);
        return (!hasVertexBeenVisited(adjacentVertexIndex) &&
                (getGraph().getDistance(closestVertex, adjacentVertex) <
                        getDistanceToVertex(adjacentVertexIndex)));
    }

    private int getClosestVertexIndex() {
        float minDistance = Float.POSITIVE_INFINITY;
        int minIndex = -1;
        for (int i = 0; i < getGraph().getNumberOfVertices(); i++) {
            if(!hasVertexBeenVisited(i) && getDistanceToVertex(i) < minDistance)
            {
                minDistance = getDistanceToVertex(i);
                minIndex = i;
            }
        }
        return minIndex;
    }
}

