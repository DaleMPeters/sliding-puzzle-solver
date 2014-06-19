import java.util.ArrayList;
import java.util.PriorityQueue;

/**
 * The class that is used to hold the data structures and behaviours to carry
 * out the A Star (A*) search algorithm on the puzzle to be solved, using the
 * heuristic choice made by the user the command line arguments.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 */
public class AStarSearch {
	/**
	 * Keeps track of all the states that have been visited and worked on.
	 */
	private ArrayList<GridState> closed = new ArrayList<GridState>();
	/**
	 * Keeps track of which state the algorithm needs to work on next. When an
	 * element is added to it, it is automatically sorted according to the cost
	 * of the particular <code>GridState</code> object.
	 */
	private PriorityQueue<GridState> priorityQueue = new PriorityQueue<GridState>();

	/**
	 * Keeps a track of the number of nodes expanded.
	 */
	private int nodesExpanded = 0;

	/**
	 * Returns the number of nodes that have been expanded.
	 * 
	 * @return The number of nodes that have been expanded.
	 */
	public int getNodesExpanded() {
		return nodesExpanded;
	}

	/**
	 * Carries out the A Star (A*) search algorithm based on the heuristic
	 * chosen by the user in the command line arguments in order to attempt to
	 * find the shortest path from the Initial State to the Target State.
	 * 
	 * If the third argument passed in to this method is "manhattan", it will
	 * use the Manhattan Distance heuristic (the sum of the distances each tile
	 * is away from where it should be, as defined by the target state) to
	 * attempt to find a path from the start state to the target state.
	 * 
	 * If the third argument passed in to this method is "hamming", it will use
	 * the Hamming Distance heuristic (the number of tiles not in the place
	 * where they should be, according to the target state) to attempt to find a
	 * path from the start state to the target state.
	 * 
	 * This method is based on the pseudo code found at
	 * http://en.wikipedia.org/wiki/A*_search_algorithm
	 * 
	 * @param startState
	 *            The state in which the puzzle starts at.
	 * @param targetState
	 *            The state in which the algorithm is to try and achieve as a
	 *            result of carrying out the algorithm.
	 * 
	 * @param heuristic
	 *            A description of the heuristic the user wishes to use to solve
	 *            the problem.
	 * @return The path the algorithm generated from the starting state to the
	 *         goal state. Returns null if the algorithm failed.
	 */
	public Path findPath(GridState startState, GridState targetState,
			String heuristic) {

		startState.setCostToGetToState(0);
		if (heuristic.equals("hamming")) {
			startState.setPredictedCostToTarget(calculateHammingDistance(
					startState, targetState));
		}
		if (heuristic.equals("manhattan")) {
			startState.setPredictedCostToTarget(calculateManhattanDistance(
					startState, targetState));
		}
		startState.setCost(startState.getCostToGetToState()
				+ startState.getPredictedCostToTarget());

		priorityQueue.add(startState);

		while (!priorityQueue.isEmpty()) {
			GridState current = priorityQueue.poll();

			if (targetState.equals(current)) {
				System.out.println("TARGET STATE REACHED\n");
				System.out.println("This is the path it took:");

				// Traverses backwards to work out the path the algorithm took
				Path path = new Path();
				while (current.getParentState() != null) {
					path.addToStart(current);
					current = current.getParentState();
				}
				return path;
			}

			closed.add(current);

			current.generateChildren();
			for (GridState child : current.getChildren()) {
				nodesExpanded++;
				int provisionalCostTotal = 0;
				int provisionalCostToGetToState = 0;

				// If the heuristic selected is the Hamming Distance heuristic
				if (heuristic.equals("hamming")) {
					provisionalCostToGetToState = current.getCostToGetToState()
							+ calculateHammingDistance(child, current);
					provisionalCostTotal = provisionalCostToGetToState
							+ calculateHammingDistance(child, targetState);
				}

				// If the heuristic selected is the Manhattan Distance heuristic
				else if (heuristic.equals("manhattan")) {
					provisionalCostToGetToState = current.getCostToGetToState()
							+ calculateManhattanDistance(child, current);
					provisionalCostTotal = provisionalCostToGetToState
							+ calculateManhattanDistance(child, targetState);
				}

				if (closed.contains(child)
						&& provisionalCostTotal >= child.getCost()) {
					continue;
				}

				if (!priorityQueue.contains(child)
						|| provisionalCostTotal < child.getCost()) {
					child.setCostToGetToState(provisionalCostToGetToState);
					child.setCost(provisionalCostTotal);
					if (!priorityQueue.contains(child)) {
						priorityQueue.add(child);
					}
				}
			}
		}
		return null; // Failed to make a path.
	}

	/**
	 * Calculates the number of tiles in the particular state that are out of
	 * place from the target state. Used in the case that the user chose to ask
	 * the software to solve the problem using the Hamming Distance heuristic.
	 * 
	 * @param current
	 *            The state to calculate the number of misplaced tiles for.
	 * @param target
	 *            The targetState the algorithm is trying to find a path to get
	 *            to.
	 * @return The number of misplaced tiles in the <code>current</code> state.
	 */
	public int calculateHammingDistance(GridState current, GridState target) {
		int tilesOutOfPlace = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (current.getGrid()[i][j] != target.getGrid()[i][j]) {
					tilesOutOfPlace++;
				}
			}
		}
		return tilesOutOfPlace;
	}

	/**
	 * Calculates the sum of the distances each tile is away from where it
	 * should be, as defined by the target state passed in. Used in the case
	 * that the user chose to ask the software to solve the problem using the
	 * Manhattan Distance heuristic.
	 * 
	 * @param current
	 *            The state to calculate the Manhattan Distance for.
	 * @param target
	 *            The targetState the algorithm is trying to find a path to get
	 *            to.
	 * @return The sum of the distances each tile is away from where it should
	 *         be.
	 */
	public int calculateManhattanDistance(GridState current, GridState target) {
		int manhattanDistance = 0;
		for (int check = 1; check < 9; check++) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					// Converts check to character
					char checkAsChar = Character.forDigit(check, 10);
					int targetRow = 0, targetColumn = 0;
					int actualRow = 0, actualColumn = 0;
					if (target.getGrid()[i][j] == checkAsChar) {
						targetRow = i;
						targetColumn = j;
					}
					if (current.getGrid()[i][j] == checkAsChar) {
						actualRow = i;
						actualColumn = j;
					}
					int distanceRows = Math.abs(actualRow - targetRow);
					int distanceColumns = Math.abs(actualColumn - targetColumn);
					manhattanDistance += (distanceColumns + distanceRows);
				}
			}
		}
		return manhattanDistance;
	}
}
