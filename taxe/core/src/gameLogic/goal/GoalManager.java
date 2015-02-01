package gameLogic.goal;

import gameLogic.Game;
import gameLogic.Player;
import gameLogic.map.CollisionStation;
import gameLogic.map.Map;
import gameLogic.map.Station;
import gameLogic.resource.ResourceManager;
import gameLogic.resource.Train;

import java.util.ArrayList;
import java.util.Random;

public class GoalManager {
	public final static int CONFIG_MAX_PLAYER_GOALS = 3;
	private ResourceManager resourceManager;
	
	public GoalManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	private Goal generateRandom(int turn) {
		Map map = Game.getInstance().getMap();
		Station origin;
		Station intermediary;
		int forTurns;
		int bonus = 0;
		Train train = null;

		do {
			origin = map.getRandomStation();
		} while (origin instanceof CollisionStation);
		
		Station destination;
		do {
			destination = map.getRandomStation();
		} while (destination == origin || destination instanceof CollisionStation);

		Random random = new Random();
		int rand = random.nextInt(2);
		if (rand == 0){//decide if goal has intermediary station; if not, initiate  the intermediary station as origin
			do {
				intermediary = map.getRandomStation();
			} while (intermediary == origin || intermediary == destination || intermediary instanceof CollisionStation);
			bonus = 100; //bonus to be awarded if the train completes the goal passing through the intermediary station
		}
		else intermediary = origin;

		if (rand==1){ //decides if goal can be competed in a number of turns for bonus;
			forTurns = random.nextInt(30); //TODO: change this so that it is based on the distance between origin and dest
			bonus = 100; //bonus to be awarded if the train completes the goal in a number of turn <= the given constraint
		}
		else forTurns=0;

		if (rand==2) {
			train = resourceManager.getRandomTrain();
		}else{
			train = null;
		}
		
		Goal goal = new Goal(origin, destination, intermediary, turn, forTurns, bonus, train);


		// Goal with a specific train
		// TODO: THIS IS WHERE WE CHANGE GOALS
		/*if(random.nextInt(2) == 1) {
			goal.addConstraint("train", resourceManager.getTrainNames().get(random.nextInt(resourceManager.getTrainNames().size())));
		}*/

		return goal;
	}
	
	public void addRandomGoalToPlayer(Player player) {
		player.addGoal(generateRandom(player.getPlayerManager().getTurnNumber()));
	}

	public ArrayList<String> trainArrived(Train train, Player player) {
		ArrayList<String> completedString = new ArrayList<String>();
		for(Goal goal:player.getGoals()) {
			if(goal.isComplete(train)) {
				if (goal.isBonusCompleted(train)){
					player.updateScore(goal);
				}
				player.completeGoal(goal);
				player.removeResource(train);
				completedString.add("Player " + player.getPlayerNumber() + " completed a goal to " + goal.toString() + "!");
			}
		}
		System.out.println("Train arrived to final destination: " + train.getFinalDestination().getName());
		return completedString;
	}
}
