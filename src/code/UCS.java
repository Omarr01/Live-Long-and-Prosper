package code;

import java.util.ArrayList;

public class UCS extends SearchStrategy {

	private UCSNodeComparator comparator = new UCSNodeComparator();

	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<GenericNode> expandedNodes, int depthLimit) {
		for (GenericNode node : expandedNodes) {
			nodes.add(node);
		}

		return nodes;
	}

	@Override
	public UCSNodeComparator getComparator() {
		return this.comparator;
	}

}
