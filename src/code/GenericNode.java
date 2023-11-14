package code;

import java.util.ArrayList;
import java.util.Objects;

public abstract class GenericNode {
	private GenericState state;
	private GenericNode parent;
	private String operator;
	private int depth;
	private int pathCost;

	public GenericNode(GenericState state, GenericNode parent, String operator, int depth, int pathCost) {
		this.state = state;
		this.parent = parent;
		this.operator = operator;
		this.depth = depth;
		this.pathCost = pathCost;
	}

	public abstract ArrayList<GenericNode> expand();

	public GenericState getState() {
		return this.state;
	}

	public void setState(GenericState state) {
		this.state = state;
	}

	public GenericNode getParent() {
		return this.parent;
	}

	public void setParent(GenericNode parent) {
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
		GenericNode other = (GenericNode) obj;
		return Objects.equals(this.state, other.getState());
	}
}
