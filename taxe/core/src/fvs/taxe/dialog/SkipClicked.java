package fvs.taxe.dialog;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.resource.Obstacle;

public class SkipClicked extends ClickListener{

    Context context;

    public SkipClicked(Context context){
        this.context = context;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {

    }
}
