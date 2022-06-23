package com.rpg.exercicio_4_profundidade;
public class Vertex {
    private String name, description;
    
    public Vertex(String name){
        this.name = name;
        this.description = "";
    }
    
    public Vertex(String name, String description){
        this.name = name;
        this.description = description;
    }
    
    public String getName(){
        return name;
    }
    
    public String getDescription(){
        return description;
    }
}
