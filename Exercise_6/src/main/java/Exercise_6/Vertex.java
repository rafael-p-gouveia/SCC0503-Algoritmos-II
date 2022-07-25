package Exercise_6;
public class Vertex {
    private String name;
    // For A* algorythm, in order to make comparison using the priorityQueue, it's necessary that these scores are kept inside the class
    public double gScores = 0;
    public final double hScores = 0;
    public double fScores = 0;
    // For a* alorythm, it's necessary in order to find the most optimal path
    public Vertex parent = null;
    
    public Vertex(){
    }
    
    public Vertex(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }

}
