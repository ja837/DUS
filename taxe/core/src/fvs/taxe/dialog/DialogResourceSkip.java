package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.MainMenuScreen;
import fvs.taxe.TaxeGame;
import gameLogic.Game;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

public class DialogResourceSkip extends Dialog {

    public DialogResourceSkip(Skin skin) {
        super("Your Turn is skipped.", skin);

        button("Okay :( ","EXIT");
    }

    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }

    @Override
    public void hide() {
        hide(null);
    }

    @Override
    protected void result(Object obj) {
        if(obj == "EXIT"){
            Game.getInstance().getPlayerManager().turnOver(null);
        }
    }

}
