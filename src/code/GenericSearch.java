package code;

import java.util.ArrayList;
import java.util.HashSet;

public class GenericSearch {

	public static String search(Problem problem, SearchStrategy queueingFunctionObject, boolean visualize,
			int depthLimit) {
		SearchQueue nodes = new SearchQueue(queueingFunctionObject);
		GenericNode rootNode = problem.createInitialNode();
		nodes.add(rootNode);
		int numberOfNodesExpanded = 0;
		HashSet<GenericNode> oldNodes = new HashSet<>();
		while (!nodes.isEmpty()) {
			GenericNode currentNode = nodes.poll();
			GenericState currentNodeState = currentNode.getState();
			if (problem.goalTest(currentNodeState))
				return getSequenceOfActions(currentNode, visualize) + ";" + currentNode.getPathCost() + ";"
						+ numberOfNodesExpanded;
			ArrayList<GenericNode> expandedNodes = handleRepeatedStates(currentNode.expand(), oldNodes);
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

	public static String getSequenceOfActions(GenericNode leafNode, boolean visualize) {
		String sequenceOfActions = "";
		GenericNode currentNode = leafNode;

		String sequenceOfStates = "";

		while (currentNode.getParent() != null) {
			sequenceOfActions = currentNode.getOperator() + (currentNode == leafNode ? "" : ",") + sequenceOfActions;

			GenericState currentNodeState = currentNode.getState();

			sequenceOfStates = "----- State " + currentNode.getDepth() + " -----\n" + currentNodeState.toString() + "\n"
					+ sequenceOfStates;

			currentNode = currentNode.getParent();
		}

		GenericState currentNodeState = currentNode.getState();

		sequenceOfStates = "----- State " + currentNode.getDepth() + " -----\n" + currentNodeState.toString() + "\n"
				+ sequenceOfStates;

		System.out.println(sequenceOfStates + "\n======================================================");

		return sequenceOfActions;
	}

	public static ArrayList<GenericNode> handleRepeatedStates(ArrayList<GenericNode> expandedNodes,
			HashSet<GenericNode> oldNodes) {
		ArrayList<GenericNode> expandedNodesNoDuplicates = new ArrayList<>();
		for (GenericNode expandedNode : expandedNodes) {
			int oldSize = oldNodes.size();
			oldNodes.add(expandedNode);
			if (oldNodes.size() != oldSize) {
				expandedNodesNoDuplicates.add(expandedNode);
			}
		}
		return expandedNodesNoDuplicates;
	}
}
