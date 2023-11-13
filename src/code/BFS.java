package code;

import java.util.ArrayList;

public class BFS extends SearchStrategy {
	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			nodes.add(node);
		}
		return nodes;
	}
}
