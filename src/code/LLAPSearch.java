package code;

public class LLAPSearch extends GenericSearch {

	public static String solve(String townParameters, String strategy, boolean visualize) {
		Town town = new Town(townParameters);
		String solution = "";

		switch (strategy) {
		case "BF":
			BFS bfs = new BFS();
			solution = search(town, bfs);
			break;

		case "DF":
			DFS dfs = new DFS();
			solution = search(town, dfs);
			break;

		case "ID":
			break;

		case "UC":
			break;

		case "GR1":
			break;

		case "GR2":
			break;

		case "AS1":
			break;

		case "AS2":
			break;
		}
		return solution;
	}
}
