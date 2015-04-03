package fvs.taxe.replay;

public class Action {

	String ID = "";
	ActionType type;
	
	public Action(String id){
		type = ActionType.ACTOR_CLICK;
		ID = id;
	}
	
	public Action(ActionType type, String id){
		this.type = type;
		this.ID = id;
	}
	
	
	public void play(){
		
	}
}
