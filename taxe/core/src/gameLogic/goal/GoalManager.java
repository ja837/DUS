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
		//This routine generates a random goal and calculates the points that should be awarded for completion of the goal

		//IDEA:
		//Calculate score outside of constructor or bonus inside constructor
		//Bonus for train = score + ((100-speed)/100)*score
		//Bonus for turns = score + (proportion of forturns compared to expected turns) * score
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
		double shortestDist = map.getShortestDistance(origin,destination);
		int score = (int)(shortestDist * (Math.pow(1.0001,shortestDist)));
		Random random = new Random();
		int rand = random.nextInt(3);
		if (rand == 0){
		//decide if goal has intermediary station; if not, initiate  the intermediary station as origin
			do {
				//Generates a suitable intermediary station
				intermediary = map.getRandomStation();
			} while (intermediary == origin || intermediary == destination || intermediary instanceof CollisionStation|| map.inShortestPath(origin,destination,intermediary));
			bonus = (int) (map.getShortestDistance(origin, intermediary) + map.getShortestDistance(intermediary, destination));
		}
		else intermediary = origin;

		if (rand==1){
		//decides if goal can be competed in a number of turns for bonus;
			int expectedTurns;
			expectedTurns = (int) shortestDist/45;
			int lowerBound = (int) Math.floor(expectedTurns - 0.5*expectedTurns);
			int upperBound = (int) Math.ceil(expectedTurns + 0.5*expectedTurns);
			do {
				forTurns = random.nextInt(upperBound+1);
			}while (forTurns < lowerBound);
			int diffFromExpected = forTurns - expectedTurns;
			bonus = score + (int) (1/2+(diffFromExpected/(upperBound-lowerBound))*shortestDist);
		}
		else forTurns=0;

		if (rand==2) {
			train = resourceManager.getRandomTrain();
			//This will now never be less than the base score as it includes it in the calculation
			bonus =(int) ((((100-train.getSpeed())/100) * shortestDist)+score);
		}
		
		Goal goal = new Goal(origin, destination, intermediary, turn, forTurns,score, bonus, train);

		return goal;
	}
	
	public void addRandomGoalToPlayer(Player player) {
		player.addGoal(generateRandom(player.getPlayerManager().getTurnNumber()));
		for (int i = 0; i<20;i++){
			generateRandom(player.getPlayerManager().getTurnNumber());
		}
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
