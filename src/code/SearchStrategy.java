package code;

import java.util.ArrayList;
import java.util.Comparator;

public abstract class SearchStrategy {
	public abstract SearchQueue queueingFunction(SearchQueue nodes, ArrayList<GenericNode> expandedNodes,
			int depthLimit);

	public Comparator<Node> getComparator() {
		return null;
	}
}
