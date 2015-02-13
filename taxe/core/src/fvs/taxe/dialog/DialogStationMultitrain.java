package fvs.taxe.dialog;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import fvs.taxe.controller.Context;
import gameLogic.Player;
import gameLogic.map.Station;
import gameLogic.resource.Resource;
import gameLogic.resource.Train;

import java.util.ArrayList;

public class DialogStationMultitrain extends Dialog {
	
	private Context context;
	private boolean isTrain = false;
	
	public DialogStationMultitrain(Station station, Skin skin, Context context) {
		//This constructor is called when a station is clicked
		super(station.getName(), skin);
		
		this.context = context;
		
		text("Choose which train you would like");
		
		for(Player player : context.getGameLogic().getPlayerManager().getAllPlayers()) {
			for(Resource resource : player.getResources()) {
				if(resource instanceof Train) {
					if(((Train) resource).getPosition() == station.getLocation()) {
						String destination = "";
						if(((Train) resource).getFinalDestination() != null) {
							destination = " to " + ((Train) resource).getFinalDestination().getName();
						}
						button(((Train) resource).getName() + destination + " (Player " + ((Train) resource).getPlayer().getPlayerNumber() + ")", ((Train) resource));
						getButtonTable().row();
						isTrain = true;
					}
				}
			}
		}
		
		button("Cancel","CANCEL");
		if(!isTrain) {
			hide();
		}
	}

	public DialogStationMultitrain(ArrayList<Train> trains, Skin skin, Context context) {
		//This constructor is called when there are multiple blocked trains sitting on top of each other
		super("Select Train", skin);

		this.context = context;

		text("Choose which train you would like");

		for(Train train:trains) {


			String destination = " to " + train.getFinalDestination().getName();

						button (train.getName() + destination + " (Player " + train.getPlayer().getPlayerNumber() + ")", train);
						getButtonTable().row();
isTrain = true;


		}

		button("Cancel","CANCEL");
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
		if(obj == "CANCEL"){
			this.remove();
		} else {
			//Simulate click on train
			TrainClicked clicker = new TrainClicked(context, (Train) obj);
			//This is a small hack, by setting the value of the simulated x value to -1, we can use this to check whether or not
			//This dialog has been opened before. If this was not here then this dialog and trainClicked would get stuck in an endless loop!
			clicker.clicked(null, -1, 0);
		}
	}
	
	public boolean getIsTrain() {
		return isTrain;
	}
}
