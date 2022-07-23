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

import java.awt.*;
import static java.lang.Math.sqrt;

public class Room extends Vertex {
    Rectangle rectangle;
    boolean isCheckpoint, isEntrance, isExit;
    
    public Room(Rectangle rectangle){
        super("("+rectangle.getX()+","+rectangle.getY()+")");
        this.rectangle = rectangle;
    }

    @Override
    public String toString(){
        return "Room{" +
            "(X, Y)= (" + rectangle.getX() + ", " + rectangle.getY() + ")"+
            '}';
    }
    
    public Point getPoint()
    {
        return new Point((int)rectangle.getX(), (int)rectangle.getY());
    }
    
    public double getDistance(Point point){
        return rectangle.getLocation().distance(point);
        //return Math.sqrt(Math.pow((point.getX() - rectangle.getX()),2) + Math.pow((point.getY() - rectangle.getY()),2));
    }
    
    public Rectangle getRectangle(){
        return rectangle;
    }

    public void setDefault(){
        isEntrance = false;
        isCheckpoint = false;
        isExit = false;
    }
    
    public void setCheckpoint(boolean isCheckpoint) {
        this.isCheckpoint = isCheckpoint;
    }

    public void setEntrance(boolean isEntrance) {
        this.isEntrance = isEntrance;
    }

    public void setExit(boolean isExit) {
        this.isExit = isExit;
    }
    
    public boolean isCheckpoint() {
        return isCheckpoint;
    }

    public boolean isEntrance() {
        return isEntrance;
    }

    public boolean isExit() {
        return isExit;
    }
    
    
    
}
