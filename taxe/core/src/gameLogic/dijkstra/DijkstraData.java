package gameLogic.dijkstra;

import java.util.ArrayList;

public class DijkstraData {
    private Vertex source;
    private Vertex target;
    private double distance;
    private ArrayList<Vertex> shortestPath;

    public DijkstraData(Vertex source, Vertex target,double distance, ArrayList<Vertex> shortestPath){
        this.source = source;
        this.target = target;
        this.distance = distance;
        this.shortestPath = shortestPath;
    }

    public Vertex getSource() {
        return source;
    }

    public Vertex getTarget() {
        return target;
    }

    public double getDistance() {
        return distance;
    }
    public boolean inShortestPath(String stationName){
        for (Vertex v: shortestPath){
            if (v.getName().equals(stationName)){
                return true;
            }
        }
        return false;
    }
    public String toString(){
        return source.getName() + " to " + target.getName() + ": " + distance;
    }
}
