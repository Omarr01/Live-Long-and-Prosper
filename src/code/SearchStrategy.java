package code;

import java.util.ArrayList;
import java.util.Deque;

public abstract class SearchStrategy {
	public abstract Deque<Node> queueingFunction(Deque<Node> nodes, ArrayList<Node> expandedNodes);
}
