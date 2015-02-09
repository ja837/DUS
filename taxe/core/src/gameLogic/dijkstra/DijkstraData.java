package gameLogic.dijkstra;

public class DijkstraData {
    private Vertex source;
    private Vertex target;
    private double distance;

    public DijkstraData(Vertex source, Vertex target,double distance){
        this.source = source;
        this.target = target;
        this.distance = distance;
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
    public String toString(){
        return source.getName() + " to " + target.getName() + ": " + distance;
    }
}
