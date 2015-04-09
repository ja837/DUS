package fvs.taxe.replay;

import fvs.taxe.controller.Context;

public class EndTurnAction extends Action {
	
	

	public EndTurnAction(Context context, long timestamp) {
		super(context, timestamp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an end of turn action.");
		context.getGameLogic().getPlayerManager().turnOver(context);
		
		context.getGameScreen().getGoalController().showCurrentPlayerGoals();
		context.getGameScreen().getResourceController().drawPlayerResources(context.getGameLogic().getPlayerManager().getCurrentPlayer());
	}

	@Override
	public String toString() {
		
		return "End of Turn Action" + super.toString();
	}

}
