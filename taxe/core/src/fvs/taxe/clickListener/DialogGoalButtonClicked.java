package fvs.taxe.clickListener;

import fvs.taxe.Button;
import fvs.taxe.clickListener.ResourceDialogClickListener;
import fvs.taxe.controller.Context;
import fvs.taxe.replay.DropGoalAction;
import fvs.taxe.replay.DropResourceAction;
import gameLogic.GameState;
import gameLogic.player.Player;
import gameLogic.goal.Goal;

public class DialogGoalButtonClicked implements ResourceDialogClickListener {
    private Player currentPlayer;
    private Goal goal;
    private Context context;

    public DialogGoalButtonClicked(Player player, Goal goal, Context context) {
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
                
                if (context.getGameLogic().getState() != GameState.REPLAYING){
                	//Add goal dropping to replay system.
    				DropGoalAction actionDropG = new DropGoalAction(context, context.getGameLogic().getReplayManager().getCurrentTimeStamp(), currentPlayer, goal);
    				context.getGameLogic().getReplayManager().addAction(actionDropG);
                
                }

                break;
        }
    }
}
