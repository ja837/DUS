package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.player.Player;
import gameLogic.resource.Engineer;
import gameLogic.resource.Modifier;
import gameLogic.resource.Obstacle;
import gameLogic.resource.Resource;
import gameLogic.resource.Skip;
import gameLogic.resource.Train;

public class GiveResourceAction extends Action {
	
	
	int playerToGiveResourceTo;
	Resource resource;

	public GiveResourceAction(Context context, long timestamp, int p, Resource r) {
		super(context, timestamp);
		playerToGiveResourceTo = p;
		
		if (r instanceof Train){
			resource = new Train((Train) r);
		}else if (r instanceof Skip){
			resource = new Skip();
		}else if (r instanceof Engineer){
			resource = new Engineer();
		}else if (r instanceof Obstacle){
			resource = new Obstacle();
		}else if (r instanceof Modifier){
			resource = new Modifier();
		}

	}

	@Override
	public void play() {
		System.out.println("Replaying an resource allocation action.");
		
		//Get the player to add the resource to
		Player player = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerToGiveResourceTo - 1);
		//Give the resource
		context.getGameLogic().getResourceManager().addResourceToPlayer(player, resource);
		
	}

	/**
	 * First half to toString for an Action, second half is in Action.java
	 */
	@Override
	public String toString() {
		
		return "Resource Allocation Action, giving " + resource.toString() + " to Player " + playerToGiveResourceTo + super.toString();
	}

}
