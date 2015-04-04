package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.goal.Goal;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class GiveGoalAction extends Action {
	
	
	Player playerToGiveGoalTo;
	Goal goal;

	public GiveGoalAction(Context context, long timestamp, Player p, Goal g) {
		super(context, timestamp);
		playerToGiveGoalTo = p;
		goal = g;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an train allocation action.");

		context.getGameLogic().getGoalManager().addGoalToPlayer(playerToGiveGoalTo, goal);
		
	}

	@Override
	public String toString() {
		
		return "Goal Allocation Action, giving '" + goal.toString() + "' to " + playerToGiveGoalTo.toString() + super.toString();
	}

}
