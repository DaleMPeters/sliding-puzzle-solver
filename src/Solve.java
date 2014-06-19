import java.io.FileNotFoundException;

/**
 * The main class of the program, containing the main method, so that the
 * program can be run from the command line.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 * 
 */
public class Solve {
	/**
	 * The main method of the program, used to run the program.
	 * 
	 * @param args
	 *            The arguments passed in from the command line when the run of
	 *            the program starts. The first two of these contain the file
	 *            name of the files containing a representation of the initial
	 *            state and target state respectively. The third command line
	 *            argument is used to represent the algorithm the user wishes to
	 *            use to solve the problem.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void main(String args[]) throws FileNotFoundException {
		Path path = new Path();

		// Stores the start time of the algorithm's run
		long startTime = System.currentTimeMillis();
		// Stores end time of the algorithm's run
		long endTime = 0;
		int nodesExpanded = 0;
		FileIO fileIO = new FileIO();
		BreadthFirstSearch bfs = new BreadthFirstSearch();
		DepthFirstSearch dfs = new DepthFirstSearch();
		AStarSearch astar = new AStarSearch();

		// Reads in the file that contains the data for the initial state
		fileIO.readFile(args[0], "initial");

		// Reads in the file that contains the data for the target state
		fileIO.readFile(args[1], "target");

		// Carries out the appropriate action depending on the third command
		// line argument entered.
		switch (args[2]) {
		case "bfs":
			startTime = System.currentTimeMillis();
			System.out.println("Solving with bfs...");
			path = bfs
					.findPath(fileIO.getStartState(), fileIO.getTargetState());
			nodesExpanded = bfs.getNodesExpanded();
			endTime = System.currentTimeMillis();
			break;
		case "dfs":
			startTime = System.currentTimeMillis();
			System.out.println("Solving with dfs...");
			path = dfs
					.findPath(fileIO.getStartState(), fileIO.getTargetState());
			endTime = System.currentTimeMillis();
			nodesExpanded = dfs.getNodesExpanded();
			break;
		case "astar1":
			startTime = System.currentTimeMillis();
			System.out
					.println("Solving with A Star Search using the Hamming Distance Heuristic...");
			path = astar.findPath(fileIO.getStartState(),
					fileIO.getTargetState(), "hamming");
			nodesExpanded = astar.getNodesExpanded();
			endTime = System.currentTimeMillis();
			break;
		case "astar2":
			startTime = System.currentTimeMillis();
			System.out
					.println("Solving with A Star search using the Manhattan Distance Heuristic...");
			path = astar.findPath(fileIO.getStartState(),
					fileIO.getTargetState(), "manhattan");
			nodesExpanded = astar.getNodesExpanded();
			endTime = System.currentTimeMillis();
			break;
		default:
			System.out
					.println("The third argument entered in the command line was not valid.");
			endTime = System.currentTimeMillis();
			break;
		}

		// Print the path the relevant algorithm found.
		for (GridState pathRoute : path.getPath()) {
			System.out.print(pathRoute.toString());
			System.out.println("===");
		}
		System.out.println("The number of nodes expanded was " + nodesExpanded);
		// Prints out the amount of time the algorithm took to execute in
		// milliseconds.
		System.out.println("Total time to execute algorithm: "
				+ (endTime - startTime) + " milliseconds.");
	}
}
