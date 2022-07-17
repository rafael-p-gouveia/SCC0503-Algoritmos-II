package graph;

import java.util.Objects;

public class Vertex
{
    private String name;
    private double xCoord;
    private double yCoord;
    
    public int getInDegree()
    {
        return inDegree;
    }

    public int getOutDegree()
    {
        return outDegree;
    }

    public void setInDegree(int inDegree)
    {
        this.inDegree = inDegree;
    }

    public void setOutDegree(int outDegree)
    {
        this.outDegree = outDegree;
    }

    private int inDegree;
    private int outDegree;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(Objects.equals(name, ""))
        {
            System.out.println("Nome vazio não será adicionado");
        }
        this.name = name;
    }
    
    public double getXCoord() {
        return xCoord;
    }
    
    public void setXCoord(double xCoord) {
        this.xCoord = xCoord;
    }
    
    public double getYCoord() {
        return yCoord;
    }
    
    public void setYCoord(double yCoord) {
        this.yCoord = yCoord;
    }
    
    public Vertex(String name, double xCoord, double yCoord) {
        this.name = name;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        inDegree = 0;
        outDegree = 0;
        
    }
    
    public Vertex(double xCoord, double yCoord) {
        name = "(" + xCoord + "," + yCoord + ")";
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        inDegree = 0;
        outDegree = 0;
        
    }
    public void incrementInDegree()
    {
        inDegree++;
    }

    public void incrementOutDegree()
    {
        outDegree++;
    }

    public void decrementInDegree()
    {
        inDegree--;
    }

    public void decrementOutDegree()
    {
        outDegree--;
    }

    @Override
    public String toString()
    {
        return "Vertex{" + "name='" + name + '\'' + '}';
    }


}
