package gameLogic.dijkstra;

/**
 * Created by mattc_000 on 04/02/2015.
 */
class Edge
{
    public final Vertex target;
    public final double weight;
    public Edge(Vertex argTarget, double argWeight)
    { target = argTarget; weight = argWeight; }
}
