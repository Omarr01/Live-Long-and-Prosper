package code;

import java.util.ArrayList;

public class IDS extends SearchStrategy {
	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<GenericNode> expandedNodes, int depthLimit) {
		for (GenericNode node : expandedNodes) {
			if (depthLimit >= node.getDepth()) {
				nodes.addFirst(node);
			}
		}
		return nodes;
	}

}
