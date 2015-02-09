package fvs.taxe.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.resource.Train;

//Responsible for checking whether the train is mouse entered
public class TrainMouseExit extends ClickListener {
    private Context context;
    private Train train;

    public TrainMouseExit(Context context, Train train) {
        this.train = train;
        this.context = context;
    }

    @Override
    public void exit(InputEvent event, float x, float y, int pointer, Actor trainActor) {

        if (Game.getInstance().getState() != GameState.NORMAL) return;

        // current player can't be passed in as it changes so find out current player at this instant
        Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

        if (!train.isOwnedBy(currentPlayer)) {
            context.getTopBarController().displayFlashMessage(" ", Color.LIGHT_GRAY, 0);
            return;
        }

        if (train.getFinalDestination() == null) {
            context.getTopBarController().displayFlashMessage(" ", Color.LIGHT_GRAY, 0);
        } else {
            context.getTopBarController().displayFlashMessage(" ", Color.LIGHT_GRAY, 0);
        }


    }



}
