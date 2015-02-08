package gameLogic.goal;

import Util.Tuple;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class Goal {
	private Station origin;
	private Station destination;
	private int turnIssued;  //Use this value to check quantifiable goals
	private boolean complete = false;
	//constraints
	private String trainName = null;
	
	public Goal(Station origin, Station destination, int turn) {
		this.origin = origin;
		this.destination = destination;
		this.turnIssued = turn;
	}
	
	public void addConstraint(String name, String value) {
		if(name.equals("train")) {
			trainName = value;
		} else {
			throw new RuntimeException(name + " is not a valid goal constraint");
		}
	}

	public boolean isComplete(Train train) {
		boolean passedOrigin = false;
		for(Tuple<Station, Integer> history: train.getHistory()) {
			if(history.getFirst().getName().equals(origin.getName()) && history.getSecond() >=
					turnIssued) {
				passedOrigin = true;
			}
		}
		if(train.getFinalDestination() == destination && passedOrigin) {
			if(trainName == null || trainName.equals(train.getName())) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public String toString() {
		String trainString = "train";
		if(trainName != null) {
			trainString = trainName;
		}
		return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName();
	}

	public void setComplete() {
		complete = true;
	}

	public boolean getComplete() {
		return complete;
	}

	public Station getOrigin() { return this.origin; }

	public Station getDestination() { return this.destination; }
}