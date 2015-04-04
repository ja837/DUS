package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.goal.Goal;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class DropGoalAction extends Action {
	
	
	Player player;
	Goal goal;

	public DropGoalAction(Context context, long timestamp, Player p, Goal g) {
		super(context, timestamp);
		player = p;
		goal = g;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an train allocation action.");

		player.removeGoal(goal);
		
	}

	@Override
	public String toString() {
		
		return "Goal Drop Action, removing '" + goal.toString() + "' from " + player.toString() + super.toString();
	}

}
