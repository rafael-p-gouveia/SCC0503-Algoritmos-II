/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rpg.exercicio_4_profundidade;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/******************************************************************************
 * Author: Rafael Pereira de Gouveia                                          *
 * NUSP: 11800820                                                             *
 *                                                                            *
 * Author: Maxsuel Fernandes de Almeida                                       *
 * NUSP: 11801028                                                             *
 *                                                                            *
 ******************************************************************************/

public class NewMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Logger l = new Logger.getLogger("Hello world?");
        
        InputParser input;
        Graph quests;
        Vertex startingQuest;
        TraversalStrategy route;
        
        input = new InputParser();
        input.parseInput();
        quests = input.getGraph();
        startingQuest = input.getStartingVertex();
        route = new DepthFirstTraversal(quests);
        route.traverse(startingQuest);
        route.printPath();
        
        
    }
    
}
