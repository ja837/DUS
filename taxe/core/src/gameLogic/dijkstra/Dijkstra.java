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
    public static void computePaths(Vertex source)
    {
        source.minDistance = 0.;
        PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
        vertexQueue.add(source);

        while (!vertexQueue.isEmpty()) {
            Vertex u = vertexQueue.poll();

            // Visit each edge exiting u
            for (Edge e : u.adjacencies)
            {
                Vertex v = e.target;
                double weight = e.weight;
                double distanceThroughU = u.minDistance + weight;
                if (distanceThroughU < v.minDistance) {
                    vertexQueue.remove(v);
                    v.minDistance = distanceThroughU ;
                    v.previous = u;
                    vertexQueue.add(v);
                }
            }
        }
    }

    public static List<Vertex> getShortestPathTo(Vertex target)
    {
        List<Vertex> path = new ArrayList<Vertex>();
        for (Vertex vertex = target; vertex != null; vertex = vertex.previous)
            path.add(vertex);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args)
    {
        Vertex v0 = new Vertex("Redvile");
        Vertex v1 = new Vertex("Blueville");
        Vertex v2 = new Vertex("Greenville");
        Vertex v3 = new Vertex("Orangeville");
        Vertex v4 = new Vertex("Purpleville");

        v0.adjacencies = new Edge[]{ new Edge(v1, 5),
                new Edge(v2, 10),
                new Edge(v3, 8) };
        v1.adjacencies = new Edge[]{ new Edge(v0, 5),
                new Edge(v2, 3),
                new Edge(v4, 7) };
        v2.adjacencies = new Edge[]{ new Edge(v0, 10),
                new Edge(v1, 3) };
        v3.adjacencies = new Edge[]{ new Edge(v0, 8),
                new Edge(v4, 2) };
        v4.adjacencies = new Edge[]{ new Edge(v1, 7),
                new Edge(v3, 2) };
        Vertex[] vertices = { v0, v1, v2, v3, v4 };
        computePaths(v0);
        for (Vertex v : vertices)
        {
            System.out.println("Distance to " + v + ": " + v.minDistance);
            List<Vertex> path = getShortestPathTo(v);
            System.out.println("Path: " + path);
        }
    }
    private static void convertToVertices(Map map){
        ArrayList<Vertex> vertices = new ArrayList<Vertex>();
        for (Station s: map.getStations()){
            Vertex v = new Vertex(s.getName());
        }
        for (Vertex v1:vertices){
            for (Connection c: map.getConnectionsFromStation(s)){
                Edge e;
                if (v1.name==c.getStation1().getName()){
                        int vIndex=0;
                        for (Vertex v2: vertices){
                            if (v2.name == c.getStation2().getName()){
                                vIndex = vertices.indexOf(v2);
                                break;
                            }
                        }
                        e = new Edge(vertices.get(vIndex),0);
                }else{
                    int vIndex=0;
                    for (Vertex v2: vertices){
                        if (v2.name == c.getStation1().getName()){
                            vIndex = vertices.indexOf(v2);
                            break;
                        }
                    }
                    e = new Edge(vertices.get(vIndex),0);
                }

            }
        }
    }

}
