package fvs.taxe.replay;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

public class ActionManager {

	ArrayList<Action> actionList;
	int currentAction = -1;
	long startingTime = 0;

	Stage stageForReplay;

	boolean playing = false;

	public ActionManager(Stage stage){
		actionList = new ArrayList<Action>();
		currentAction = 0;
		stageForReplay = stage;
		startingTime = TimeUtils.millis();
	}

	public long getStartingTime(){
		return startingTime;
	}

	public void addAction(Action a){

		actionList.add(a);

		System.out.println("Action added to replay list: " + a.toString());
	}

	public ArrayList<Action> getAllActions(){
		return actionList;
	}

	public void playNextAction(){
		playing = true;

		if (!(currentAction >= actionList.size())) {

			Action a = actionList.get(currentAction);


			clickActor(a.getTimeStamp());


			currentAction++;
		}

		playing = false;
	}

	private void clickActor(long timeStamp) {


		

		for (Actor actor : stageForReplay.getActors()){
			


			//If the actor is the correct one associated with this timestamp
			if (actor.getName() != null){
				if (actor.getName().equals(timeStamp + "")){


					for (EventListener l : actor.getListeners()){
						if (l instanceof ClickListener){

							//Click the actor
							((ClickListener) l).clicked(null, 0, 0);
						}
					}
				}
			}
		}

	}

}
