package code;

import java.util.ArrayList;
import java.util.Deque;

public class DFS extends SearchStrategy {
	@Override
	public Deque<Node> queueingFunction(Deque<Node> nodes, ArrayList<Node> expandedNodes) {
		for (Node node : expandedNodes) {
			nodes.addFirst(node);
		}
		return nodes;
	}

}
