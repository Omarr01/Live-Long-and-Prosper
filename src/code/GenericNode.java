package code;

import java.util.ArrayList;
import java.util.Objects;

public abstract class GenericNode {
	private State state;
	private Node parent;
	private String operator;
	private int depth;
	private int pathCost;

	public GenericNode(State state, Node parent, String operator, int depth, int pathCost) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
	}
	
	public abstract ArrayList<Node> expand();
	
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

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public int getDepth() {
		return this.depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getPathCost() {
		return this.pathCost;
	}

	public void setPathCost(int pathCost) {
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
		return Objects.equals(this.state, other.getState());
	}
}
