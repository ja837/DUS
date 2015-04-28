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
	
	/**
	 * Gets the next unplayed action
	 * @return
	 */
	public Action getCurrentAction(){
		return actionList.get(currentAction);
	}
	
	/**
	 * Gets the last action that was replayed.
	 * @return
	 */
	public Action getPreviousAction(){
		return actionList.get(currentAction - 1);
	}
	
	/**
	 * Gets the timestamp of the next action
	 * @return
	 */
	public long getTimeOfNextAction(){
		if (currentAction < actionList.size()){
			return actionList.get(currentAction).getTimeStamp();
		}
		//This denotes the end of the replay
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
	
	/**
	 * Gets the time elapsed since the replay started in milliseconds.
	 * @return
	 */
	public long getTimeSinceReplayStarted() {
		return timeSinceReplayStarted;
	}

	/**
	 * Sets the time elapsed since the replay started in milliseconds.
	 * @param timeSinceReplayStarted
	 */
	public void setTimeSinceReplayStarted(long timeSinceReplayStarted) {
		this.timeSinceReplayStarted = timeSinceReplayStarted;
	}


/**
 * Sets the time stamp of when the replay was started.
 * @param replayStartingTime
 */
	public void setReplayStartingTime(long replayStartingTime) {
		this.replayStartingTime = replayStartingTime;
	}
	
	/**
	 * Returns true if currently in replaying mode.
	 * @return
	 */
	public boolean isReplaying() {
		return replaying;
	}
	
	
	/**
	 * starts the replay from the beginning
	 */
	public void startReplay() {
		this.replaying = true;
		restartReplay();
		
	}
	
	/**
	 * Resumes the replay from when it was last left off.
	 */
	public void resumeReplay(){
		this.replaying = true;
		paused = false;
	}
	
	/**
	 * restarts the replay from the beginning
	 */
	public void restartReplay(){
		setReplayStartingTime(TimeUtils.millis());
		timeSinceReplayStarted = 0;
		currentAction=0;
		paused = false;
	}
	
	/**
	 * stops the replay
	 */
	public void endReplay(){
		this.replaying = false;
	}
	
	/**
	 * pauses the replay
	 */
	public void pauseReplay(){
		this.paused = true;
	}
	
	/**
	 * Returns true if there are no more actions to replay. i.e. at the end of the replay
	 * @return
	 */
	public boolean atEndOfReplay(){
		return !(currentAction < actionList.size());
	}
	
	/**
	 * updates the time since the replay was started
	 * @param delta time since last frame in seconds
	 */
	public void updateTimeSinceReplayStart(float delta){
		timeSinceReplayStarted += delta * 1000; //delta is in seconds, we want milliseconds
	}
	
	/**
	 * updates the time since the game was started
	 * @param delta time since last frame in seconds
	 */
	public void updateTimeSinceGameStart(float delta){
		timeSinceGameStarted += delta * 1000; //delta is in seconds, we want milliseconds
	}
	
	/**
	 * returns true if replay is currently paused
	 * @return
	 */
	public boolean isPaused() {
		return paused;
	}


/**
 * returns true if the system is currently skipping thinking time
 * @return
 */
	public boolean isSkipThinkingTime() {
		return skipThinkingTime;
	}


/**
 * start or stop skipping thinking time
 * @param skipThinkingTime
 */
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
