package gameLogic.resource;

import gameLogic.map.Connection;
import gameLogic.map.Map;



public class Obstacle extends Resource {


    private Map map = new Map();
    private Connection affectedConnection = map.getRandomConnection();
    private int forTurns;

    public Obstacle(int forTurns)
    {
        this.name="Roadblock";
        this.forTurns=forTurns;
        this.affectedConnection.setBlocked(forTurns);

    }

    private int getForTurns()
    {
        return this.forTurns;
    }

    private void setForTurns(int turns)
    {
        this.forTurns=turns;
    }

    private Connection getAffectedConnection()
    {
        return affectedConnection;
    }





    @Override
    public void dispose() {

    }
}
