package code;

import java.util.ArrayList;

public abstract class Problem {
	private static ArrayList<String> operators;
	private String initialStateString;
	private ArrayList<String> stateSpace;

	public Problem(ArrayList<String> operators, String initialStateString, ArrayList<String> stateSpace) {
		this.operators = operators;
		this.initialStateString = initialStateString;
		this.stateSpace = stateSpace;
	}

	public abstract boolean goalTest(GenericState state);

	public abstract GenericNode createInitialNode();

	public static int pathCost(GenericNode node) {
		return 0;
	}

	public static ArrayList<String> getOperators() {
		return operators;
	}

	public GenericState getInitialState() {
		return null;
	}

	public ArrayList<String> getStateSpace() {
		return this.stateSpace;
	}
}
