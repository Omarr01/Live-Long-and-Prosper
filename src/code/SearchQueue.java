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

	public void add(GenericNode node) {
		this.nodes.add((Node) node);
	}

	public void addFirst(GenericNode node) {
		if (this.nodes instanceof ArrayDeque) {
			((ArrayDeque<Node>) this.nodes).addFirst((Node) node);
		}
	}

	public GenericNode poll() {
		return this.nodes.poll();
	}

	public boolean isEmpty() {
		return this.nodes.isEmpty();
	}
}
