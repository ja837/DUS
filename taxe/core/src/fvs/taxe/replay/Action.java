package fvs.taxe.replay;

import com.badlogic.gdx.utils.TimeUtils;

public class Action {

	String ID = "";
	


	ActionType type;
	long timeStamp;
	
	public Action(String id, long time){
		type = ActionType.ACTOR;
		ID = id;		
		timeStamp = time;
		
	}
	
	public Action(ActionType type, String id, long time){
		this.type = type;
		this.ID = id;
		
		this.timeStamp = time;
	}
	
	
	public void play(){
		
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Action of type: " + type.toString() + " with an ID of " + ID;
	}
	
	
	
	
	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public ActionType getType() {
		return type;
	}

	public void setType(ActionType type) {
		this.type = type;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	
}
