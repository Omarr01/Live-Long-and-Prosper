package code;

import java.util.ArrayList;

public abstract class Problem {
	private static ArrayList<String> operators;
	private String initialState;
	private ArrayList<String> stateSpace;
	
	public Problem(ArrayList<String> operators, String initialState, ArrayList<String> stateSpace) {
		this.operators = operators;
		this.initialState = initialState;
		this.stateSpace = stateSpace;
	}

	public abstract boolean goalTest(GenericState state);
	
	public static int pathCost(GenericNode node) {
		return 0;
	}

	public static ArrayList<String> getOperators() {
		return operators;
	}

	public String getInitialState() {
		return initialState;
	}

	public ArrayList<String> getStateSpace() {
		return stateSpace;
	}
}
