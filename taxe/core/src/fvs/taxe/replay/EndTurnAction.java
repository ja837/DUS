package fvs.taxe.replay;

import fvs.taxe.controller.Context;

public class EndTurnAction extends Action {

	public EndTurnAction(Context context, long timestamp) {
		super(context, timestamp);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void play() {
		context.getGameLogic().getPlayerManager().turnOver(context);
	}

}
