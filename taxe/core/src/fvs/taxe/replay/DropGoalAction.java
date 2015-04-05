package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.goal.Goal;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class DropGoalAction extends Action {
	
	

	Goal goal;
	int playerDropping;

	public DropGoalAction(Context context, long timestamp, int p, Goal g) {
		super(context, timestamp);
		playerDropping = p;
		
		goal = g;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an goal dropping action.");

		Player player = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerDropping - 1);
		player.removeGoal(goal);
		
	}

	@Override
	public String toString() {
		
		return "Goal Drop Action, removing '" + goal.toString() + "' from Player " + playerDropping + super.toString();
	}

}
