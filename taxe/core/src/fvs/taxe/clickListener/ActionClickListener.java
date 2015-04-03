package fvs.taxe.clickListener;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

import fvs.taxe.replay.Action;
import fvs.taxe.replay.ActionManager;

public class ActionClickListener extends ClickListener {
	
	private ActionManager manager;
    private Actor actor;

    public ActionClickListener(ActionManager manager, Actor actor) {
        this.manager = manager;
        this.actor = actor;
    }

    @Override
    public void clicked(InputEvent event, float x, float y) {       
        if (actor != null) {
        	
        	//Get the time since the game started
        	long time = TimeUtils.millis() - manager.getStartingTime();
        	
        	//Set the name of the actor to the timestamp at which it was clicked
        	actor.setName(time + "");
        	Action action = new Action(actor.getName(), time);
        	
        	//Add the action to the managers list of actions
            manager.addAction(action);
        }

        
        
    }

}
