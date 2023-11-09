package code;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;

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
		int numberOfNodesExpanded = 0;
		ArrayList<Node> oldNodes = new ArrayList<>();
		while (!nodes.isEmpty()) {
			Node currentNode = nodes.poll();
			State currentNodeState = currentNode.getState();
			if (currentNodeState.getProsperity() >= 100) {
				System.out.println(getSequenceOfActions(currentNode) + ";" + currentNodeState.getMoneySpent() + ";"
						+ numberOfNodesExpanded);
				return getSequenceOfActions(currentNode) + ";" + currentNodeState.getMoneySpent() + ";"
						+ numberOfNodesExpanded;
			}
			ArrayList<Node> expandedNodes = handleRepeatedStates(currentNode.expand(town), oldNodes);
			oldNodes.addAll(expandedNodes);
			nodes = queueingFunctionObject.queueingFunction(nodes, expandedNodes);
			numberOfNodesExpanded++;
		}
		return "NOSOLUTION";
	}

	public static String getSequenceOfActions(Node leafNode) {
		String sequenceOfActions = "";
		Node currentNode = leafNode;
		while (currentNode.getParent() != null) {
			sequenceOfActions = currentNode.getOperator() + (currentNode == leafNode ? "" : ",") + sequenceOfActions;
			currentNode = currentNode.getParent();
		}
		return sequenceOfActions;
	}

//	public static ArrayList<Node> handleRepeatedStates(ArrayList<Node> expandedNodes, ArrayList<Node> oldNodes) {
//		ArrayList<Node> expandedNodesNoDuplicates = new ArrayList<>();
//		for (int i = 0; i < expandedNodes.size(); i++) {
//			boolean isDuplicate = false;
//			for (int j = 0; j < oldNodes.size(); j++) {
//				if (expandedNodes.get(i).getState().compareTo(oldNodes.get(j).getState())) {
//					isDuplicate = true;
//					break;
//				}
//			}
//			if (!isDuplicate) {
//				expandedNodesNoDuplicates.add(expandedNodes.get(i));
//			}
//		}
//		return expandedNodesNoDuplicates;
//	}

	public static ArrayList<Node> handleRepeatedStates(ArrayList<Node> expandedNodes, ArrayList<Node> oldNodes) {
		HashSet<State> oldStates = new HashSet<>();
		for (Node oldNode : oldNodes) {
			oldStates.add(oldNode.getState());
		}

		ArrayList<Node> expandedNodesNoDuplicates = new ArrayList<>();
		for (Node expandedNode : expandedNodes) {
			if (!oldStates.contains(expandedNode.getState())) {
				expandedNodesNoDuplicates.add(expandedNode);
			}
		}

		return expandedNodesNoDuplicates;
	}

}
