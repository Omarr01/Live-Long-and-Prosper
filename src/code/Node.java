package code;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;

public class Node {
	private State state;
	private Node parent;
	private Operator operator;
	private int depth;
	private double pathCost;

	public Node(State state, Node parent, Operator operator, int depth, double pathCost) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
	}

	public ArrayList<Node> expand() {
		ArrayList<Node> expandedNodes = new ArrayList<>();

		for (Operator operator : Operator.values()) {
			Node node = this.isValidOperation(operator);

			if (node != null) {
				expandedNodes.add(node);
			}
		}

		return expandedNodes;
	}

	public Node isValidOperation(Operator operator) {
		Node node = null;
		State currentState = this.getState();

		switch (operator) {
		case BUILD1:
			node = Town.build(this, 1);
			break;

		case BUILD2:
			node = Town.build(this, 2);
			break;

		case RequestEnergy:
			if (currentState.getEnergy() < 50 && currentState.getEnergyDelay() == 0) {
				node = Town.requestEnergy(this);
			}
			break;

		case RequestFood:
			if (currentState.getFood() < 50 && currentState.getFoodDelay() == 0) {
				node = Town.requestFood(this);
			}
			break;

		case RequestMaterials:
			if (currentState.getMaterials() < 50 && currentState.getMaterialsDelay() == 0) {
				node = Town.requestMaterials(this);
			}
			break;

		case WAIT:
			if (currentState.getFoodDelay() + currentState.getMaterialsDelay() + currentState.getEnergyDelay() > 0) {
				node = Town.wait(this);
			}
			break;
		}

		if (node == null)
			return null;

		State nodeState = node.getState();

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

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Operator getOperator() {
		return this.operator;
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public double getPathCost() {
		return this.pathCost;
	}

	public void setPathCost(double pathCost) {
		this.pathCost = pathCost;
	}

	@Override
	public int hashCode() {
		return Objects.hash(this.state);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		Node other = (Node) obj;
		return Objects.equals(this.state, other.state);
	}

}

class UCSNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		State n1State = n1.getState();
		State n2State = n2.getState();

		if (n1State.getMoneySpent() < n2State.getMoneySpent())
			return -1;
		if (n1State.getMoneySpent() > n2State.getMoneySpent())
			return 1;
		return 0;
	}
}

class GRSOneNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		State n1State = n1.getState();
		State n2State = n2.getState();

		int n1RemainingProsperity = Math.min(100 - n1State.getProsperity(), 0);

		int n2RemainingProsperity = Math.min(100 - n2State.getProsperity(), 0);

		double n1Heuristic = Math.min((n1RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n1RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		double n2Heuristic = Math.min((n2RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n2RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		if (n1Heuristic < n2Heuristic)
			return -1;
		if (n1Heuristic > n2Heuristic)
			return 1;
		return 0;
	}
}

class GRSTwoNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		// TO BE IMPLEMENTED
		return 0;
	}
}

class ASOneNodeComparator implements Comparator<Node> {
	@Override
	public int compare(Node n1, Node n2) {
		State n1State = n1.getState();
		State n2State = n2.getState();

		int n1RemainingProsperity = Math.min(100 - n1State.getProsperity(), 0);

		int n2RemainingProsperity = Math.min(100 - n2State.getProsperity(), 0);

		double n1Heuristic = Math.min((n1RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n1RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		double n2Heuristic = Math.min((n2RemainingProsperity / Town.getProsperityBUILD1()) * Town.getTotalPriceBUILD1(),
				(n2RemainingProsperity / Town.getProsperityBUILD2()) * Town.getTotalPriceBUILD2());

		if (n1Heuristic + n1State.getMoneySpent() < n2Heuristic + n2State.getMoneySpent())
			return -1;
		if (n1Heuristic + n1State.getMoneySpent() > n2Heuristic + n2State.getMoneySpent())
			return 1;
		return 0;
	}
}

class ASTwoNodeComparator implements Comparator<Node> {

	@Override
	public int compare(Node n1, Node n2) {
		// TO BE IMPLEMENTED
		return 0;
	}
}
