package gameLogic.dijkstra;

import java.util.ArrayList;

class Vertex implements Comparable<Vertex>
{
    public final String name;
    private ArrayList<Edge> adjacencies;
    public double minDistance = Double.POSITIVE_INFINITY;
    public Vertex previous;
    public Vertex(String argName) { name = argName; }
    public String toString() { return name; }
    public int compareTo(Vertex other) {
        return Double.compare(minDistance, other.minDistance);
    }
    public ArrayList<Edge> getAdjacencies(){
        return adjacencies;
    }
    public ArrayList<Edge> addAdjacency(Edge edge){
        this.adjacencies.add(edge);
    }
}