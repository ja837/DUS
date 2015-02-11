package gameLogic.dijkstra;

import java.util.ArrayList;

public class Vertex implements Comparable<Vertex>
{
    private final String name;
    private ArrayList<Edge> adjacencies = new ArrayList<Edge>();
    private double minDistance = Double.POSITIVE_INFINITY;
    private Vertex previous;
    public Vertex(String argName) { name = argName;
    this.previous = null;}
    public String toString() { return name; }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
    public ArrayList<Edge> getAdjacencies(){
        return adjacencies;
    }
    public void addAdjacency(Edge edge){
        this.adjacencies.add(edge);
    }
    public String getName(){
        return this.name;
    }

    public void setPrevious(Vertex previous) {
        this.previous = previous;
    }

    public Vertex getPrevious() {
        return previous;
    }

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }
}