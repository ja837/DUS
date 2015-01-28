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

public class DialogEndGame extends Dialog{
	private TaxeGame game;
	
	public DialogEndGame(TaxeGame game, PlayerManager pm, Skin skin) {
		super("GAME OVER", skin);
		this.game = game;

		int highScore = 0;
		int playerNum = 0;
		for(Player player : pm.getAllPlayers()) {
			if (player.getScore()>highScore){
				highScore = player.getScore();
				playerNum = player.getPlayerNumber();
			}
		}

		if(playerNum != 0) {
			text("PLAYER " + playerNum + " WINS!");
		} else {
			text("IT'S A TIE!");
		}
		
		//button("Main Menu","MENU");
		button("Exit","EXIT");
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
