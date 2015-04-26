package fvs.taxe.replay;

import fvs.taxe.controller.Context;

public class EndTurnAction extends Action {
	
	

	public EndTurnAction(Context context, long timestamp) {
		super(context, timestamp);
	}

	@Override
	public void play() {
		System.out.println("Replaying an end of turn action.");
		
		//End the turn
		context.getGameLogic().getPlayerManager().turnOver(context);
		
		//Refresh the goals and inventories
		context.getGameScreen().getGoalController().showCurrentPlayerGoals();
		context.getGameScreen().getResourceController().drawPlayerResources(context.getGameLogic().getPlayerManager().getCurrentPlayer());
	}

	
	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "End of Turn Action" + super.toString();
	}

}
