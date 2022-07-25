/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

import static java.lang.Math.floor;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author Dell
 */
public class BreadthKeyDistributor extends TraversalStrategy{
    Queue<Vertex> verticesToVisit;
    int nKeys;
    private static RandomSingleton randomNumberGenerator = RandomSingleton.getInstance();
    List<Integer> layerSize;
    int vertexLayers[];
    Room startingRoom;
    
    
    public BreadthKeyDistributor(Graph graph){
        super(graph);
        vertexLayers = new int[getGraph().getVertices().size()];
        findStartingRoom();
        
    }
    
    private void findStartingRoom(){
        Room startingRoomCandidate;
        
        var vertices = graph.getVertices();
        startingRoomCandidate = (Room)vertices.get(0);
        for(int i = 0; i < vertices.size(); i++){
            startingRoomCandidate = (Room)vertices.get(i);
            if(startingRoomCandidate.isEntrance()){
                startingRoom = startingRoomCandidate;
                break;
            }
        }
    }

    public Room getStartingRoom() {
        return startingRoom;
    }
    
    public void insertKeys(int nKeys){
        this.nKeys = nKeys;
        
        int keysRemaining = nKeys;
        
        
        
        traverse(startingRoom);
        
        for(int i = 0; i < layerSize.size(); i++){

            var ammountOfRooms = layerSize.get(i);
            if(ammountOfRooms > 1){
                var keysInLayer = randomNumberGenerator.nextInt(1,(int)floor(ammountOfRooms/2)+1);
                //System.out.println(keysInLayer);
                if(keysInLayer > keysRemaining){
                    keysInLayer = keysRemaining;
                }
                keysRemaining = keysRemaining - keysInLayer;
                List<Integer> ordinalsUsedLayer = new ArrayList<Integer>();
                for(int j = 0; j < keysInLayer; j++){
                    while(true){
                        var ordinalVertexWithKey = randomNumberGenerator.nextInt(ammountOfRooms);
                        if(!ordinalsUsedLayer.contains(ordinalVertexWithKey)){
                            addKeyLock(i,ordinalVertexWithKey,0);
                            ordinalsUsedLayer.add(ordinalVertexWithKey);
                            break;
                        }
                    }
                }
                for(int j = 0; j < keysInLayer; j++){
                    while(true){
                        var ordinalVertexWithLock = randomNumberGenerator.nextInt(ammountOfRooms);
                        if(!ordinalsUsedLayer.contains(ordinalVertexWithLock)){
                            addKeyLock(i,ordinalVertexWithLock,1);
                            ordinalsUsedLayer.add(ordinalVertexWithLock);
                            break;
                        }
                    }
                }
            }
            if(keysRemaining == 0){
                break;
            }
        }
    }
    
    public boolean addKeyLock(int layer, int ordinalVertex, int keyOrLock){
        int counter = 0;
        
        for(int i = 0; i < getGraph().getNumberOfVertices(); i++){
            if(vertexLayers[i] == layer){
                if(counter == ordinalVertex){
                    if(keyOrLock == 0){
                        ((Room)getGraph().getVertices().get(i)).setKey(true);
                        return true;
                    }
                    else{
                        ((Room)getGraph().getVertices().get(i)).setLock(true);
                        return true;
                    }
                    
                }
                counter++;
            }
        }
        return false;
    }
    
    public List<Integer> getLayerSize(){
        return layerSize;
    }
    
    @Override
    public void traverse(Vertex start){
        verticesToVisit = new <Vertex>LinkedList();
      
        layerSize = new ArrayList<Integer>();
        int currentLayer = -1, nToVisitInLayer = 0;
        
        
        
        var startIndex = getGraph().getVertexIndex(start);
        setDistanceToVertex(startIndex,0);
        verticesToVisit.add(start);
        
        layerSize.add(1);
        vertexLayers[startIndex] = 0;
        
        while(!verticesToVisit.isEmpty()){
            Vertex candidate;
            int candidateIndex;
            
            if(nToVisitInLayer == 0){
                currentLayer++;
                nToVisitInLayer = layerSize.get(currentLayer);
                layerSize.add(0);
                
                
            }
            
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
                        layerSize.set(currentLayer + 1, layerSize.get(currentLayer + 1) + 1);
                        
                        vertexLayers[neighborIndex] = currentLayer + 1; 
                        
                        float distanceToNeighbor = getGraph().getDistance(candidateIndex, neighborIndex);
                        setDistanceToVertex(neighborIndex,getDistanceToVertex(candidateIndex) + distanceToNeighbor);
                        
                        verticesToVisit.add(neighborVertex);
                    }  
                }
            }
            nToVisitInLayer--;
        }
        //layerSize.remove(0);
    }

    @Override
    public void traverse(Vertex start, Vertex end) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
