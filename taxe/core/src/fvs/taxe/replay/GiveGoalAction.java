package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.goal.Goal;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class GiveGoalAction extends Action {
	
	
	int playerNumber;
	Goal goal;

	public GiveGoalAction(Context context, long timestamp, int player, Goal g) {
		super(context, timestamp);
		playerNumber = player - 1;
		goal = g;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an goal allocation action.");
		
		Player playerToGiveGoalTo = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerNumber);
		context.getGameLogic().getGoalManager().addGoalToPlayer(playerToGiveGoalTo, goal);
		
		context.getGameScreen().getGoalController().showCurrentPlayerGoals();
		
	}

	@Override
	public String toString() {
		
		return "Goal Allocation Action, giving '" + goal.toString() + "' to Player " + playerNumber + super.toString();
	}

}
