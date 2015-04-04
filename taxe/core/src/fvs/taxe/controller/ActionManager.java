package fvs.taxe.controller;

import java.util.LinkedList;

import gameLogic.listeners.PlayerChangedListener;


public class ActionManager {
	
	private Context context;
	private LinkedList<Action> actionList;
	public ActionManager(final Context context) {
        this.context = context;
        this.actionList = new LinkedList<Action>();
    }
}
