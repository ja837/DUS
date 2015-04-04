package fvs.taxe.replay;

import com.badlogic.gdx.utils.TimeUtils;

public class Action {

	String ID = "";
	ActionType type;
	long timeStamp;
	
	public Action(String id){
		type = ActionType.ACTOR;
		ID = id;
		
		this.timeStamp = TimeUtils.millis();
	}
	
	public Action(ActionType type, String id){
		this.type = type;
		this.ID = id;
		
		this.timeStamp = TimeUtils.millis();
	}
	
	
	public void play(){
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Action of type: " + type.toString() + " with an ID of " + ID;
	}
	
	
	/*
	 * Enum for which type of action was performed.
	 * Button for if a dialog button was clicked
	 * stage for if the stage was clicked
	 * Actor for if an actor was clicked.
	 */
	public enum ActionType {	
		
		BUTTON,
		STAGE,
		ACTOR
		

	}
	
	
}
