package fvs.taxe.replay;

import fvs.taxe.controller.Context;
import gameLogic.player.Player;
import gameLogic.resource.Engineer;
import gameLogic.resource.Modifier;
import gameLogic.resource.Obstacle;
import gameLogic.resource.Resource;
import gameLogic.resource.Skip;
import gameLogic.resource.Train;

public class DropResourceAction extends Action {
	
	int playerDropping;
	Resource resource;
	Train train = null;
	Skip skip = null;
	Engineer engineer= null;
	Obstacle  obst= null;
	Modifier mod = null;
	
	final int tr = 0;
	final int sk = 1;
	final int en = 2;
	final int ob = 3;
	final int mo = 4;
	
	int type = -1;
	

	public DropResourceAction(Context context, long timestamp, int p, Resource r) {
		super(context, timestamp);
		playerDropping = p;
		resource = r;
		if (r instanceof Train){
			train = (Train) r;
			type = tr;
		}else if (r instanceof Skip){
			skip = (Skip) r;
			type = sk;
		}else if (r instanceof Engineer){
			engineer = (Engineer) r;
			type = en;
		}else if (r instanceof Obstacle){
			obst = (Obstacle) r;
			type = ob;
		}else if (r instanceof Modifier){
			mod = (Modifier) r;
			type = mo;
		}

	}

	@Override
	public void play() {
		System.out.println("Replaying an resource dropping action.");

		Player player = context.getGameLogic().getPlayerManager().getAllPlayers().get(playerDropping - 1);
		
		for (Resource r : context.getReplayingGame().getPlayerManager().getAllPlayers().get(playerDropping - 1).getResources()){
			switch (type){
			case tr:
				if (r instanceof Train){
					Train t = (Train) r;
					if (t.getName().equals(train.getName()) && !t.isDeployed()){
						resource = t;
					}
				}
				break;
			case sk:
				if (r instanceof Skip){
					resource = r;
				}
				break;
			case en:
				if (r instanceof Engineer){
					resource = r;
				}
				break;
			case ob:
				if (r instanceof Obstacle){
					resource = r;
				}
				break;
			case mo:
				if (r instanceof Modifier){
					resource = r;
				}
				break;
			default:
				break;
			}
			
		}
		
		player.removeResource(resource);
		
		context.getGameScreen().getResourceController().drawPlayerResources(context.getGameLogic().getPlayerManager().getCurrentPlayer());
	}

	@Override
	public String toString() {
		
		return "Resource Drop Action, removing " + resource.toString() + " from Player " + playerDropping + super.toString();
	}

}
