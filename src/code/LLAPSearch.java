package code;

public class LLAPSearch extends GenericSearch {

	public static String solve(String townParameters, String strategy, boolean visualize) {
		Town town = new Town(townParameters);

		switch (strategy) {
		case "BF":
			BFS bfs = new BFS();
			search(town, bfs);
			break;
		case "DF":
			DFS dfs = new DFS();
			search(town, dfs);
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

		return "";
	}
}
