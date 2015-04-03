package fvs.taxe.replay;

public class Action {

	String ID = "";
	ActionType type;
	
	public Action(String id){
		type = ActionType.ACTOR;
		ID = id;
	}
	
	public Action(ActionType type, String id){
		this.type = type;
		this.ID = id;
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
		ACTOR,
		

	}
	
	
}
