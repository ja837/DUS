package fvs.taxe.controller;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import fvs.taxe.TaxeGame;
import fvs.taxe.dialog.GoalClickListener;
import gameLogic.Player;
import gameLogic.PlayerManager;
import gameLogic.goal.Goal;

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
    public void drawHeaderText(){
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

    public void showCurrentPlayerGoals() {
        //Displays the player's current goals
        goalButtons.remove();
        goalButtons.clear();

        PlayerManager pm = context.getGameLogic().getPlayerManager();
        Player currentPlayer = pm.getCurrentPlayer();

        float top = (float) TaxeGame.HEIGHT;
        float x = 10.0f;
        float y = top - 55.0f - TopBarController.CONTROLS_HEIGHT;

        for (Goal goal : currentPlayer.getGoals()) {
            String goalString = goal.toString();

            y -= 30;
            
            TextButton button  = new TextButton(goalString, context.getSkin());
            GoalClickListener listener = new GoalClickListener(context, goal);

            button.setPosition(x,y);
            button.addListener(listener);
            goalButtons.addActor(button);
        }

        
        context.getStage().addActor(goalButtons);
    }

    private String playerHeader() {
        return "Player " + context.getGameLogic().getPlayerManager().getCurrentPlayer().getPlayerNumber() + ": " + context.getGameLogic().getPlayerManager().getCurrentPlayer().getScore();
    }
}
