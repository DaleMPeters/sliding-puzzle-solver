import java.util.ArrayList;
import java.util.Stack;

/**
 * The class that is used to hold the data structures and behaviours to carry
 * out the depth first search algorithm on the puzzle to be solved.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 * 
 */
public class DepthFirstSearch {
	/**
	 * Stores the GridStates that have already been visited by the algorithm.
	 */
	private ArrayList<GridState> closed = new ArrayList<GridState>();
	/**
	 * Used to keep a track of the order of the states to visit.
	 */
	private Stack<GridState> stack = new Stack<GridState>();
	/**
	 * Keeps a track of how many nodes have been expanded.
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
	 * Carries out the depth first search algorithm on the 8 puzzle to be
	 * solved.
	 * 
	 * This method is based on the pseudo code found at:
	 * http://en.wikipedia.org/wiki/Depth-first_search
	 * 
	 * @param initialState
	 *            The initial (starting) state of the puzzle. Represents the
	 *            tile positions at the start of the puzzle.
	 * @param targetState
	 *            The target (goal) state that the algorithm is required to try
	 *            and reach. Represents the tile positions the algorithm is to
	 *            try and get to by the end of the algorithm.
	 * @return The targetState <code>GridState</code> object when the solution
	 *         is found.
	 */
	public Path findPath(GridState initialState, GridState targetState) {
		stack.push(initialState);
		closed.add(initialState);

		while (!stack.isEmpty()) {
			GridState current = stack.peek();

			if (current.equals(targetState)) {
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

			// Generate the children of the state currently being worked on.
			current.generateChildren();
			for (GridState child : current.getChildren()) {
				nodesExpanded++;
				// If the child has already been worked on
				if (!this.closed.contains(child)) {
					stack.push(child);
					closed.add(child);
					current = child;
					break;
				} else {
					stack.remove(child);
				}
			}
		}
		return null; // Failed to make a path
	}
}
