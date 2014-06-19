import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * The class that handles all the loading in of files from the file system.
 * 
 * @author Dale Peters (dmp9@aber.ac.uk)
 * 
 */
public class FileIO {
	/**
	 * The number of lines the files contain.
	 */
	private static final int NUMBER_OF_LINES_IN_FILE = 3;
	/**
	 * Used to hold the initial (starting) state of the puzzle.
	 */
	private GridState startState;
	/**
	 * Used to hold the state of the puzzle the algorithm is required to get to.
	 */
	private GridState targetState;

	/**
	 * Reads the two files specified by the user in to the program so that the
	 * algorithm can be carried out.
	 * 
	 * @param fileName
	 *            The name of the file the user wishes to read in, passed in by
	 *            the command line arguments <code>args[0]</code> and
	 *            <code>args[1]</code>.
	 * @param mode
	 *            The way in which the program decides which variable to store
	 *            the grid in. If it is "initial", it will store it in the
	 *            <code>startState</code> variable. If it is "target", it will
	 *            store it in the <code>targetState</code> variable.
	 * @throws FileNotFoundException
	 */
	public void readFile(String fileName, String mode)
			throws FileNotFoundException {
		Scanner fileReader = new Scanner(new InputStreamReader(
				new FileInputStream(fileName)));
		char[][] lines = new char[3][3];
		for (int i = 0; i < NUMBER_OF_LINES_IN_FILE; i++) {
			String documentLine = fileReader.nextLine();

			// We are not interested in the commas in the file, so only get
			// the non-comma characters.
			char char1 = documentLine.charAt(0);
			char char2 = documentLine.charAt(2);
			char char3 = documentLine.charAt(4);
			char[] line = { char1, char2, char3 };

			// To confirm the initial and target states that were loaded in to
			// the user.
			if (mode.equals("initial") && i == 0) {
				System.out.println("INITIAL STATE");
			}
			if (mode.equals("target") && i == 0) {
				System.out.println("TARGET STATE");
			}
			System.out.print(char1);
			System.out.print(char2);
			System.out.print(char3);

			// Puts each 1D array in to the 2D array of characters.
			lines[i] = line;

			// If it's the grid to represent the initial state, stores the 2D
			// array in the startState variable, else stores it in the
			// targetState variable.
			if (mode.equals("initial")) {
				startState = new GridState(lines);
				startState.setParentState(null);
			} else if (mode.equals("target")) {
				targetState = new GridState(lines);
			}
			System.out.println();
		}
		fileReader.close();
	}

	/**
	 * Returns the starting state of the puzzle to be solved.
	 * 
	 * @return the starting state of the puzzle to be solved.
	 */
	public GridState getStartState() {
		return startState;
	}

	/**
	 * Returns the state of the puzzle the algorithm is to try and get to in
	 * order to solve the puzzle.
	 * 
	 * @return the state of the puzzle the algorithm is to try and get to in
	 *         order to solve the puzzle.
	 */
	public GridState getTargetState() {
		return targetState;
	}
}
