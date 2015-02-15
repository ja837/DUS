package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.resource.Skip;

public class SkipClicked extends ClickListener {

    Context context;
    Skip skip;

    public SkipClicked(Context context, Skip skip) {
        this.context = context;
        this.skip = skip;
    }


    public void clicked(InputEvent event, float x, float y) {
        //When skip is clicked it checks whether the game is in the normal state
        if (Game.getInstance().getState() == GameState.NORMAL) {

            // current player can't be passed in as it changes so find out current player at this instant
            Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

            //Creates a dialog when skip is clicked allowing the user to select what they want to do with the resource
            DialogButtonClicked listener = new DialogButtonClicked(context, currentPlayer, skip);
            DialogResourceSkipped dia = new DialogResourceSkipped(context);
            dia.show(context.getStage());
            dia.subscribeClick(listener);
        }
    }
}
