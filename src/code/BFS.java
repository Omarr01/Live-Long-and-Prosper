package code;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public class BFS {
	public static Queue<Node> queueingFunction(Queue<Node> nodes, ArrayList<Node> expandedNodes) {
		for (Node node : expandedNodes) {
			nodes.add(node);
		}
		
		return nodes;
	}
}
