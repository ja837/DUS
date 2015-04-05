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
	float currentPlaybackSpeedMultiplier = 1;
    private boolean replaying = false;
	
	Game gameToBeReplayed;
	
	
	public ReplayManager(){
		actionList = new ArrayList<Action>();
		currentAction = 0;
		gameStartingTime = TimeUtils.millis();
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
		
		if (currentAction < actionList.size()){
			System.out.println("Current Action index = " + currentAction);
			actionList.get(currentAction).play();
			currentAction++;
		}
		
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

	private void setReplayStartingTime(long replayStartingTime) {
		this.replayStartingTime = replayStartingTime;
	}
	
	public boolean isReplaying() {
		return replaying;
	}
	
	public void startReplay() {
		this.replaying = true;
		setReplayStartingTime(TimeUtils.millis());
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
