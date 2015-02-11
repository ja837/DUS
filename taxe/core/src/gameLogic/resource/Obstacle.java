package gameLogic.resource;

import gameLogic.map.Connection;



public class Obstacle extends Resource {

    private int forTurns;

    public Obstacle()
    {
        this.name="Roadblock";
        this.forTurns=5;
    }

    public void use(Connection connection)
    {
        connection.setBlocked(this.forTurns);
    }



    @Override
    public void dispose() {

    }
}