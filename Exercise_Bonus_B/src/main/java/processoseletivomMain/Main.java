/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processoseletivomMain;

import processoseletivo.CorridorFilter;
import Exercise_Bonus_B.BreadthFirstTraversal;
import Exercise_Bonus_B.DelaunayTriangulation;
import Exercise_Bonus_B.DungeonGraphic;
import Exercise_Bonus_B.RandomSingleton;
import Exercise_Bonus_B.RandomDungeonGenerator;
import Exercise_Bonus_B.PrimMSTTraversal;
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
        int seed, nRooms;
        PrimMSTTraversal traversal;
        CorridorFilter filter;
        BreadthFirstTraversal BFT;
        
        scan = new Scanner(System.in);
        seed = scan.nextInt();
        nRooms = scan.nextInt();
        scan.close();
        
        RandomSingleton.getInstance(seed); //Initialize random number generator
        var randomDungeonGenerator = new RandomDungeonGenerator(nRooms); //Generate random rooms (vertices)
        randomDungeonGenerator.generateDungeon(); //Generate the dungeon (graph) itself
        var dungeon = randomDungeonGenerator.getDungeon();
        DelaunayTriangulation.triangulateGraphVertices(dungeon); //Create room connections (edges)
        
        //get a dungeon with a small number of corridors (minimum spanning tree)
        traversal = new PrimMSTTraversal(dungeon);
        traversal.traverse(dungeon.getVertices().get(0));
        var antecessores = traversal.getPredecessorsArray(); 
        filter = new CorridorFilter(dungeon, antecessores);
        var newDungeon = filter.getFilteredDungeon(); 
        
        //SwingUtilities.invokeLater(() -> new DungeonGraphic(newDungeon).setVisible(true));
        
        //get and print the traversal of the dungeon, starting from the first room created
        BFT = new BreadthFirstTraversal(newDungeon);
        BFT.traverse(newDungeon.getVertices().get(0));
        BFT.printPath();
        
        
        
    }
    
}
