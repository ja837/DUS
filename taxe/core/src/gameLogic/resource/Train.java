package gameLogic.resource;

import Util.Tuple;
import fvs.taxe.actor.TrainActor;
import gameLogic.Game;
import gameLogic.map.IPositionable;
import gameLogic.map.Station;

import java.util.ArrayList;
import java.util.List;

public class Train extends Resource {
    private String leftImage;
    private String rightImage;
    private IPositionable position;
    private TrainActor actor;
    private int speed;
    private boolean replay = false;
    private boolean deployed = false;


	// Final destination should be set to null after firing the arrival event
    private Station finalDestination;

    // Should NOT contain current position!
    private List<Station> route;

    //Station name and turn number
    private List<Tuple<Station, Integer>> history;


    public Train(String name, String leftImage, String rightImage, int speed) {
        this.name = name;
        this.leftImage = leftImage;
        this.rightImage = rightImage;
        this.speed = speed;
        history = new ArrayList<Tuple<Station, Integer>>();
        route = new ArrayList<Station>();
    }
    
    public Train(Train t){
    	this.name = t.name;
    	this.leftImage = t.leftImage;
    	this.rightImage = t.rightImage;
    	this.speed = t.speed;
    	history = new ArrayList<Tuple<Station, Integer>>();
        route = new ArrayList<Station>();
    }

    public String getName() {
        return name;
    }
    
    public boolean isReplay() {
		return replay;
	}

	public void setReplay(boolean replay) {
		this.replay = replay;
	}

	public boolean isDeployed() {
		return deployed;
	}

	public void setDeployed(boolean deployed) {
		this.deployed = deployed;
	}

    public String getLeftImage() {
        return "trains/" + leftImage;
    }

    public String getRightImage() {
        return "trains/" + rightImage;
    }

    public String getCursorImage() {
        return "trains/cursor/" + leftImage;
    }

    public void setPosition(IPositionable position) {
        this.position = position;
        changed();
    }

    public boolean routeContains(Station station) {
        //Returns whether or not the route contains the station passed to the method
        if (this.route.contains(station)) return true;
        return false;
    }

    public IPositionable getPosition() {
        return position;
    }

    public void setActor(TrainActor actor) {
        this.actor = actor;
    }

    public TrainActor getActor() {
        return actor;
    }

    public void setRoute(List<Station> route) {
        // Final destination should be set to null after firing the arrival event
        if (route != null && route.size() > 0) finalDestination = route.get(route.size() - 1);

        this.route = route;
    }

    public boolean isMoving() {
        return finalDestination != null;
    }

    public List<Station> getRoute() {
        return route;
    }

    public Station getFinalDestination() {
        return finalDestination;
    }

    public void setFinalDestination(Station station) {
        finalDestination = station;
    }

    public int getSpeed() {
        return speed;
    }

    //Station name and turn number
    public List<Tuple<Station, Integer>> getHistory() {
        return history;
    }

    //Station name and turn number
    public void addHistory(Station station, int turn) {
        history.add(new Tuple<Station, Integer>(station, turn));
    }

    @Override
    public void dispose() {
        if (actor != null) {
            actor.remove();
        }
    }

    public Station getLastStation() {
        //Returns the station that the train has most recently visited
        return this.history.get(history.size() - 1).getFirst();
    }

    public Station getNextStation() {
        //Returns the next station along the route
        Station last = getLastStation();
        for (int i = 0; i < route.size() - 1; i++) {
            Station station = route.get(i);
            if (last.getName().equals(station.getName())) {
                return route.get(i + 1);
            }
        }
        return null;
    }
}
