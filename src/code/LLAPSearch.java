package code;

public class LLAPSearch extends GenericSearch {

	public static String solve(String townParameters, String strategy, boolean visualize) {
		Town.setInitialState(townParameters);
		String solution = "";

		switch (strategy) {
		case "BF":
			BFS bfs = new BFS();
			solution = search(bfs);
			break;

		case "DF":
			DFS dfs = new DFS();
			solution = search(dfs);
			break;

		case "ID":
			IDS ids = new IDS();
			solution = idSearch(ids);
			break;

		case "UC":
			UCS ucs = new UCS();
			solution = search(ucs);
			break;

		case "GR1":
			GRSOne grsOne = new GRSOne();
			solution = search(grsOne);
			break;

		case "GR2":
//			GRSTwo grsTwo = new GRSTwo();
//			solution = search(grsTwo, -1);
			break;

		case "AS1":
			ASOne asOne = new ASOne();
			solution = search(asOne);
			break;

		case "AS2":
//			ASTwo asTwo = new ASTwo();
//			solution = search(asTwo, -1);
			break;
		}
		return solution;
	}
}
