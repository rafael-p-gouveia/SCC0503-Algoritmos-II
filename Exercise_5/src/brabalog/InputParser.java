package brabalog;
import graph.AbstractGraph;
import graph.DigraphMatrix;
import java.util.Scanner;
import java.util.ArrayList;
import graph.Vertex;

/**
 * Class to parse the input.
 * @author Maxsuel F de Almeida
 * @author Rafael P. Golveia
 */
public class InputParser {
    private final Scanner scanner;
    private final String COMMA = ",";
    private final String COLON = ":";
    /**
     * Constructor of the class.
     */
    public InputParser() {
        scanner = new Scanner(System.in);
    }

    /**
     * Parses the vertices entered through the input.
     * Given the number of vertices to be entered, reads the
     * inputs and parses it into vertices.
     * @param numberOfVertices number of vertices to be read.
     * @return ArrayList list containing the parsed vertices.
     */
    public ArrayList<Vertex> parseVertices(int numberOfVertices) {
        ArrayList<Vertex> vertices = new ArrayList();
        String inputStr;
        double[] coord;

        for (int i = 0; i < numberOfVertices; i++) {
            inputStr = scanner.next();
            coord = parseLine(inputStr);

            vertices.add(new Vertex(coord[0], coord[1]));
        }

        return vertices;
    }

    /**
     * Parses the edges entered through the input.
     * Given the number of edges to be entered, reads the
     * inputs and parses it into edges.
     * @param numOfEdges number of edges to be read.
     * @param graph
     */
    public void parseEdges(int numOfEdges, AbstractGraph graph) {
        Vertex source = null, dest = null;
        String inputStr;
        double[][] coords;
        double distance;

        for (int i = 0; i < numOfEdges; i++) {
            inputStr = scanner.next();

            coords = parseLine(inputStr, true);

            for (Vertex v : graph.getVertices()) {
                if (v.getXCoord() == coords[0][0]
                        && v.getYCoord() == coords[0][1]) {
                    source = v;
                }
            }

            for (Vertex v : graph.getVertices()) {
                if (v.getXCoord() == coords[1][0]
                        && v.getYCoord() == coords[1][1]) {
                    dest = v;
                }
            }

            distance = distance(coords[0][0], coords[0][1],
                                coords[1][0], coords[1][1]);

            graph.addEdge(source, dest, (float)distance);
        }
    }

    /**
     * Create graph.
     * Creates a graph using the vertices and edges parsed from
     * the input.
     * @return AbstractGraph graph created.
     */
    public AbstractGraph createGraph() {
        AbstractGraph graph;
        int numOfVertices, numOfEdges;

        numOfVertices = scanner.nextInt();
        scanner.nextLine();
        graph = new DigraphMatrix(this.parseVertices(numOfVertices));

        numOfEdges = scanner.nextInt();
        scanner.nextLine();
        this.parseEdges(numOfEdges, graph);

        return graph;
    }

    /**
     * Parse a line read from input.
     * Given a line read from the input, parses it in
     * the form of double array of two positions.
     * @param line to be parsed.
     * @return double the coordinates read in the line.
     */
    public double[] parseLine(String line) {
        double[] coords;
        String[] strCoord;

        coords = new double[2];

        strCoord = line.split(COMMA);

        coords[0] = Double.parseDouble(strCoord[0]);
        coords[1] = Double.parseDouble(strCoord[1]);

        return coords;
    }

    /**
     * Parse a line corresponding to a edge read from the input.
     * @param line to be parsed.
     * @param isEdge verify if the line corresponds to an edge.
     * @return double the coordinates read in the line.
     */
    public double[][] parseLine(String line, boolean isEdge) {
        double [] coordHelper;
        double[][] coords;
        String[] strHelper;

        coords = new double[2][2];

        strHelper = line.split(COLON);

        coordHelper = parseLine(strHelper[0]);
        coords[0][0] = coordHelper[0];
        coords[0][1] = coordHelper[1];

        coordHelper = parseLine(strHelper[1]);
        coords[1][0] = coordHelper[0];
        coords[1][1] = coordHelper[1];

        return coords;
    }
    
    /**
     * Euclidean distance.
     * Given the coordinates of two vertex, returns the
     * euclidean distance between them.
     * @param xsource x coordinate of the source vertex.
     * @param ysource y coordinate of the source vertex.
     * @param xdest x coordinate of the destination vertex.
     * @param ydest y coordinate of the destination vertex.
     * @return euclidean distance between the two vertices.
     */
    double distance (double xsource, double ysource,
                     double xdest, double ydest) {
        double deltaX = xsource - xdest;
        double deltaY = ysource - ydest;
        double result = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        return result;
    }

}
