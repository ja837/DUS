package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.Button;
import fvs.taxe.MainMenuScreen;
import fvs.taxe.TaxeGame;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

import java.util.ArrayList;
import java.util.List;

public class DialogResourceSkipped extends Dialog {
    private List<ResourceDialogClickListener> clickListeners = new ArrayList<ResourceDialogClickListener>();
    private TaxeGame game;

    public DialogResourceSkipped(TaxeGame game, PlayerManager pm, Skin skin) {
        super("Skip", skin);
        this.game = game;

        text("What do you want to do with this resource?");

        button("Cancel", "CLOSE");
        button("Drop", "DROP");
        button("Use", "USE");
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

    private void clicked(Button button) {
        for(ResourceDialogClickListener listener : clickListeners) {
            listener.clicked(button);
        }
    }


    @Override
    protected void result(Object obj) {
        if(obj == "EXIT"){
            Gdx.app.exit();
        } else if (obj == "DROP") {
            clicked(Button.SKIP_DROP);
        }else if (obj == "USE") {
            clicked(Button.SKIP_RESOURCE);
        }else {
            game.setScreen(new MainMenuScreen(game));
        }
    }

}
