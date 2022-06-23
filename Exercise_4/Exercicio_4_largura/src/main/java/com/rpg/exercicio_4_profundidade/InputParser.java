/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rpg.exercicio_4_profundidade;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class InputParser {
    //private File input;
    private Scanner scan;
    
    private Graph graph;
    private Vertex startingVertex;
    
    //Receives input from console
    public InputParser(){
        scan = new Scanner(System.in); 
    }
    
    
    
    
    public void parseInput(){
        int numberOfVertices, numberOfEdges, startingVertexIndex;
        Vertex newVertex;
        ArrayList<Vertex> vertices;
        int[] newEdgeVertices;
        
        vertices = new ArrayList<>();
        if(scan.hasNextInt()){
            //Receive the number of vertices that are being received from the input;
            numberOfVertices = scan.nextInt(); 
            scan.nextLine(); //Go to the next line after reading one integer;
            
            //Store the passed vertices in a list;
            for(int i = 0; i < numberOfVertices; i++){
                newVertex = parseVertexInput();
                if(newVertex != null){
                    vertices.add(newVertex);
                }
            } 
        }
        graph = new DigraphMatrix(vertices);
        
        //Receive the number of edges being received from input and get their respective source and destination
        //vertices indices and generates a Graph object with that data.
        if(scan.hasNextInt()){
            numberOfEdges = scan.nextInt();
            scan.nextLine(); 
            for(int i = 0; i < numberOfEdges; i++){
                newEdgeVertices = parseEdgeInput();
                graph.addEdge(newEdgeVertices[0], newEdgeVertices[1], 1);
            }
        }
        
        //Also gets from input the index of the vertex in wich the traversal
        //starts and store the respective Vertex object.
        if(scan.hasNextInt()){
            startingVertexIndex = scan.nextInt();
            startingVertex = graph.getVertices().get(startingVertexIndex);
        }
    }
    
    //Returns list of source and destination
    //vertices of the edge received from input. 
    private int[] parseEdgeInput(){
        int[] edgeIndices;
        
        edgeIndices = new int[2];
        if(scan.hasNextInt()){
            edgeIndices[0] = scan.nextInt();
        }
        if(scan.hasNextInt()){
            edgeIndices[1] = scan.nextInt();
        }
        scan.nextLine(); //to go to the next line after reading one integer;
        
        return edgeIndices;
    }
    
    //Returns Vertex object from input
    private Vertex parseVertexInput(){
        Vertex readVertex;
        String readStrings[];
        
        readStrings = new String[2];
        for(int i = 0; i < 2; i++){    
            if(scan.hasNextLine()){
                readStrings[i] = scan.nextLine();
            }
            else{
                return null;
            }
        }
        readVertex = new Vertex(readStrings[0], readStrings[1]);
        
        return readVertex;
    }
    
    public Graph getGraph(){
        return graph;
    }
    
    public Vertex getStartingVertex(){
        return startingVertex;
    }
}
