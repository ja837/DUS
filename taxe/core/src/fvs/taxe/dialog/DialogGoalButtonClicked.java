package fvs.taxe.dialog;

import fvs.taxe.Button;
import fvs.taxe.controller.Context;
import gameLogic.Player;
import gameLogic.goal.Goal;

public class DialogGoalButtonClicked implements ResourceDialogClickListener {
	private Context context;
	private Player currentPlayer;
	private Goal goal;

	public DialogGoalButtonClicked(Context context, Player player, Goal goal) {
		this.currentPlayer = player;
		this.goal = goal;
		this.context = context;
	}

	@Override
	public void clicked(Button button) {
		switch (button) {
			case GOAL_DROP:
				currentPlayer.removeGoal(goal);
				//simulate mouse exiting goal button to remove tooltips

				break;
		}
	}
}
