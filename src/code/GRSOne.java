package code;

import java.util.ArrayList;

public class GRSOne extends SearchStrategy {

	private GRSOneNodeComparator comparator = new GRSOneNodeComparator();

	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			nodes.add(node);
		}

		return nodes;
	}

	@Override
	public GRSOneNodeComparator getComparator() {
		return this.comparator;
	}

}
