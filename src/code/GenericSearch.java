package code;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class GenericSearch {
	private Operator operators;
	private State initialState;
	private ArrayList<State> reachableStates;
//	private
//	private int pathCost;

	public static String search(Town town, SearchStrategy queueingFunctionObject) {
		Deque<Node> nodes = new ArrayDeque<Node>();
		State rootNodeState = new State(town.getInitialProsperity(), town.getInitialFood(), town.getInitialMaterials(),
				town.getInitialEnergy(), 0, 0, 0, 0);
		Node rootNode = new Node(rootNodeState, null, null, 0, 0);
		nodes.addFirst(rootNode);
		int nodesExpanded = 0;
		while (!nodes.isEmpty()) {
			Node currentNode = nodes.poll();
			State currentNodeState = currentNode.getState();
			if (currentNodeState.getProsperity() >= 100)
				return getSequenceOfActions(currentNode) + ";" + currentNodeState.getMoneySpent() + ";" + nodesExpanded;
			nodes = queueingFunctionObject.queueingFunction(nodes, currentNode.expand(town));
		}
		return "NOSOLUTION";
	}

	public static String getSequenceOfActions(Node leafNode) {
		String sequenceOfActions = ".";
		Node currentNode = leafNode;
		while (currentNode != null) {
			sequenceOfActions = currentNode.getOperator() + (currentNode == leafNode ? "" : ",") + sequenceOfActions;
			currentNode = currentNode.getParent();
		}
		return sequenceOfActions;
	}

}
