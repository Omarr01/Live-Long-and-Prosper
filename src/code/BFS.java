package code;

import java.util.ArrayList;

public class BFS extends SearchStrategy {
	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<GenericNode> expandedNodes, int depthLimit) {
		for (GenericNode node : expandedNodes) {
			nodes.add(node);
		}
		return nodes;
	}
}
