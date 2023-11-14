package code;

import java.util.ArrayList;

public class DFS extends SearchStrategy {
	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<GenericNode> expandedNodes, int depthLimit) {
		for (GenericNode node : expandedNodes) {
			nodes.addFirst(node);
		}
		return nodes;
	}

}
