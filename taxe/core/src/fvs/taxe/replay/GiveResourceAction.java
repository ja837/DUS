package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class GiveResourceAction extends Action {
	
	
	Player playerToGiveResourceTo;
	Resource resource;

	public GiveResourceAction(Context context, long timestamp, Player p, Resource r) {
		super(context, timestamp);
		playerToGiveResourceTo = p;
		resource = r;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an train allocation action.");

		context.getGameLogic().getResourceManager().addResourceToPlayer(playerToGiveResourceTo, resource);
		
	}

	@Override
	public String toString() {
		
		return "Train Allocation Action, giving " + resource.toString() + " to " + playerToGiveResourceTo.toString() + super.toString();
	}

}
