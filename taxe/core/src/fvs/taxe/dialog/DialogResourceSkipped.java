package fvs.taxe.dialog;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.Button;
import fvs.taxe.MainMenuScreen;
import fvs.taxe.TaxeGame;
import fvs.taxe.controller.Context;
import gameLogic.Player;
import gameLogic.resource.Skip;

import java.util.ArrayList;
import java.util.List;

public class DialogResourceSkipped extends Dialog {
    private List<ResourceDialogClickListener> clickListeners = new ArrayList<ResourceDialogClickListener>();

    private Skip skip;

    public DialogResourceSkipped(Skin skin, Skip skip) {
        super("Skip Turn", skin);
        text("What do you want to do with this resource?");

        button("Cancel", "CLOSE");
        button("Drop", "DROP");
        button("Use", "USE");
    }

    public void subscribeClick(ResourceDialogClickListener listener) {
        clickListeners.add(listener);
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
        }
    }

}
