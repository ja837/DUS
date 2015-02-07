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

        /*// current player can't be passed in as it changes so find out current player at this instant
        Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

        if (!train.isOwnedBy(currentPlayer)) {
            context.getTopBarController().displayFlashMessage("Opponent's " + train.getName() + ". Speed: " + train.getSpeed(), Color.RED, 2);
            return;
        }

        if (train.getFinalDestination() == null) {
            context.getTopBarController().displayFlashMessage("Your " + train.getName() + ". Speed: " + train.getSpeed(), Color.BLACK, 2);
        } else {
            context.getTopBarController().displayFlashMessage("Your " + train.getName() + ". Speed: " + train.getSpeed() + ". Destination: " + train.getFinalDestination().getName(), Color.BLACK, 2);
        }
        DialogButtonClicked listener = new DialogButtonClicked(context, currentPlayer, train);
        DialogResourceTrain dia = new DialogResourceTrain(train, context.getSkin(), train.getPosition() != null);
        dia.show(context.getStage());
        dia.subscribeClick(listener);*/
    }

}
