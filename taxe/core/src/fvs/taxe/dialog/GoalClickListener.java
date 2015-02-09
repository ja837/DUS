package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import fvs.taxe.Tooltip;
import fvs.taxe.actor.StationActor;
import fvs.taxe.controller.Context;
import gameLogic.Game;
import gameLogic.GameState;
import gameLogic.Player;
import gameLogic.goal.Goal;
import gameLogic.map.Station;

//Responsible for checking whether the goal is clicked
public class GoalClickListener extends ClickListener {
	private Context context;
	private Goal goal;
	private Tooltip tooltip1;
	private Tooltip tooltip2;
	private boolean showingTooltips;
	public GoalClickListener(Context context, Goal goal) {
		this.goal = goal;
		this.context = context;
		this.showingTooltips = false;
	}

	@Override
	public void clicked(InputEvent event, float x, float y) {
		if (showingTooltips) {
			//This hides the currently shown tooltips as otherwise they get stuck
			tooltip1.hide();
			tooltip2.hide();
			showingTooltips = false;
		}
		if (Game.getInstance().getState() != GameState.NORMAL) return;


		Player currentPlayer = Game.getInstance().getPlayerManager().getCurrentPlayer();

		DialogGoalButtonClicked listener = new DialogGoalButtonClicked(context, currentPlayer,
				goal);
		DialogGoal dia = new DialogGoal(goal, context.getSkin());
		dia.show(context.getStage());
		dia.subscribeClick(listener);
	}

	@Override
	public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
		if (!showingTooltips) {
			//Need to check whether tooltips are currently being shown as otherwise it redraws them instantly after the clicked routine has ended
			tooltip1 = new Tooltip(context.getSkin());
			Station origin = goal.getOrigin();
			StationActor originActor = origin.getActor();

			tooltip1.setPosition(originActor.getX() + 20, originActor.getY() + 20);
			tooltip1.show(origin.getName());
			context.getStage().addActor(tooltip1);

			tooltip2 = new Tooltip(context.getSkin());
			Station destination = goal.getDestination();
			StationActor destinationActor = destination.getActor();
			context.getStage().addActor(tooltip2);


			tooltip2.setPosition(destinationActor.getX() + 20, destinationActor.getY() + 20);
			tooltip2.show(destination.getName());
			showingTooltips = true;
		}
	}

	@Override
	public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
		if (showingTooltips) {
			tooltip1.hide();
			tooltip2.hide();
			showingTooltips = false;
		}
	}

}
