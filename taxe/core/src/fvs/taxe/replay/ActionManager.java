package fvs.taxe.replay;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class ActionManager {

	ArrayList<Action> actionList;
	int currentAction = -1;

	boolean playing = false;

	public ActionManager(){
		actionList = new ArrayList<Action>();
		currentAction = 0;
	}

	public void addAction(Action a){

		actionList.add(a);

		System.out.println("Action added to replay list: " + a.toString());
	}
	
	

	public void playNextAction(){
		playing = true;

		if (!(currentAction > actionList.size())) {

			Action a = actionList.get(currentAction);


			clickActor(a.getTimeStamp());


			currentAction++;
		}

		playing = false;
	}

	private void clickActor(long timeStamp) {

		
	}

}
