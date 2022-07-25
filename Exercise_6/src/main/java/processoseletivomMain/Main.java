/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processoseletivomMain;

import processoseletivo.CorridorFilter;
import Exercise_6.*;
import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.swing.SwingUtilities;

import java.util.Arrays;

/******************************************************************************
 * Author: Rafael Pereira de Gouveia                                          *
 * NUSP: 11800820                                                             *
 *                                                                            *
 * Author: Maxsuel Fernandes de Almeida                                       *
 * NUSP: 11801028                                                             *
 *                                                                            *
 ******************************************************************************/
import java.util.Scanner;
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Scanner scan;
        int seed, nRooms, nKeys;
        PrimMSTTraversal traversal;
        CorridorFilter filter;
        BreadthFirstTraversal BFT;
        DepthFirstTraversal DFT;
        AStarSearch AStar;
        
        FloydWarshallTraversal floydWarshall;
        float distanceMatrix[][];
        
        Room checkpointRoom, entranceRoom, exitRoom;
        
        scan = new Scanner(System.in);
        seed = scan.nextInt();
        nRooms = scan.nextInt();
        nKeys = scan.nextInt();
        scan.close();
        
        RandomSingleton.getInstance(seed); //Initialize random number generator
        var randomDungeonGenerator = new RandomDungeonGenerator(nRooms); //Generate random rooms (vertices)
        randomDungeonGenerator.generateDungeon(); //Generate the dungeon (graph) itself
        var dungeon = randomDungeonGenerator.getDungeon();
        DelaunayTriangulation.triangulateGraphVertices(dungeon); //Create room connections (edges)
        
        //get a dungeon with a small number of corridors (minimum spanning tree)
        traversal = new PrimMSTTraversal(dungeon);
        traversal.traverse(dungeon.getVertices().get(0));
        //traversal.printVisitTree();
        var antecessores = traversal.getPredecessorsArray(); 
        filter = new CorridorFilter(dungeon, antecessores);
        var newDungeon = filter.getFilteredDungeon(); 
        
       
        
        
        floydWarshall = new FloydWarshallTraversal(newDungeon);
        floydWarshall.traverse(newDungeon.getVertices().get(0));
        distanceMatrix = floydWarshall.getDistanceMatrix();
        
        checkpointRoom = (Room)newDungeon.getCentermostVertex(distanceMatrix);
        entranceRoom = (Room)newDungeon.getOuterMostVertex(distanceMatrix);
        exitRoom = (Room)newDungeon.getMostDistantVertex(distanceMatrix, entranceRoom);
        
        //System.out.println(((Room)(newDungeon.getVertices().get(newDungeon.getVertexIndex(exitRoom)))).isExit());
        
        checkpointRoom.setCheckpoint(true);
        entranceRoom.setEntrance(true);
        exitRoom.setExit(true);
        
        //System.out.println(((Room)(newDungeon.getVertices().get(newDungeon.getVertexIndex(exitRoom)))).isExit());
        
        //get and print the traversal of the dungeon, starting from the first room created
        
        
        /*
        for(int i = 0; i < newDungeon.getVertices().size(); i++){
            System.out.println(((Room)newDungeon.getVertices().get(i)).isKey());
        }
        */
        
        System.out.println();
        var keys = new BreadthKeyDistributor(newDungeon);
        keys.insertKeys(nKeys);
        
        /*
        for(int i = 0; i < newDungeon.getVertices().size(); i++){
            System.out.println(((Room)newDungeon.getVertices().get(i)).isLock());
            System.out.println(((Room)newDungeon.getVertices().get(i)).isCheckpoint());
            System.out.println();
        }*/
        
        /*
        for(int i = 0; i<layerSize.size(); i++){
            System.out.println(layerSize.get(i));
        }
        */
        
        System.out.println("---Busca em largura---");
        BFT = new BreadthFirstTraversal(newDungeon);
        BFT.traverse(entranceRoom);
        BFT.printPath();
        
        System.out.println("---Busca em profundidade---");
        DFT = new DepthFirstTraversal(newDungeon);
        DFT.traverse(entranceRoom);
        DFT.printPath();
        
        System.out.println("---Busca em A*---");
        AStar = new AStarSearch(newDungeon);
        AStar.traverse(entranceRoom, exitRoom);
        AStar.printPath();
        
        
        SwingUtilities.invokeLater(() -> new DungeonGraphic(newDungeon).setVisible(true)); //visualize dungeon
        
        
    }
    
}
