package code;

import java.util.ArrayList;

public class IDS extends SearchStrategy {
	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			if (depthLimit >= node.getDepth()) {
				nodes.addFirst(node);
			}
		}
		return nodes;
	}

}
