package code;

import java.util.ArrayList;

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

	public ArrayList<Node> expand(Town town) {
		ArrayList<Node> expandedNodes = new ArrayList<>();

		for (Operator operator : Operator.values()) {
			Node node = this.isValidOperation(town, operator);

			if (node != null) {
				expandedNodes.add(node);
			}
		}

		return expandedNodes;
	}

	public Node isValidOperation(Town town, Operator operator) {
		Node node = null;
		State currentState = this.getState();

		switch (operator) {
		case BUILD1:
			node = town.build(this, 1);
			break;

		case BUILD2:
			node = town.build(this, 2);
			break;

		case RequestEnergy:
			if (currentState.getEnergy() < 50 && currentState.getEnergyDelay() == 0) {
				node = town.requestEnergy(this);
			}
			break;

		case RequestFood:
			if (currentState.getFood() < 50 && currentState.getFoodDelay() == 0) {
				node = town.requestFood(this);
			}
			break;

		case RequestMaterials:
			if (currentState.getMaterials() < 50 && currentState.getMaterialsDelay() == 0) {
				node = town.requestMaterials(this);
			}
			break;

		case WAIT:
			node = town.wait(this);
			break;
		}

		State nodeState = node.getState();

		if (nodeState.getFood() < 0 || nodeState.getMaterials() < 0 || nodeState.getEnergy() < 0
				|| nodeState.getMoneySpent() > 100000
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
}
