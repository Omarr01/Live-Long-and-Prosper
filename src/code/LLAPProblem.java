package code;

import java.util.ArrayList;

public class LLAPProblem extends Problem {
	public LLAPProblem(ArrayList<String> operators, String initialState, ArrayList<String> stateSpace) {
		super(operators, initialState, stateSpace);
		Town.setInitialState(initialState);
	}

	public boolean goalTest(GenericState state) {
		return ((State) state).getProsperity() >= 100;
	}

	public static int pathCost(GenericState state) {
		return ((State) state).getMoneySpent();
	}
}
