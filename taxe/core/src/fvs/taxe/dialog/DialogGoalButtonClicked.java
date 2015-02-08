package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import fvs.taxe.Button;
import fvs.taxe.GameScreen;
import fvs.taxe.StationClickListener;
import fvs.taxe.actor.TrainActor;
import fvs.taxe.controller.Context;
import fvs.taxe.controller.StationController;
import fvs.taxe.controller.TrainController;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.goal.Goal;
import gameLogic.map.CollisionStation;
import gameLogic.map.Station;
import gameLogic.resource.Train;

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

                break;
            case GOAL_VIEW:
                //highlight goals
                break;
        }
    }
}
