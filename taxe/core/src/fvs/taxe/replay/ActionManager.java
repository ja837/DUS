package fvs.taxe.replay;

import java.util.ArrayList;

public class ActionManager {
	
	ArrayList<Action> actionList;
	int currentAction = -1;
	
	boolean playing = false;
	
	public void addAction(Action a){
		
		actionList.add(a);
	}

}
