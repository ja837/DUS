package fvs.taxe.replay;

import java.util.ArrayList;

import com.badlogic.gdx.utils.TimeUtils;

/**
 * Manages all the actions for replaying
 * @author Jamie
 *
 */
public class ReplayManager {
	
	
	ArrayList<Action> actionList;
	int currentAction = -1;
	long startingTime = 0;
	boolean playing = false;
	float currentPlaybackSpeedMultiplier = 1;
	
	
	public ReplayManager(){
		actionList = new ArrayList<Action>();
		currentAction = 0;
		startingTime = TimeUtils.millis();
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
		}
		
		
		
		playing = false;
	}
	
	/**
	 * Get the list of all the actions
	 */
	public ArrayList<Action> getAllActions(){
		return actionList;
	}
	
	
	/**
	 * When recording an action we need to save the time it happened. This method returns the time elapsed after the game started
	 */
	public long getCurrentTimeStamp(){		
		return TimeUtils.millis() - startingTime;
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
