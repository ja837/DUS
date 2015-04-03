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
	
	
}
