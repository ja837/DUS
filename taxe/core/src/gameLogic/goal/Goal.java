package gameLogic.goal;

import Util.Tuple;
import com.badlogic.gdx.Game;
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
	private int score;
	private int bonus;
	private boolean withTrain;
	private Station intermediary;
	//constraints
	private Train trainName = null;

	public void setScore(int score){
		this.score = score;
	}

	public int getScore(){
		return this.score;
	}

	public int getBonus(){
		return this.bonus;
	}
	
	public Goal(Station origin, Station destination, Station intermediary, int turn, int turnsTime, int bonus, Train train) {
		if (train != null){
			trainName = train;
			withTrain = true;
		}
		this.origin = origin;
		this.destination = destination;
		//set the amount of extra points to give if a bonus goal is completed
		this.bonus = bonus;
		//the amount of points give is equal to the distance
		this.score = (int) fvs.taxe.controller.TrainMoveController.getDistanceStatic(origin.getLocation(), destination.getLocation());
		if (intermediary != destination && intermediary != origin) {
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


	}


	
	/*public void addConstraint(String name, String value) {
		if(name.equals("train")) {
			trainName = value;
		} else {
			throw new RuntimeException(name + " is not a valid goal constraint");
		}
	}*/

	public boolean isComplete(Train train) {
		boolean passedOrigin = false;
		for(Tuple<String, Integer> history: train.getHistory()) {
			if(history.getFirst().equals(origin.getName()) && history.getSecond() >= turnIssued) {
				passedOrigin = true;
			}
		}
		if(train.getFinalDestination() == destination && passedOrigin) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isBonusCompleted(Train train){
		if(withTrain == true) {
			 return wentThroughStation(train);
		}
		if(inTurns == true){
			return completedWithinMaxTurns(train);
		}
		if(goingThrough == true){
			return completedWithTrain(train);
		}
		return false;
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
			if (turnsTime + this.turnIssued <= gameLogic.Game.getInstance().getPlayerManager().getTurnNumber())
				completed=true;
		return completed;

	}

	public boolean completedWithTrain(Train train){
		if(this.trainName.getName() == train.getName()){
			return true;
		}
		return false;
	}


	public String toString() { // based on the type of goal
		String trainString = "train";
		if (trainName != null) {
			trainString = trainName.getName();
		}
		if (withTrain) {
			return "Send a " + trainString + " from " + origin.getName() + " to " + destination.getName();
		}
		if (inTurns){
			return "Send a train from " + origin.getName() + " to " + destination.getName() + " in " + this.turnsTime + " turns";
		}
		if (goingThrough) {
			return "Send a train from " + origin.getName() + " to " + destination.getName() + " through " + intermediary.getName();
		}
		return "Send a train from " + origin.getName() + " to " + destination.getName();
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