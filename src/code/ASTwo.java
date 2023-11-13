package code;

import java.util.ArrayList;

public class ASTwo extends SearchStrategy {

	private ASTwoNodeComparator comparator = new ASTwoNodeComparator();

	@Override
	public SearchQueue queueingFunction(SearchQueue nodes, ArrayList<Node> expandedNodes, int depthLimit) {
		for (Node node : expandedNodes) {
			nodes.add(node);
		}

		return nodes;
	}

	@Override
	public ASTwoNodeComparator getComparator() {
		return this.comparator;
	}

}
