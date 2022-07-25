/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exercise_6;

/**
 *
 * @author Dell
 */

import java.awt.*;
import static java.lang.Math.sqrt;

public class Room extends Vertex {
    Rectangle rectangle;
    boolean isCheckpoint, isEntrance, isExit,isKey, isLock;
    
    public Room(Rectangle rectangle){
        super("("+rectangle.getX()+","+rectangle.getY()+")");
        setCheckpoint(false);
        setEntrance(false);
        setExit(false);
        setKey(false);
        this.rectangle = rectangle;
    }

    @Override
    public String toString(){
        String retval;
        boolean flag = false;
        
        retval = "Room{" +
            "(X, Y)= (" + rectangle.getX() + ", " + rectangle.getY() + ")"+
            '}';
        if(isCheckpoint()){
            retval = retval + " Checkpoint";
            flag = true;
        }
        if(isEntrance()){
            retval = retval + " Entrance";
            flag = true;
        }
        if(isExit()){
            retval = retval + " Exit";
            flag = true;
        }
        if(isKey()){
            retval = retval + " Key";
            flag = true;
        }
        if(isLock()){
            retval = retval + " Lock";
            flag = true;
        }
        if(!flag){
            retval = retval + "Common";
        }
        return retval;
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

    public void setKey(boolean isKey) {
        this.isKey = isKey;
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

    public boolean isKey() {
        return isKey;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean isLock) {
        this.isLock = isLock;
    }
    
    
}
