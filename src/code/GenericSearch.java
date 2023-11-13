package code;

import java.util.ArrayList;
import java.util.HashSet;

public class GenericSearch {

	public static String search(SearchStrategy queueingFunctionObject, int depthLimit) {
		SearchQueue nodes = new SearchQueue(queueingFunctionObject);
		State rootNodeState = new State(Town.getInitialProsperity(), Town.getInitialFood(), Town.getInitialMaterials(),
				Town.getInitialEnergy(), 0, 0, 0, 0);
		Node rootNode = new Node(rootNodeState, null, null, 0, 0);
		nodes.add(rootNode);
		int numberOfNodesExpanded = 0;
		HashSet<Node> oldNodes = new HashSet<>();
		while (!nodes.isEmpty()) {
			Node currentNode = nodes.poll();
			State currentNodeState = currentNode.getState();
			if (currentNodeState.getProsperity() >= 100)
				return getSequenceOfActions(currentNode) + ";" + currentNodeState.getMoneySpent() + ";"
						+ numberOfNodesExpanded;
			ArrayList<Node> expandedNodes = handleRepeatedStates(currentNode.expand(), oldNodes);
			nodes = queueingFunctionObject.queueingFunction(nodes, expandedNodes, depthLimit);
			numberOfNodesExpanded++;
		}
		return "NOSOLUTION";
	}

	public static String search(SearchStrategy queueingFunctionObject) {
		return search(queueingFunctionObject, -1);
	}

	public static String idSearch(SearchStrategy queueingFunctionObject) {
		int depthLimit = 0;
		while (true) {
			String solution = search(queueingFunctionObject, depthLimit);
			depthLimit++;
			if (solution != "NOSOLUTION")
				return solution;
		}
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

	public static ArrayList<Node> handleRepeatedStates(ArrayList<Node> expandedNodes, HashSet<Node> oldNodes) {
		ArrayList<Node> expandedNodesNoDuplicates = new ArrayList<>();
		for (Node expandedNode : expandedNodes) {
			int oldSize = oldNodes.size();
			oldNodes.add(expandedNode);
			if (oldNodes.size() != oldSize) {
				expandedNodesNoDuplicates.add(expandedNode);
			}
		}
		return expandedNodesNoDuplicates;
	}
}
