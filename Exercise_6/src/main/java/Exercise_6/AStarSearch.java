/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class AStarSearch extends TraversalStrategy{

    Queue<Vertex> verticesToVisit;
    
    public AStarSearch(Graph graph){
        super(graph);
        
    }
    // Actual Distance g(x)
    public double manhattanDistanceCalculate(Room actualRoom, Room goal) {
        Point actualPoint = actualRoom.getPoint();
        Point goalPoint = goal.getPoint();
        return Math.abs(actualPoint.x - goalPoint.x) + Math.abs(actualPoint.y - goalPoint.y);
    }
    // Heuristic h(x)
    public double euclidianDistanceCalculate(Room actualRoom, Room goal) {
        Point actualPoint = actualRoom.getPoint();
        Point goalPoint = goal.getPoint();
        return Math.sqrt((actualPoint.x - goalPoint.x)^2 + (actualPoint.y - goalPoint.y)^2);
    }
    
    // Getting neighbours as a list
    public List<Vertex> getAllNeighbours(Vertex node) {
        List<Vertex> verticesConnected = new LinkedList<>();
        int indexVertex = this.getGraph().getVertexIndex(node);
        Stack<Vertex> stackConnected = this.getGraph().getConnectedVertices(indexVertex);
        while(!stackConnected.empty()){
            Vertex aux = stackConnected.pop();
            verticesConnected.add(aux);
        }
        return verticesConnected;
    }
    
    public void addPathToList(Vertex end) {
        Vertex aux = end;
        Stack<Vertex> s = new Stack<>();
        while(aux != null) {
            s.add(aux);
            aux = aux.parent;
        }
        
        
        while(s.size() > 0) {
            aux = s.pop();
            visitVertex(aux);
        }
    }
    
    // Normal transverse (transverse whole graph)
    @Override
    public void traverse(Vertex start){
        System.out.print("Class A* needs destination vertex");
    }
    
    // Tranverse from start to end location
    @Override
    public void traverse(Vertex start, Vertex end){
        PriorityQueue<Vertex> exploredVertices = new PriorityQueue<Vertex>(new Comparator<Vertex>(){
                                 //override compare method
                                @Override
                                public int compare(Vertex i, Vertex j) {
                                    if(i.fScores > j.fScores){
                                        return 1;
                                    }
                                    else if (i.fScores < j.fScores){
                                            return -1;
                                        } else {
                                            return 0;
                                        }
                                    }
                            });
        verticesToVisit = new PriorityQueue<Vertex>( 
                            new Comparator<Vertex>(){
                                 //override compare method
                                @Override
                                public int compare(Vertex i, Vertex j) {
                                    if(i.fScores > j.fScores){
                                        return 1;
                                    }
                                    else if (i.fScores < j.fScores){
                                            return -1;
                                        } else {
                                            return 0;
                                        }
                                    }
                            });
        boolean isEndVertexFound = false;

        verticesToVisit.add(start);
        
        while(!verticesToVisit.isEmpty() && !isEndVertexFound) {
            Room current = (Room)verticesToVisit.poll();
            exploredVertices.add(current);

            // In case Target is found
            if(current.getName().equals(end.getName())){                
                isEndVertexFound = true;
                addPathToList(end);
                return;
            }
            // Checking for all neighbours of current node
            List<Vertex> neighbourVertices = getAllNeighbours(current);
            for(Vertex neighbourVertex: neighbourVertices){
                Room neighbour = (Room)neighbourVertex;
                double weightValue = neighbour.gScores 
                                        + manhattanDistanceCalculate(current, neighbour) 
                                        + euclidianDistanceCalculate(current, neighbour);
                if(!verticesToVisit.contains(neighbour) && !exploredVertices.contains(neighbour)) {
                    neighbour.parent = current;
                    neighbour.gScores = weightValue;
                    neighbour.fScores = weightValue + euclidianDistanceCalculate(neighbour, (Room)end);
                    verticesToVisit.add(neighbour);
                } else {
                    if(weightValue < neighbour.gScores) {
                        neighbour.parent = current;
                        neighbour.gScores = weightValue;
                        neighbour.fScores = weightValue + euclidianDistanceCalculate(neighbour, (Room)end);
                        
                        if(exploredVertices.contains(neighbour)) {
                            exploredVertices.remove(neighbour);
                            verticesToVisit.add(neighbour);
                        }
                    }
                }
            }   
        }
    }
}
