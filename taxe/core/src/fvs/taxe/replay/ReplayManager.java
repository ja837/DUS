package fvs.taxe.replay;

import gameLogic.Game;

import java.util.ArrayList;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Manages all the actions for replaying
 * @author Jamie
 *
 */
public class ReplayManager {
	
	
	ArrayList<Action> actionList;
	public int currentAction = -1;
	long gameStartingTime = 0;
	long replayStartingTime = -1;
	boolean playing = false;
	float currentPlaybackSpeedMultiplier = 1;
	
	Game gameToBeReplayed;
	
	
	public ReplayManager(Game game){
		actionList = new ArrayList<Action>();
		currentAction = 0;
		gameStartingTime = TimeUtils.millis();
		gameToBeReplayed = game;
	}



	/**
	 * Add an action to the list
	 * @param a Action to be added
	 */
	public void addAction(Action a){

		actionList.add(a);

		System.out.println("Action added to replay list: " + a.toString());
	}
	
	/**
	 * Plays the next action in the list.
	 */
	public void playNextAction(){
		playing = true;
		
		if (currentAction < actionList.size()){
			actionList.get(currentAction).play();
			currentAction++;
		}
		
		
		
		playing = false;
	}
	
	/**
	 * Get the list of all the actions
	 */
	public ArrayList<Action> getAllActions(){
		return actionList;
	}
	
	public long getTimeOfNextAction(){
		if (currentAction < actionList.size()){
			return actionList.get(currentAction).getTimeStamp();
		}
		return -1;
	}
	
	
	/**
	 * When recording an action we need to save the time it happened. This method returns the time elapsed after the game started
	 */
	public long getCurrentTimeStamp(){		
		return TimeUtils.millis() - gameStartingTime;
	}
	
	public long getReplayStartingTime() {
		return replayStartingTime;
	}

	public void setReplayStartingTime(long replayStartingTime) {
		this.replayStartingTime = replayStartingTime;
	}
	
	/**
	 * Prints all the recorded actions out into console. Just to check if recording has worked.
	 */
	public void printDebugInfo(){
		System.out.println("\nReplay Manager Debug Info \n\n");
		for (Action a : actionList){
			System.out.println(a.toString());
		}
	}
}
