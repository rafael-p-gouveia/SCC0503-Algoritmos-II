/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package processoseletivo;

import Exercise_Bonus_B.Graph;
import Exercise_Bonus_B.GraphMatrix;
import java.util.ArrayList;

/**
 *
 * @author Dell
 */
public class CorridorFilter {
    private Graph dungeon,filteredDungeon;
    private int predecessorArray[];
    
    public CorridorFilter(Graph dungeon, int[] predecessorArray){
        this.dungeon = dungeon;
        this.predecessorArray = predecessorArray;
        filteredDungeon = new GraphMatrix(new ArrayList(dungeon.getVertices()));
    }
    
    private int getStartIndex(){
        for(int i = 0; i < dungeon.getNumberOfVertices(); i++){
            if (predecessorArray[i] == -1){
                return i;
            }
        }
        return -1;
    }
    
    public Graph getFilteredDungeon(){
        //int sourceIndex;
        
        //sourceIndex = getStartIndex();
        for(int i = 0; i < dungeon.getNumberOfVertices(); i++){
            for(int j = 0; j < dungeon.getNumberOfVertices(); j++)
                if(predecessorArray[j] == i){
                    filteredDungeon.addEdge(i, j, dungeon.getDistance(i, j));
                }
        }
        
        return filteredDungeon;
    }
}
