package code;

import java.util.ArrayList;

public class LLAPSearch extends GenericSearch {

	public static String solve(String townParameters, String strategy, boolean visualize) {
		String solution = "";
		
		ArrayList<String> operators = new ArrayList<>();
		operators.add("WAIT");
		operators.add("RequestMaterials");
		operators.add("RequestFood");
		operators.add("RequestEnergy");
		operators.add("BUILD2");
		operators.add("BUILD1");
		
		LLAPProblem problem = new LLAPProblem(operators, townParameters, new ArrayList<String>());

		switch (strategy) {
		case "BF":
			BFS bfs = new BFS();
			solution = search(problem, bfs);
			break;

		case "DF":
			DFS dfs = new DFS();
			solution = search(problem, dfs);
			break;

		case "ID":
			IDS ids = new IDS();
			solution = idSearch(problem, ids);
			break;

		case "UC":
			UCS ucs = new UCS();
			solution = search(problem, ucs);
			break;

		case "GR1":
			GRSOne grsOne = new GRSOne();
			solution = search(problem, grsOne);
			break;

		case "GR2":
			GRSTwo grsTwo = new GRSTwo();
			solution = search(problem, grsTwo);
			break;

		case "AS1":
			ASOne asOne = new ASOne();
			solution = search(problem, asOne);
			break;

		case "AS2":
			ASTwo asTwo = new ASTwo();
			solution = search(problem, asTwo);
			break;
		}
		return solution;
	}
}
