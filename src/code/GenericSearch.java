package code;

import java.util.ArrayList;
import java.util.HashSet;

public class GenericSearch {

	public static String search(Problem problem, SearchStrategy queueingFunctionObject, boolean visualize, int depthLimit) {
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
			if (problem.goalTest(currentNodeState))
				return getSequenceOfActions(currentNode, visualize) + ";" + currentNode.getPathCost() + ";"
						+ numberOfNodesExpanded;
			ArrayList<Node> expandedNodes = handleRepeatedStates(currentNode.expand(), oldNodes);
			nodes = queueingFunctionObject.queueingFunction(nodes, expandedNodes, depthLimit);
			numberOfNodesExpanded++;
		}
		return "NOSOLUTION";
	}

	public static String search(Problem problem, SearchStrategy queueingFunctionObject, boolean visualize) {
		return search(problem, queueingFunctionObject, visualize, -1);
	}

	public static String idSearch(Problem problem, SearchStrategy queueingFunctionObject, boolean visualize) {
		int depthLimit = 0;
		while (true) {
			String solution = search(problem, queueingFunctionObject, visualize, depthLimit);
			depthLimit++;
			if (solution != "NOSOLUTION")
				return solution;
		}
	}

	public static String getSequenceOfActions(Node leafNode, boolean visualize) {
		String sequenceOfActions = "";
		Node currentNode = leafNode;

		String sequenceOfStates = "";
		
		while (currentNode.getParent() != null) {
			sequenceOfActions = currentNode.getOperator() + (currentNode == leafNode ? "" : ",") + sequenceOfActions;
			
			State currentNodeState = currentNode.getState();
			
			sequenceOfStates = "----- State " + currentNode.getDepth() + " -----\n" + currentNodeState.toString() + "\n" + sequenceOfStates;
			
			currentNode = currentNode.getParent();
		}
		
		State currentNodeState = currentNode.getState();
		
		sequenceOfStates = "----- State " + currentNode.getDepth() + " -----\n" + currentNodeState.toString() + "\n" + sequenceOfStates;

		System.out.println(sequenceOfStates + "\n======================================================");
		
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
