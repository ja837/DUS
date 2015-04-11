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
	long timeSinceGameStarted = 0;
	
	long replayStartingTime = -1;
	long timeSinceReplayStarted = -1;;
	
	float currentPlaybackSpeedMultiplier = 1;
    private boolean replaying = false;
    private boolean paused = false;
    private boolean skipThinkingTime = false;

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
	
	public Action getCurrentAction(){
		return actionList.get(currentAction);
	}
	
	public Action getPreviousAction(){
		return actionList.get(currentAction - 1);
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
		return timeSinceGameStarted;
	}
	
	/**
	 * 
	 * @return the time that the replay started at.
	 */
	public long getReplayStartingTime() {
		return replayStartingTime;
	}
	
	public long getTimeSinceReplayStarted() {
		return timeSinceReplayStarted;
	}

	public void setTimeSinceReplayStarted(long timeSinceReplayStarted) {
		this.timeSinceReplayStarted = timeSinceReplayStarted;
	}



	public void setReplayStartingTime(long replayStartingTime) {
		this.replayStartingTime = replayStartingTime;
	}
	
	public boolean isReplaying() {
		return replaying;
	}
	
	public void startReplay() {
		this.replaying = true;
		restartReplay();
		
	}
	
	public void resumeReplay(){
		this.replaying = true;
		paused = false;
	}
	
	public void restartReplay(){
		setReplayStartingTime(TimeUtils.millis());
		timeSinceReplayStarted = 0;
		currentAction=0;
		paused = false;
	}
	
	public void endReplay(){
		this.replaying = false;
	}
	
	public void pauseReplay(){
		this.paused = true;
	}
	
	public boolean atEndOfReplay(){
		return !(currentAction < actionList.size());
	}
	
	
	public void updateTimeSinceReplayStart(float delta){
		timeSinceReplayStarted += delta * 1000; //delta is in seconds, we want milliseconds
	}
	
	public void updateTimeSinceGameStart(float delta){
		timeSinceGameStarted += delta * 1000; //delta is in seconds, we want milliseconds
	}
	
	
	public boolean isPaused() {
		return paused;
	}



	public boolean isSkipThinkingTime() {
		return skipThinkingTime;
	}



	public void setSkipThinkingTime(boolean skipThinkingTime) {
		this.skipThinkingTime = skipThinkingTime;
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
