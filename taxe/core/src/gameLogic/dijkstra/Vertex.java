package gameLogic.dijkstra;

import java.util.ArrayList;

class Vertex implements Comparable<Vertex>
{
    private final String name;
    private ArrayList<Edge> adjacencies = new ArrayList<Edge>();
    private double minDistance = Double.POSITIVE_INFINITY;
    public Vertex(String argName) { name = argName; }
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

    public double getMinDistance() {
        return minDistance;
    }

    public void setMinDistance(double minDistance) {
        this.minDistance = minDistance;
    }
}