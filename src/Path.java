import java.util.ArrayList;

/**
 * Class to collect the gridState objects that are on the path the algorithms
 * take to solve the 8-puzzle.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 * 
 */
public class Path {
	/**
	 * Stores the <code>GridState</code> objects the algorithm took to solve the
	 * 8-puzzle.
	 */
	private ArrayList<GridState> path;

	/**
	 * Creates a new instance of the <code>Path</code> class.
	 */
	public Path() {
		path = new ArrayList<GridState>();
	}

	/**
	 * Adds a <code>GridState</code> object to the start of the <code>path</code>
	 * instance variable (adds it at index 0 of the ArrayList).
	 * 
	 * @param state The <code>GridState</code> object to add to the path.
	 */
	public void addToStart(GridState state) {
		path.add(0, state);
	}

	/**
	 * Returns the path of the algorithm.
	 * 
	 * @return the path the algorithm took.
	 */
	public ArrayList<GridState> getPath() {
		return this.path;
	}
}
