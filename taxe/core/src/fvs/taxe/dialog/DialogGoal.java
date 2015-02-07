package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.Button;
import fvs.taxe.TaxeGame;
import gameLogic.goal.Goal;

import java.util.ArrayList;
import java.util.List;

public class DialogGoal extends Dialog{
    private List<ResourceDialogClickListener> clickListeners = new ArrayList<ResourceDialogClickListener>();
    private TaxeGame game;

    public DialogGoal(Goal goal, Skin skin) {

        super(goal.toString(), skin);

        text("What do you want to do with this goal?");

        button("Cancel", "CLOSE");
        button("Drop", "DROP");
        button("View Locations", "VIEW");

    }

    @Override
    public Dialog show(Stage stage) {
        show(stage, null);
        setPosition(Math.round((stage.getWidth() - getWidth()) / 2), Math.round((stage.getHeight() - getHeight()) / 2));
        return this;
    }


    @Override
    public void hide () {
        hide(null);
    }

    private void clicked(Button button) {
        for(ResourceDialogClickListener listener : clickListeners) {
            listener.clicked(button);
        }
    }

    public void subscribeClick(ResourceDialogClickListener listener) {
        clickListeners.add(listener);
    }

    @Override
    protected void result(Object obj) {
        if (obj == "CLOSE") {
            this.remove();
        } else if (obj == "DROP") {
            clicked(Button.GOAL_DROP);
        } else if(obj == "VIEW") {
            clicked(Button.GOAL_VIEW);
        }
    }
}
