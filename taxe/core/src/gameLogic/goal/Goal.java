package gameLogic.goal;

import Util.Tuple;
import gameLogic.map.Station;
import gameLogic.resource.Train;

public class Goal {//hobitses
	private Station origin;
	private Station destination;
	private int turnIssued;  //Use this value to check quantifiable goals
	private boolean complete = false;
	private boolean goingThrough = false;
	private boolean inTurns = false;
	private int turnsTime;
	private Station intermediary;
	//constraints
	private String trainName = null;
	
	public Goal(Station origin, Station destination, Station intermediary, int turn, int turnsTime) {
		this.origin = origin;
		this.destination = destination;
		if (this.intermediary != destination && this.intermediary != origin) {
			goingThrough = true;
			this.intermediary = intermediary;
		}

		else {
			this.intermediary = intermediary;
		}

		this.turnIssued = turn;

		if (turnsTime!=0)
		{
			this.inTurns=true;
			this.turnsTime=turnsTime;
		}
		else this.turnsTime=turnsTime;

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
		for(Tuple<String, Integer> history: train.getHistory()) {
			if(history.getFirst().equals(origin.getName()) && history.getSecond() >= turnIssued) {
				passedOrigin = true;
			}
		}
		if(train.getFinalDestination() == destination && passedOrigin) {
			return trainName == null || trainName.equals(train.getName());
		} else {
			return false;
		}
	}

	public boolean wentThroughStation(Train train) { //checks if a train has passed through the intermediary station if it exists
		boolean passedThrough = false;
		if (this.isComplete(train))
			if (goingThrough && train.routeContains(intermediary)) passedThrough = true;
		return passedThrough;
	}



	public boolean completedWithinMaxTurns(Train train) {
		boolean completed = false;
		if (this.isComplete(train) && this.inTurns)
			if (turnsTime + this.turnIssued <= resource.PlayerManager().getTurnNumber()) // dunno how to retrieve current turn number
				completed=true;
		return completed;

	}

	public String toString() { // based on the type of goal
		String trainString = "train";
		if(trainName != null) {
			trainString = trainName;
		}
		if (!goingThrough && !inTurns)
			return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName();
		else if (!goingThrough && inTurns)
					return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName() + " in " + this.turnsTime;
			 else if (goingThrough && !inTurns)
					return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName() + " through " + intermediary.getName();
				  else
						return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName() + " through " + intermediary.getName()  + " in " + this.turnsTime;
	}

	public void setComplete() {
		complete = true;
	}

	public boolean getComplete() {
		return complete;
	}

	public boolean isInTurns(){
		return inTurns;
	}

	public boolean isGoingThrough(){
		return goingThrough;
	}
}