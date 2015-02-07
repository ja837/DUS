package gameLogic.dijkstra;
import gameLogic.map.Connection;
import gameLogic.map.Map;
import gameLogic.map.Station;

import java.util.PriorityQueue;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
public class Dijkstra
{
    ArrayList<Vertex> vertices;
    ArrayList<DijkstraData> dijkstras = new ArrayList<DijkstraData>();

    public void computePaths(Vertex source)
    {
        for (Vertex v: vertices){
            v.setMinDistance(Double.POSITIVE_INFINITY);
        }
        source.setMinDistance(0.);
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.getAdjacencies())
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.getMinDistance() + weight;
                if (distanceThroughU < v.getMinDistance()) {
                    vertexQueue.remove(v);
                    v.setMinDistance(distanceThroughU);
                    vertexQueue.add(v);
                }
            }
        }
    }


    public Dijkstra(Map map) {
        convertToVertices(map);
        for (Station s : map.getStations()) {
            addEdges(map, s);
        }

        for (Vertex vSource : vertices) {
            computePaths(vSource);
            for (Vertex vDestination : vertices) {
                DijkstraData tempDijkstra = new DijkstraData(vSource,vDestination,vDestination.getMinDistance());
                dijkstras.add(tempDijkstra);
            }
        }
    }
    private void convertToVertices(Map map){
        vertices = new ArrayList<Vertex>();
        for (Station s: map.getStations()){
            Vertex v = new Vertex(s.getName());
            vertices.add(v);
        }
    }
    private void addEdges(Map map,Station s1){
        for (Station s2: map.getStations()){
            if (map.doesConnectionExist(s1.getName(),s2.getName())){
                Edge edge = new Edge(findVertex(s2),map.getDistance(s1,s2));
                findVertex(s1).addAdjacency(edge);
            }
        }

    }
    private Vertex findVertex(Station s){
        for (Vertex v: vertices){
            if (v.getName()==s.getName()){
                return v;
            }
        }
        return null;
    }
    public double findMinDistance(Station s1,Station s2){
        for (DijkstraData d:dijkstras){
            if (d.getSource().getName().equals(s1.getName()) && d.getTarget().getName().equals(s2.getName())){
                return d.getDistance();
            }
        }
        return -1;
    }
}
