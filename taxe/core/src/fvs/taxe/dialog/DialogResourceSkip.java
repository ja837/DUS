package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.MainMenuScreen;
import fvs.taxe.TaxeGame;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

public class DialogResourceSkip extends Dialog {
    private TaxeGame game;

    public DialogResourceSkip(TaxeGame game, PlayerManager pm, Skin skin) {
        super("Stolen turn", skin);
        this.game = game;

        button("Okay :(","EXIT");
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
            Gdx.app.exit();
        } else {
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
