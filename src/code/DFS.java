package code;

import java.util.ArrayList;

public class DFS extends SearchStrategy {
	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			nodes.addFirst(node);
		}
		return nodes;
	}

}
