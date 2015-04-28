package fvs.taxe.replay;

import fvs.taxe.controller.Context;

public abstract class Action {

	
	long timeStamp;
	Context context;
	
	public Action(Context context, long timestamp){
		this.context = context;
		this.timeStamp = timestamp;
	}
	
	/**
	 * This method is called when the action is being replayed.
	 */
	public abstract void play();
	
	/**
	 * returns the timestamp of when the action happened.
	 * @return
	 */
	public long getTimeStamp(){
		return timeStamp;
	}

	/**
	 * returns the second half of the tostring for an action. The first halves are in the subclasses of Action.
	 */
	@Override
	public String toString() {
		return " at timestamp of " + timeStamp;
	}
}
