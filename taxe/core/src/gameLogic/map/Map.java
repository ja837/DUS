package gameLogic.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import gameLogic.dijkstra.Dijkstra;
import gameLogic.dijkstra.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Map {
    private List<Station> stations;
    private List<Connection> connections;
    private Random random = new Random();
    private Dijkstra dijkstra;
    private JSONImporter jsonImporter;

    public Map() {
        stations = new ArrayList<Station>();
        connections = new ArrayList<Connection>();

        //Imports all values from the JSON file using the JSONImporter
        jsonImporter = new JSONImporter(this);

        //Analyses the graph using Dijkstra's algorithm
        dijkstra = new Dijkstra(this);
    }

    public boolean doesConnectionExist(String stationName, String anotherStationName) {
        //Returns whether or not the connection exists by checking the two station names passed to it
        for (Connection connection : connections) {
            String s1 = connection.getStation1().getName();
            String s2 = connection.getStation2().getName();

            //Checks whether or not the connection has station 1 and station 2 in its attributes, if so returns true, if not returns false
            if (s1.equals(stationName) && s2.equals(anotherStationName)
                    || s1.equals(anotherStationName) && s2.equals(stationName)) {
                return true;
            }
        }

        return false;
    }

    public Connection getConnection(Station station1, Station station2) {
        //Returns the connection that connects station1 and station2 if it exists
        String stationName = station1.getName();
        String anotherStationName = station2.getName();

        //Iterates through every connection and checks them
        for (Connection connection : connections) {
            String s1 = connection.getStation1().getName();
            String s2 = connection.getStation2().getName();

            //Checks whether the connection is between station1 and station2 by comparing the start and end to their names
            if (s1.equals(stationName) && s2.equals(anotherStationName)
                    || s1.equals(anotherStationName) && s2.equals(stationName)) {
                return connection;
            }
        }

        return null;
    }


    public Station getRandomStation() {
        //Returns a random station
        return stations.get(random.nextInt(stations.size()));
    }

    public Station addStation(String name, Position location) {
        Station newStation = new Station(name, location);
        stations.add(newStation);
        return newStation;
    }
    
    public CollisionStation addJunction(String name, Position location) {
    	CollisionStation newJunction = new CollisionStation(name, location);
    	stations.add(newJunction);
    	return newJunction;
    }

    public List<Station> getStations() {
        return stations;
    }

    public List<Connection> getConnections() {
        return connections;
    }

    public Connection addConnection(Station station1, Station station2) {
        Connection newConnection = new Connection(station1, station2);
        connections.add(newConnection);
        return newConnection;
    }

    //Add Connection by Names
    public Connection addConnection(String station1, String station2) {
        Station st1 = getStationByName(station1);
        Station st2 = getStationByName(station2);
        return addConnection(st1, st2);
    }


    public Station getStationByName(String name) {
        int i = 0;
        while(i < stations.size()) {
            if(stations.get(i).getName().equals(name)) {
                return stations.get(i);
            } else{
                i++;
            }
        }
        return null;
    }

    public Station getStationFromPosition(IPositionable position) {
        for (Station station : stations) {
            if (station.getLocation().equals(position)) {
                return station;
            }
        }

        throw new RuntimeException("Station does not exist for that position");
    }

    public List<Station> createRoute(List<IPositionable> positions) {
        List<Station> route = new ArrayList<Station>();

        for (IPositionable position : positions) {
            route.add(getStationFromPosition(position));
        }

        return route;
    }

    public void decrementBlockedConnections() {
        for (Connection connection : connections) {
            connection.decrementBlocked();
        }
    }

    public Connection getRandomConnection(){
        int index = random.nextInt(connections.size());
        return connections.get(index);
    }

    public void blockRandomConnection(){
        int rand = random.nextInt(2);
        if (rand > 0) { //50% chance of connection being blocked

            Connection toBlock = getRandomConnection();
            toBlock.setBlocked(4);
            System.out.println("Connection blocked: " + toBlock.getStation1().getName() + " to " + toBlock.getStation2().getName());

            //connections.get(0).setBlocked(1); //UNCOMMENT FOR TEST OF 50% CHANCE TO BLOCK PARIS-MADRID CONNECTION
        }

    }

    public void blockConnection(Station station1, Station station2, int turnsBlocked){
        if (doesConnectionExist(station1.getName(),station2.getName())){
            Connection connection = getConnection(station1, station2);
            connection.setBlocked(turnsBlocked);
        }
    }

    public boolean isConnectionBlocked(Station station1, Station station2) {
        for (Connection connection : connections){
            if(connection.getStation1() == station1)
                if(connection.getStation2() == station2)
                    return connection.isBlocked();
            if(connection.getStation1() == station2)
                if(connection.getStation2() == station1)
                    return connection.isBlocked();
        }
        //Reaching here means a connection has been added to the route where a connection doesn't exist
        return true;
    }

    public ArrayList<Connection> getBlockedConnections(){
        ArrayList<Connection> blockedConnections = new ArrayList<Connection>();
        for (Connection connection : this.getConnections()){
            if (connection.isBlocked()){
                blockedConnections.add(connection);
            }
        }
        return blockedConnections;
    }

    public float getDistance (Station s1, Station s2) {
        return Vector2.dst(s1.getLocation().getX(), s1.getLocation().getY(), s2.getLocation().getX(), s2.getLocation().getY());
    }
    public double getShortestDistance(Station s1, Station s2){
        return dijkstra.findMinDistance(s1, s2);
    }

    public boolean inShortestPath(Station s1, Station s2,Station s3){
        return dijkstra.inShortestPath(s1,s2,s3);
    }
}