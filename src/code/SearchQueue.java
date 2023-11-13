package code;

import java.util.ArrayDeque;
import java.util.PriorityQueue;
import java.util.Queue;

public class SearchQueue {
	Queue<Node> nodes;

	public SearchQueue(SearchStrategy searchStrategy) {
		if (searchStrategy instanceof BFS || searchStrategy instanceof DFS || searchStrategy instanceof IDS) {
			this.nodes = new ArrayDeque<>();
		} else {
			this.nodes = new PriorityQueue<>(searchStrategy.getComparator());
		}
	}

	public void add(Node node) {
		this.nodes.add(node);
	}

	public void addFirst(Node node) {
		if (this.nodes instanceof ArrayDeque<Node>) {
			((ArrayDeque<Node>) this.nodes).addFirst(node);
		}
	}

	public Node poll() {
		return this.nodes.poll();
	}

	public boolean isEmpty() {
		return this.nodes.isEmpty();
	}
}
