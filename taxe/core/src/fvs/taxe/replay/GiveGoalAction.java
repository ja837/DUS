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
	}

	@Override
	public void play() {
		System.out.println("Replaying an goal allocation action.");
		
		//Get the player to give the goal to
		Player playerToGiveGoalTo = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerNumber);
		
		//Give the goal
		context.getGameLogic().getGoalManager().addGoalToPlayer(playerToGiveGoalTo, goal);
		
		//Refresh displayed goals
		context.getGameScreen().getGoalController().showCurrentPlayerGoals();
		
	}

	
	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Goal Allocation Action, giving '" + goal.toString() + "' to Player " + playerNumber + super.toString();
	}

}
