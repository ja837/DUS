package fvs.taxe.replay;

import java.util.ArrayList;

import com.badlogic.gdx.utils.TimeUtils;

public class ReplayManager {
	
	
	ArrayList<Action> actionList;
	int currentAction = -1;
	long startingTime = 0;
	boolean playing = false;
	
	
	public ReplayManager(){
		actionList = new ArrayList<Action>();
		currentAction = 0;
		startingTime = TimeUtils.millis();
	}

	
	public void addAction(Action a){

		actionList.add(a);

		System.out.println("Action added to replay list: " + a.toString());
	}
	
	public void playNextAction(){
		playing = true;
		
		
		
		
		
		playing = false;
	}
	
	/*
	 * Get the list of all the actions
	 */
	public ArrayList<Action> getAllActions(){
		return actionList;
	}
	
	
	/*
	 * When recording an action we need to save the time it happened. This method returns the time elapsed after the game started
	 */
	public long getCurrentTimeStamp(){		
		return TimeUtils.millis() - startingTime;
	}
}
