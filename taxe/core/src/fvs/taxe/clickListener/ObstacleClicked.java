package fvs.taxe.clickListener;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.controller.Context;
import fvs.taxe.dialog.DialogButtonClicked;
import fvs.taxe.dialog.DialogResourceObstacle;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.resource.Obstacle;

public class ObstacleClicked extends ClickListener {
    private Obstacle obstacle;
    private Context context;

    public ObstacleClicked(Context context, Obstacle obstacle) {
        this.obstacle = obstacle;
        this.context = context;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (Game.getInstance().getState() == GameState.NORMAL) {

            // current player can't be passed in as it changes so find out current player at this instant
            Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

            //Creates a dialog and a listener for the result of it
            DialogButtonClicked listener = new DialogButtonClicked(context, currentPlayer, obstacle);
            DialogResourceObstacle dia = new DialogResourceObstacle(obstacle, context.getSkin());
            dia.show(context.getStage());
            dia.subscribeClick(listener);
        }
    }
}
