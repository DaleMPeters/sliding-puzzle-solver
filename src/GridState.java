import java.util.ArrayList;

/**
 * The class to represent a single state of the puzzle at any one time.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 * 
 */
public class GridState implements Cloneable, Comparable<Object> {

	/**
	 * The number of rows the 8-puzzle has.
	 */
	private static final int NUMBER_OF_ROWS = 3;
	/**
	 * The number of columns the 8-puzzle has.
	 */
	private static final int NUMBER_OF_COLUMNS = 3;
	/**
	 * The characters in the puzzle.
	 */
	private char[][] grid = new char[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
	/**
	 * All the possible states that can be reached from the current state.
	 */
	private ArrayList<GridState> children = new ArrayList<GridState>();
	/**
	 * The parent of the state.
	 */
	private GridState parentState = null;
	/**
	 * The cost to get to the state.
	 */
	private int costToGetToState;
	/**
	 * A prediction of the cost it will take to get to the target state.
	 */
	private int predictedCostToTarget;
	/**
	 * The value of the cost of the <code>GridState</code> object.
	 */
	private int cost;

	/**
	 * Creates an instance of the GridState class.
	 * 
	 * @param grid
	 *            The 2D character array to represent the numbers on the tiles
	 *            in the puzzle.
	 */
	public GridState(char[][] grid) {
		this.grid = grid;
	}
	
	/**
	 * Sets the <code>grid</code> instance variable of the GridState object.
	 * 
	 * @param grid
	 *            The 2D char array to set the variable to.
	 */
	public void setGrid(char[][] grid) {
		this.grid = grid;
	}

	/**
	 * Returns the GridState object's 2D array, stored in the <code>grid</code>
	 * variable.
	 * 
	 * @return
	 */
	public char[][] getGrid() {
		return this.grid;
	}
	
	/**
	 * Sets the cost of the gridState object.
	 * 
	 * @param cost
	 *            The cost of the gridState object to set.
	 */
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	/**
	 * Returns the cost of the gridState object.
	 * 
	 * @return the cost of the gridState object.
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Returns the cost it took to get to the state.
	 * 
	 * @return the costToGetToState
	 */
	public int getCostToGetToState() {
		return costToGetToState;
	}

	/**
	 * Sets the value of the cost it took to get to the state.
	 * 
	 * @param costToGetToState
	 *            the costToGetToState to set
	 */
	public void setCostToGetToState(int costToGetToState) {
		this.costToGetToState = costToGetToState;
	}

	/**
	 * Returns the predicted cost to get from the <code>GridState</code> to get
	 * to the target.
	 * 
	 * @return the predictedCostToTarget
	 */
	public int getPredictedCostToTarget() {
		return predictedCostToTarget;
	}

	/**
	 * Sets the predicted cost to get from the particular <code>GridState</code>
	 * object to the target.
	 * 
	 * @param predictedCostToTarget
	 *            the predictedCostToTarget to set
	 */
	public void setPredictedCostToTarget(int predictedCostToTarget) {
		this.predictedCostToTarget = predictedCostToTarget;
	}

	/**
	 * Sets the state's parent.
	 * 
	 * @param parent
	 *            The parent of the state.
	 */
	public void setParentState(GridState parent) {
		parentState = parent;
	}

	/**
	 * Returns the state's parent state.
	 * 
	 * @return The state's parent state.
	 */
	public GridState getParentState() {
		return parentState;
	}

	/**
	 * Returns the possible grid states that are generated as a result of a move
	 * of the current state's gap (zero).
	 * 
	 * @return The possible grids that can be reached as a result of movements
	 *         of the gap (zero).
	 */
	public ArrayList<GridState> getChildren() {
		return children;
	}

	/**
	 * Determines whether the gap (zero) can move upwards in the grid. If the
	 * zero is already in the top row of the grid, false is returned, otherwise
	 * true is returned.
	 * 
	 * @return false if the gap (zero) can't move upwards in the grid.
	 */
	public boolean canMoveUp() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			if (grid[0][i] == '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines whether the gap (zero) can move downwards in the grid. If the
	 * zero is already in the bottom row of the grid, false is returned,
	 * otherwise true is returned.
	 * 
	 * @return false if the gap (zero) can't move downwards in the grid.
	 */
	public boolean canMoveDown() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			if (grid[2][i] == '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines whether the gap (zero) can move leftwards in the grid. If the
	 * zero is already in the left hand column of the grid, false is returned,
	 * otherwise true is returned.
	 * 
	 * @return false if the gap (zero) can't move leftwards in the grid.
	 */
	public boolean canMoveLeft() {
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			if (grid[i][0] == '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Determines whether the gap (zero) can move rightwards in the grid. If the
	 * zero is already in the right hand column of the grid, false is returned,
	 * otherwise true is returned.
	 * 
	 * @return false if the gap (zero) can't move rightwards in the grid.
	 */
	public boolean canMoveRight() {
		for (int i = 0; i < NUMBER_OF_COLUMNS; i++) {
			if (grid[i][2] == '0') {
				return false;
			}
		}
		return true;
	}

	/**
	 * Moves the gap (zero) to the tile on the right of it and the number in the
	 * tile to the right of it to the place where the zero was originally.
	 */
	public void moveZeroRight() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				// If zero not in 3rd column
				if (grid[i][j] == '0') {
					char tempChar = grid[i][j + 1];
					grid[i][j + 1] = '0';
					grid[i][j] = tempChar;
					return;
				}
			}
		}
	}

	/**
	 * Moves the gap (zero) to the tile on the left of it and the number in the
	 * tile to the left of it to the place where the zero was originally.
	 */
	public void moveZeroLeft() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				// If zero not in 1st column
				if (grid[i][j] == '0') {
					char tempChar = grid[i][j - 1];
					grid[i][j - 1] = '0';
					grid[i][j] = tempChar;
					return;
				}
			}
		}
	}

	/**
	 * Moves the gap (zero) to the tile above it and the number in the tile
	 * above it to the place where the zero was originally.
	 */
	public void moveZeroUp() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				if (grid[i][j] == '0') {
					char tempChar = grid[i - 1][j];
					grid[i - 1][j] = '0';
					grid[i][j] = tempChar;
					return;
				}
			}
		}
	}

	/**
	 * Moves the gap (zero) to the tile below it and the number in the tile
	 * below it to the place where the zero was originally.
	 */
	public void moveZeroDown() {
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				if (grid[i][j] == '0') {
					char tempChar = grid[i + 1][j];
					grid[i + 1][j] = '0';
					grid[i][j] = tempChar;
					return;
				}
			}
		}
	}

	/**
	 * Determines if the 2D array of one GridState object is equal to that of
	 * another by overriding the <code>equals</code> method in the Java API. It
	 * tests the deep equality of two objects, rather than the shallow equality
	 * that is tested by the <code>equals</code> method provided in the Object
	 * class of the Java API.
	 * 
	 * @return true if the two 2D char arrays are the same.
	 */
	@Override
	public boolean equals(Object obj) {
		GridState other = (GridState) obj;
		for (int i = 0; i < this.grid.length; i++) {
			for (int j = 0; j < this.grid[i].length; j++) {
				if (this.grid[i][j] != other.grid[i][j]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Generates all the possible states that can be reached from another
	 * particular state by moving the gap (zero) in all four directions (where
	 * possible) then adds all the possible child states to the GridState
	 * object's <code>children</code> ArrayList variable.
	 */
	public void generateChildren() {
		if (canMoveUp()) {
			// Create child with grid same as parent
			GridState upChild = new GridState(this.clone());
			// Swap zero in grid with the number above it in the grid
			upChild.moveZeroUp();
			upChild.setParentState(this);

			this.children.add(upChild);
		}
		if (canMoveLeft()) {

			// Create child with grid identical to that of the parent
			GridState leftChild = new GridState(this.clone());
			leftChild.setParentState(this);
			// Swap the zero in the grid with the number to the left of it
			leftChild.moveZeroLeft();

			this.children.add(leftChild);
		}
		if (canMoveRight()) {
			// Create child with grid identical to that of the parent
			GridState rightChild = new GridState(this.clone());

			rightChild.setParentState(this);

			// Swap the zero in the grid with the number to the right of it
			rightChild.moveZeroRight();

			this.children.add(rightChild);
		}
		if (canMoveDown()) {
			// Create child with grid identical to that of the parent
			GridState downChild = new GridState(this.clone());
			// Swap the zero in the grid with the number below it
			downChild.moveZeroDown();
			downChild.setParentState(this);

			this.children.add(downChild);
		}
	}

	/**
	 * Used to clone the 2D char array stored in the <code>grid</code> variable
	 * for the particular <code>GridState</code> object.
	 * 
	 * Overrides the <code>clone</code> method that is provided in the Object
	 * class of the Java API so the actual grid is cloned.
	 * 
	 * @return The cloned 2D array
	 */
	@Override
	public char[][] clone() {
		char[][] newGrid = new char[NUMBER_OF_ROWS][NUMBER_OF_COLUMNS];
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				newGrid[i][j] = this.grid[i][j];
			}
		}
		return newGrid;
	}

	/**
	 * Returns a string representation of the <code>grid</code> instance
	 * variable so that the path can be printed.
	 * 
	 * @return a string representation of the <code>grid</code> instance
	 *         variable.
	 */
	public String toString() {
		String stringRepresentation = "";
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				stringRepresentation += grid[i][j];
			}
			stringRepresentation += "\n";
		}
		return stringRepresentation;
	}

	/**
	 * Compares <code>this</code> gridState object to another. Written for the
	 * purpose of sorting the GridState objects in the AStarSearch class.
	 * Overrides the compareTo method in the Object class of the Java API.
	 * 
	 * @param o
	 *            The object to compare <code>this</code> gridState object to.
	 */
	@Override
	public int compareTo(Object o) {
		GridState other = (GridState) o;
		return cost - other.cost;
	}
}
