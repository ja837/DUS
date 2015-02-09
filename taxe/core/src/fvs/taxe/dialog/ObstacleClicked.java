package fvs.taxe.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.resource.Obstacle;

public class ObstacleClicked extends ClickListener {
    private Obstacle obstacle;
    private Context context;
    public ObstacleClicked(Context context, Obstacle obstacle){
        this.obstacle = obstacle;
        this.context = context;
    }
    @Override
    public void clicked(InputEvent event, float x, float y) {
        if (Game.getInstance().getState() != GameState.NORMAL) return;

        // current player can't be passed in as it changes so find out current player at this instant
        Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

        DialogButtonClicked listener = new DialogButtonClicked(context, currentPlayer, obstacle);
        DialogResourceObstacle dia = new DialogResourceObstacle(obstacle, context.getSkin());
        dia.show(context.getStage());
        dia.subscribeClick(listener);
    }
}
