package code;

import java.util.ArrayList;

public class GRSOne extends SearchStrategy {

	private GRSOneNodeComparator comparator = new GRSOneNodeComparator();

	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<GenericNode> expandedNodes, int depthLimit) {
		for (GenericNode node : expandedNodes) {
			nodes.add(node);
		}

		return nodes;
	}

	@Override
	public GRSOneNodeComparator getComparator() {
		return this.comparator;
	}

}
