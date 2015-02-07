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
import java.lang.Math;

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
		int rand = random.nextInt(3);
		if (rand == 0){//decide if goal has intermediary station; if not, initiate  the intermediary station as origin
			do {
				intermediary = map.getRandomStation();
			} while (intermediary == origin || intermediary == destination || intermediary instanceof CollisionStation);
			//This bonus might need tuning
			bonus = (int) (map.getShortestDistance(origin,intermediary) + map.getShortestDistance(intermediary,destination));
		}
		else intermediary = origin;

		if (rand==1){ //decides if goal can be competed in a number of turns for bonus;
			int expectedTurns;
			int shortestDist = (int) map.getShortestDistance(origin,destination);
			expectedTurns = shortestDist/45;
			int lowerBound = (int) Math.floor(expectedTurns - 0.5*expectedTurns);
			int upperBound = (int) Math.ceil(expectedTurns + 0.5*expectedTurns);
			do {
				forTurns = random.nextInt(upperBound);
			}while (forTurns < lowerBound);
			//TODO: Calculate a suitable bonus
			bonus = (forTurns - lowerBound);
		}
		else forTurns=0;

		if (rand==2) {
			train = resourceManager.getRandomTrain();
			int shortestDist = (int) map.getShortestDistance(origin,destination);
			bonus = ((100-train.getSpeed())/100) * shortestDist + shortestDist;
		}else{
			train = null;
		}
		
		Goal goal = new Goal(origin, destination, intermediary, turn, forTurns, bonus, train);


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
					player.updateScore(goal.getBonus());
				}else{
					player.updateScore(goal.getScore());
				}
				player.completeGoal(goal);
				player.removeResource(train);
				completedString.add("Player " + player.getPlayerNumber() + " completed a goal to " + goal.toString() + "!");
			}
		}
		System.out.println("Train arrived at final destination: " + train.getFinalDestination().getName());
		return completedString;
	}
}
