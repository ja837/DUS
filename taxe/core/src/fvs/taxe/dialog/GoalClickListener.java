package fvs.taxe.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.goal.Goal;

import javax.swing.*;

//Responsible for checking whether the goal is clicked
public class GoalClickListener extends ClickListener {
    private Context context;
    private Goal goal;

    public GoalClickListener(Context context, Goal goal) {
        this.goal = goal;
        this.context = context;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (Game.getInstance().getState() != GameState.NORMAL) return;
        JOptionPane.showMessageDialog(null, "Goal clicked", "InfoBox: " + "Message", JOptionPane.INFORMATION_MESSAGE);
        Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

        DialogGoalButtonClicked listener = new DialogGoalButtonClicked(context, currentPlayer, goal);
        DialogGoal dia = new DialogGoal(goal, context.getSkin());
        dia.show(context.getStage());
        dia.subscribeClick(listener);
    }

}
