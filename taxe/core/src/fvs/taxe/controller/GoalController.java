package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.TaxeGame;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GoalController {
    private Context context;
    private Group goalButtons = new Group();

    public GoalController(Context context) {
        this.context = context;
    }

    private List<String> playerGoalStrings() {
        //Returns player goals so that they can displayed on screen
        ArrayList<String> strings = new ArrayList<String>();
        PlayerManager pm = context.getGameLogic().getPlayerManager();
        Player currentPlayer = pm.getCurrentPlayer();

        for (Goal goal : currentPlayer.getGoals()) {
            if(goal.getComplete()) {
                continue;
            }

            strings.add(goal.toString());
        }

        return strings;
    }

    public void showCurrentPlayerGoals() {
        TaxeGame game = context.getTaxeGame();
        //Displays the player's current goals
        goalButtons.remove();
        goalButtons.clear();

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
        
        y -= 15;

        for (String goalString : playerGoalStrings()) {
            y -= 30;
            
            TextButton button  = new TextButton(goalString, context.getSkin());
            button.setPosition(x,y);
            goalButtons.addActor(button);
        }
        
        context.getStage().addActor(goalButtons);
    }

    private String playerHeader() {
        DecimalFormat integer = new DecimalFormat("0");
        return "Player " + context.getGameLogic().getPlayerManager().getCurrentPlayer().getPlayerNumber() + ": " + integer.format(context.getGameLogic().getPlayerManager().getCurrentPlayer().getScore());
    }
}
