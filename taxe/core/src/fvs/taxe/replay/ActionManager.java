package fvs.taxe.replay;

import java.util.ArrayList;

public class ActionManager {
	
	ArrayList<Action> actionList;
	int currentAction = -1;
	
	boolean playing = false;
	
	public ActionManager(){
		actionList = new ArrayList<Action>();
	}
	
	public void addAction(Action a){
		
		actionList.add(a);
		
		System.out.println("Action added to replay list: " + a.toString());
	}
	
	public void playNextAction(){
		playing = true;
		
		
		
		playing = false;
	}

}
