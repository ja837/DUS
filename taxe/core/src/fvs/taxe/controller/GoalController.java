package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import fvs.taxe.TaxeGame;
import fvs.taxe.dialog.GoalClickListener;
import gameLogic.Player;
import gameLogic.PlayerChangedListener;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

public class GoalController {
	private Context context;
	private Group goalButtons = new Group();
	private Color[] colours = new Color[3];

	public GoalController(Context context) {
		this.context = context;

		context.getGameLogic().getPlayerManager()
			   .subscribePlayerChanged(new PlayerChangedListener() {
				   @Override
				   public void changed() {
					   showCurrentPlayerGoals();
				   }
			   });
	}

	private List<String> playerGoalStrings() {
		//Returns player goals so that they can displayed on screen
		ArrayList<String> strings = new ArrayList<String>();
		PlayerManager pm = context.getGameLogic().getPlayerManager();
		Player currentPlayer = pm.getCurrentPlayer();

		for (Goal goal : currentPlayer.getGoals()) {
			if (goal.getComplete()) {
				continue;
			}

			strings.add(goal.toString());
		}

		return strings;
	}

	public void drawHeaderText() {
		TaxeGame game = context.getTaxeGame();
		PlayerManager pm = context.getGameLogic().getPlayerManager();
		float top = (float) TaxeGame.HEIGHT;
		float x = 10.0f;
		float y = top - 10.0f - TopBarController.CONTROLS_HEIGHT;
		game.batch.begin();
		game.fontSmall.setColor(Color.BLACK);
		//Draws the player's name and their score
		game.fontSmall.draw(game.batch, playerHeader(), x, y);
		y -= 30;
		//Draws "Goals:"
		game.fontSmall.draw(game.batch, "Goals:", x, y);
		game.batch.end();
	}

	public void setColours(Color[] colours) {
		this.colours = colours;
		showCurrentPlayerGoals();
	}

	public void showCurrentPlayerGoals() {
		//Displays the player's current goals
		goalButtons.remove();
		goalButtons.clear();

		PlayerManager pm = context.getGameLogic().getPlayerManager();
		Player currentPlayer = pm.getCurrentPlayer();

		float top = (float) TaxeGame.HEIGHT;
		float x = 10.0f;
		float y = top - 60.0f - TopBarController.CONTROLS_HEIGHT;
		int index = 0;

		for (Goal goal : currentPlayer.getGoals()) {
			if (!goal.getComplete()) {
				y -= 40;
				TextButton button = new TextButton(
						goal.baseGoalString() + "\n" + goal.bonusString(), context.getSkin());
				button.getLabel().setAlignment(Align.left);
				float scaleFactor = 0.8f;
				button.getLabel().setFontScale(scaleFactor, scaleFactor);
				button.setWidth(scaleFactor * button.getWidth());
				button.setHeight(scaleFactor * button.getHeight());
				GoalClickListener listener = new GoalClickListener(context, goal);

				button.setPosition(x, y);
				if (colours[index++] != null) {
					button.setColor(colours[index - 1]);
				}
				button.addListener(listener);
				button.getClickListener();
				goalButtons.addActor(button);
			}
		}


		context.getStage().addActor(goalButtons);
	}

	private String playerHeader() {
		DecimalFormat integer = new DecimalFormat("0");
		return "Player " +
				context.getGameLogic().getPlayerManager().getCurrentPlayer().getPlayerNumber() +
				": " + integer.format(
				context.getGameLogic().getPlayerManager().getCurrentPlayer().getScore());
	}
}
