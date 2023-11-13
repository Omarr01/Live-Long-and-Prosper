package code;

import java.util.ArrayList;

public class GRSTwo extends SearchStrategy {

	private GRSTwoNodeComparator comparator = new GRSTwoNodeComparator();

	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			nodes.add(node);
		}

		return nodes;
	}

	@Override
	public GRSTwoNodeComparator getComparator() {
		return this.comparator;
	}

}
