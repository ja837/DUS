package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.goal.Goal;
import gameLogic.player.Player;

public class DropGoalAction extends Action {
	
	

	Goal goal;
	int playerDropping;

	public DropGoalAction(Context context, long timestamp, int p, Goal g) {
		super(context, timestamp);
		playerDropping = p;
		goal = g;
	}

	@Override
	public void play() {
		System.out.println("Replaying an goal dropping action.");

		//Drop the goal
		Player player = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerDropping - 1);
		player.removeGoal(goal);
		
		//Refresh the goal display
		context.getGameScreen().getGoalController().showCurrentPlayerGoals();
		
	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Goal Drop Action, removing '" + goal.toString() + "' from Player " + playerDropping + super.toString();
	}

}
