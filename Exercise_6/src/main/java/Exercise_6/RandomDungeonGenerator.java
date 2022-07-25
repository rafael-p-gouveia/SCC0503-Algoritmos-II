/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

import java.awt.*;
import java.util.ArrayList;

public class RandomDungeonGenerator {
    
    private Graph dungeon;
    private int numberOfRooms;
    private static RandomSingleton randomNumberGenerator = RandomSingleton.getInstance();
    
    private static final int ROOM_MAX_WIDTH = 100;
    private static final int ROOM_MIN_WIDTH = 40;
    private static final int ROOM_MAX_HEIGHT = 100;
    private static final int ROOM_MIN_HEIGHT = 40;
    private static final int ROOM_X_BOUNDS = 800;
    private static final int ROOM_Y_BOUNDS = 800;
    
    public RandomDungeonGenerator(int numberOfRooms){
        this.numberOfRooms = numberOfRooms;
    }
    
    public void generateDungeon(){
        ArrayList<Vertex> vertices;
        //GraphMatrix dungeon = new GraphMatrix();
        
        vertices = new <Vertex>ArrayList(); 
        for(int i = 0; i < numberOfRooms; i++){
            boolean isRoomValid;
            Rectangle newRectangle;
            
            do{
                isRoomValid = true;
                newRectangle = generateRandomRectangle();
                for(int j = 0; j < vertices.size(); j++){
                    if(newRectangle.intersects(((Room)vertices.get(j)).getRectangle())){
                        isRoomValid = false;
                    }
                }
            }while(!isRoomValid);
            vertices.add(new Room(newRectangle));
        }
        dungeon = new GraphMatrix(vertices);
    }
    
    private Rectangle generateRandomRectangle(){
        int height, width, x, y; 
        
        height = randomNumberGenerator.nextInt(ROOM_MIN_HEIGHT,ROOM_MAX_HEIGHT);
        width = randomNumberGenerator.nextInt(ROOM_MIN_WIDTH,ROOM_MAX_WIDTH);
        x = randomNumberGenerator.nextInt(ROOM_X_BOUNDS-width);
        y = randomNumberGenerator.nextInt(ROOM_Y_BOUNDS-height);
        
        return new Rectangle(x,y,width,height);
    }
    
    public Graph getDungeon(){
        return dungeon;
    }
    
    public void setNumberOfRooms(int numberOfRooms){
        this.numberOfRooms = numberOfRooms;
    }
}
