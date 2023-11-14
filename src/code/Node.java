package code;

import java.util.ArrayList;
import java.util.Comparator;

public class Node extends GenericNode {

	public Node(State state, Node parent, String operator, int depth, int pathCost) {
		super(state, parent, operator, depth, pathCost);
	}

	@Override
	public ArrayList<GenericNode> expand() {
		ArrayList<GenericNode> expandedNodes = new ArrayList<>();

		for (String operator : LLAPProblem.getOperators()) {
			Node node = this.isValidOperation(operator);

			if (node != null) {
				expandedNodes.add(node);
			}
		}

		return expandedNodes;
	}

	public Node isValidOperation(String operator) {
		Node node = null;
		State currentState = (State) this.getState();

		switch (operator) {
		case "BUILD1":
			node = Town.build(this, 1);
			break;

		case "BUILD2":
			node = Town.build(this, 2);
			break;

		case "RequestEnergy":
			if (currentState.getEnergy() < 50 && currentState.getEnergyDelay() == 0) {
				node = Town.requestEnergy(this);
			}
			break;

		case "RequestFood":
			if (currentState.getFood() < 50 && currentState.getFoodDelay() == 0) {
				node = Town.requestFood(this);
			}
			break;

		case "RequestMaterials":
			if (currentState.getMaterials() < 50 && currentState.getMaterialsDelay() == 0) {
				node = Town.requestMaterials(this);
			}
			break;

		case "WAIT":
			if (currentState.getFoodDelay() + currentState.getMaterialsDelay() + currentState.getEnergyDelay() > 0) {
				node = Town.wait(this);
			}
			break;
		}

		if (node == null)
			return null;

		State nodeState = (State) node.getState();

		if (nodeState.getFood() < 0 || nodeState.getMaterials() < 0 || nodeState.getEnergy() < 0
				|| nodeState.getMoneySpent() > 100000 || nodeState.getFood() > 50 || nodeState.getMaterials() > 50
				|| nodeState.getEnergy() > 50
				|| (nodeState.getFoodDelay() == 0 && nodeState.getMaterialsDelay() != 0
						&& nodeState.getEnergyDelay() != 0)
				|| (nodeState.getFoodDelay() != 0 && nodeState.getMaterialsDelay() == 0
						&& nodeState.getEnergyDelay() != 0)
				|| (nodeState.getFoodDelay() != 0 && nodeState.getMaterialsDelay() != 0
						&& nodeState.getEnergyDelay() == 0))
			return null;
		return node;
	}

}

class UCSNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		State n1State = (State) n1.getState();
		State n2State = (State) n2.getState();

		return Integer.compare(n1State.getMoneySpent(), n2State.getMoneySpent());
	}
}

class GRSOneNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		State n1State = (State) n1.getState();
		State n2State = (State) n2.getState();

		int n1RemainingProsperity = Math.max(100 - n1State.getProsperity(), 0);

		double n1Heuristic = Math.min((n1RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n1RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		int n2RemainingProsperity = Math.max(100 - n2State.getProsperity(), 0);

		double n2Heuristic = Math.min((n2RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n2RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		return Double.compare(n1Heuristic, n2Heuristic);
	}
}

class GRSTwoNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		State n1State = (State) n1.getState();
		State n2State = (State) n2.getState();

		int n1RemainingProsperity = Math.max(100 - n1State.getProsperity(), 0);
		int n2RemainingProsperity = Math.max(100 - n2State.getProsperity(), 0);

		double n1RemainingBuild1 = n1RemainingProsperity / Town.getProsperityBUILD1();
		double n2RemainingBuild1 = n2RemainingProsperity / Town.getProsperityBUILD1();

		double n1RemainingBuild2 = n1RemainingProsperity / Town.getProsperityBUILD2();
		double n2RemainingBuild2 = n2RemainingProsperity / Town.getProsperityBUILD2();

		double n1Heuristic = calculateHeuristic(n1RemainingBuild1, n1RemainingBuild2, n1State);
		double n2Heuristic = calculateHeuristic(n2RemainingBuild1, n2RemainingBuild2, n2State);

		return Double.compare(n1Heuristic, n2Heuristic);
	}

	public static double calculateHeuristic(double remainingBuild1, double remainingBuild2, State state) {
		double build1FoodRequestCost = calculateRequestCost(remainingBuild1, Town.getFoodUseBUILD1(), state.getFood(),
				Town.getAmountRequestFood(), Town.getUnitPriceFood());
		double build1MaterialsRequestCost = calculateRequestCost(remainingBuild1, Town.getMaterialsUseBUILD1(),
				state.getMaterials(), Town.getAmountRequestMaterials(), Town.getUnitPriceMaterials());
		double build1EnergyRequestCost = calculateRequestCost(remainingBuild1, Town.getEnergyUseBUILD1(),
				state.getEnergy(), Town.getAmountRequestEnergy(), Town.getUnitPriceEnergy());

		double build2FoodCost = calculateRequestCost(remainingBuild2, Town.getFoodUseBUILD2(), state.getFood(),
				Town.getAmountRequestFood(), Town.getUnitPriceFood());
		double build2MaterialsCost = calculateRequestCost(remainingBuild2, Town.getMaterialsUseBUILD2(),
				state.getMaterials(), Town.getAmountRequestMaterials(), Town.getUnitPriceMaterials());
		double build2EnergyCost = calculateRequestCost(remainingBuild2, Town.getEnergyUseBUILD2(), state.getEnergy(),
				Town.getAmountRequestEnergy(), Town.getUnitPriceEnergy());

		return Math.min(
				(remainingBuild1 * Town.getTotalPriceBUILD1()) + build1FoodRequestCost + build1MaterialsRequestCost
						+ build1EnergyRequestCost,
				(remainingBuild2 * Town.getTotalPriceBUILD2()) + build2FoodCost + build2MaterialsCost
						+ build2EnergyCost);
	}

	public static double calculateRequestCost(double remainingBuild, double resourceUse, double currentResource,
			double amountRequest, double unitPrice) {
		return Math.min(0, ((remainingBuild * resourceUse - currentResource) / amountRequest) * unitPrice);
	}
}

class ASOneNodeComparator implements Comparator<Node> {
	@Override
	public int compare(Node n1, Node n2) {
		State n1State = (State) n1.getState();
		State n2State = (State) n2.getState();

		int n1RemainingProsperity = Math.max(100 - n1State.getProsperity(), 0);

		double n1Heuristic = Math.min((n1RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n1RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		int n2RemainingProsperity = Math.max(100 - n2State.getProsperity(), 0);

		double n2Heuristic = Math.min((n2RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n2RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		return Double.compare(n1Heuristic + n1.getPathCost(), n2Heuristic + n2.getPathCost());
	}
}

class ASTwoNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		State n1State = (State) n1.getState();
		State n2State = (State) n2.getState();

		int n1RemainingProsperity = Math.max(100 - n1State.getProsperity(), 0);
		int n2RemainingProsperity = Math.max(100 - n2State.getProsperity(), 0);

		double n1RemainingBuild1 = n1RemainingProsperity / Town.getProsperityBUILD1();
		double n2RemainingBuild1 = n2RemainingProsperity / Town.getProsperityBUILD1();

		double n1RemainingBuild2 = n1RemainingProsperity / Town.getProsperityBUILD2();
		double n2RemainingBuild2 = n2RemainingProsperity / Town.getProsperityBUILD2();

		double n1Heuristic = GRSTwoNodeComparator.calculateHeuristic(n1RemainingBuild1, n1RemainingBuild2, n1State);
		double n2Heuristic = GRSTwoNodeComparator.calculateHeuristic(n2RemainingBuild1, n2RemainingBuild2, n2State);

		return Double.compare(n1Heuristic + n1.getPathCost(), n2Heuristic + n2.getPathCost());
	}
}
