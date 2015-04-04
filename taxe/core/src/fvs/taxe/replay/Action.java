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
	
	
	public long getTimeStamp(){
		return timeStamp;
	}

	@Override
	public String toString() {
		return " at timestamp of " + timeStamp;
	}
}
