package code;

import java.util.ArrayList;

public class LLAPProblem extends Problem {
	public LLAPProblem(ArrayList<String> operators, String initialStateString, ArrayList<String> stateSpace) {
		super(operators, initialStateString, stateSpace);
		Town.setInitialState(initialStateString);
	}

	@Override
	public Node createInitialNode() {
		return new Node(new State(Town.getInitialProsperity(), Town.getInitialFood(), Town.getInitialMaterials(),
				Town.getInitialEnergy(), 0, 0, 0, 0), null, null, 0, 0);
	}

	@Override
	public boolean goalTest(GenericState state) {
		return ((State) state).getProsperity() >= 100;
	}

	public static int pathCost(GenericState state) {
		return ((State) state).getMoneySpent();
	}
}
