package code;

import java.util.ArrayList;

public class ASOne extends SearchStrategy {

	private ASOneNodeComparator comparator = new ASOneNodeComparator();

	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			nodes.add(node);
		}

		return nodes;
	}

	@Override
	public ASOneNodeComparator getComparator() {
		return this.comparator;
	}

}
