import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * The class that is used to hold the data structures and behaviours to carry
 * out the depth first search algorithm on the puzzle to be solved.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 * 
 */
public class BreadthFirstSearch {

	/**
	 * Contains a list of the <code>GridState</code> objects that have already
	 * been worked on.
	 */
	private ArrayList<GridState> closed = new ArrayList<GridState>();
	/**
	 * Keeps track of the order of the states to work on.
	 */
	private Queue<GridState> queue = new LinkedList<GridState>();
	/**
	 * Keeps a track of how many nodes have been expanded.
	 */
	private int nodesExpanded = 0;

	/**
	 * Returns the number of nodes that have been expanded.
	 * 
	 * @return the number of nodes that have been expanded.
	 */
	public int getNodesExpanded() {
		return nodesExpanded;
	}

	/**
	 * Carries out the breadth first search algorithm on the 8-puzzle to be
	 * solved.
	 * 
	 * This method is based on the pseudo code found at:
	 * http://en.wikipedia.org/wiki/Breadth-first_search
	 * 
	 * @param initialState
	 *            The initial (starting) state of the puzzle. Represents the
	 *            tile positions at the start of the puzzle.
	 * @param targetState
	 *            The target (goal) state that the algorithm is required to try
	 *            and reach. Represents the tile positions the algorithm is to
	 *            try and get to by the end of the algorithm.
	 * @return the targetState <code>GridState</code> object when the solution
	 *         is found.
	 */
	public Path findPath(GridState initialState, GridState targetState) {
		queue.add(initialState);

		while (!queue.isEmpty()) {
			GridState current = queue.poll();

			// If the GridState object currently being worked on is the target,
			// break out of the loop.
			if (current.equals(targetState)) {
				System.out.println("TARGET STATE REACHED");
				System.out.println("This is the path it took:\n");

				// Traverses backwards to work out the path the algorithm took
				Path path = new Path();
				while (current.getParentState() != null) {
					path.addToStart(current);
					current = current.getParentState();
				}
				return path;
			}

			queue.remove(current);
			closed.add(current);

			current.generateChildren();
			for (GridState child : current.getChildren()) {
				nodesExpanded++;
				if (!this.closed.contains(child)) {
					queue.add(child);
					closed.add(child);
				}
			}
		}
		return null; // Failed to make a path
	}
}
