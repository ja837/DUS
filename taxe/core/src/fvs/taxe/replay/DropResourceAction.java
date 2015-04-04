package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.player.Player;
import gameLogic.resource.Resource;

public class DropResourceAction extends Action {
	
	
	Player player;
	Resource resource;

	public DropResourceAction(Context context, long timestamp, Player p, Resource r) {
		super(context, timestamp);
		player = p;
		resource = r;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		System.out.println("Replaying an train allocation action.");

		player.removeResource(resource);
		
	}

	@Override
	public String toString() {
		
		return "Resource Drop Action, removing " + resource.toString() + " from " + player.toString() + super.toString();
	}

}
